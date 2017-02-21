package o1.adventure
import scala.io.Source
import java.io.FileReader
import java.util.Scanner
import java.io.File
import scala.util.Random

import scala.collection.mutable.Map

  
/** A `Player` object represents a player character controlled by the real-life user of the program. 
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player */
class Player(startingArea: Area){

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private var playerInventory = Map[String, Item]()
  
  
                
  private val RNG = new Random                     
  
  private def randomInt(n: Int) = RNG.nextInt(n)
  
    /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before player is exhausted. */
  val timeLimit = 20
  
  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven
  
  /** Determines if the flashlight is on */
  var flashlightOn = false
  
  /** Determines if the lights have been switched on with the computer*/
  var lightsOn = false
  
  /** Determines whether player can hack the computer. */
  private var canHack = false
  
  /** Determines whether the computer is already hacked */
  private var computerHacked = false  
  
  /** Returns the current location of the player. */
  def location = this.currentLocation
  
  /** Prints out a map of the game area in ASCII. */
  def printMap() = {
    val source = scala.io.Source.fromFile("sanastot/dict/kartta.txt")
    source.getLines() mkString "\n"
  }
  
  /** Attempts to move the player in the given direction. This is successful if there 
    * is an exit from the player's current location towards the direction name. 
    * Returns a description of the results of the attempt. */
  def go(direction: String) = {
    if(direction == "door" || direction == "exit" || direction == "side"){
      "You cannot do that!"
    }else {
      val destination = this.location.neighbor(direction)
      this.currentLocation = destination.getOrElse(this.currentLocation) 
      if (destination.isDefined) "You go " + direction + "." else "You can't go " + direction + "."
    }
    
  }
  /** Exits player from the location. */
  def exit() = {
    val destination = this.location.neighbor("exit")
    val previousLocation = this.currentLocation.name
    this.currentLocation = destination.getOrElse(this.currentLocation) 
    if (destination.isDefined) "You exit "  +  previousLocation +"." else "You can't exit here."
  }
  
  /** Opens a door and moves player through it.*/
  def open(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation) 
    if (destination.isDefined) "You open door to " + this.currentLocation.name + "." else "There is no " + direction +  " to open."
  }
  
  /** Switches player's side of the metro tunnel.*/
  def switch(direction: String) = {
    val destination = this.location.neighbor(direction)
    this.currentLocation = destination.getOrElse(this.currentLocation) 
    if (destination.isDefined) "You switch side of the metro tunnel."  else "Where are you trying to switch to?"
  }
  

  
  /** Causes the player to rest for a short while (this has no substantial effect in game terms).
    * Returns a description of what happened. */
  def rest() = {
    "You rest for a while. Better get a move on, though." 
  }
  
  
  /** Signals that the player wants to quit the game. Returns a description of what happened within 
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }

  
  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name
  
  /** Returns a list of items in player's inventory. */
  def inventory: String = {
    if(!this.playerInventory.isEmpty){
      var output = "You are carrying:"
      
      for(item <- this.playerInventory.keys){
        output += "\n" + item
      }
      output

    }else{
      "You are empty-handed."
    }
  }
  
  /** Determines whether player has object in their invetory. */
  def has(itemName: String) = this.playerInventory.contains(itemName)
  
  /** Returns a brief description of the item */
  def examine(itemName: String): String = if(this.has(itemName)) "You look closely at the " + itemName + "." + "\n" + this.playerInventory(itemName).description else "If you want to examine something, you need to pick it up first."
  
  /** Returns player's fatigue level.*/
  def fatigue = {
    if(turnCount >= 18){
      List("I am at the brink of exhaustion.", "All I can think about is food!")(this.randomInt(2))
    }else if(turnCount >= 15){
      List("I could eat a horse.", "I am hunry like a wolf", "All I can think about is a delicious Metrobread!")(this.randomInt(3))
    }else if(turnCount >= 10){
      List("I am starting to feel hungry.", "I think my stomach grumbled.", "I could eat a Metrobread.")(this.randomInt(3))
    }else if(turnCount >=5){
      List("Running around makes me hungry.", "I could eat a bit more.", "Is there a Metrobread Co. we could stop by?")(this.randomInt(3))
    }else {
      "All good!"
    }
  }
    
  /** Adds item to player's inventory, if the item is nearby. */  
  def get(itemName: String): String = {
    if(this.location.contains(itemName) && itemName != "computer"){
      this.playerInventory(itemName) = this.location.removeItem(itemName).get
      "You pick up the " + itemName + "."
    }else if(this.has("metrobread") && itemName == "metrobread"){  
     "You don't have room for two metrobreads..."
    }else if(itemName == "computer"){
      "It is way too bulky. I couldn't carry it."
    }else{
      "There is no " + itemName + " here to pick up."
    }
  }
  
  /** Drops the item from player's inventory, if the item is in the inventory. */  
  def drop(itemName: String) ={
    if(this.playerInventory.contains(itemName)){
      this.currentLocation.addItem(playerInventory(itemName))
      this.playerInventory.remove(itemName)
      "You drop the " + itemName + "."
    }else{
      "You don't have that!"
    }
  }
  /** 
   *  Player eats the selected item from his inventory if there is one and it is edible. 
   *  As a result player gets more turns to complete the game. Easter egg for the brochure.
   */
  def eat(itemName: String) ={
    if(this.playerInventory.contains(itemName) && playerInventory(itemName).isEdible){
      this.playerInventory.remove(itemName)
      this.turnCount -= 5
      "You eat the " + itemName + "."
    }else if(this.playerInventory.contains(itemName) && !playerInventory(itemName).isEdible){
      "You can't eat that!"      
    }else{
      "You don't have that!"
    }
  }
  
  /** Player reads the book and depending on book certain message appears. Cookbook of Scala 
   	* gives player hacking abilities. Brochure prints out a map of the area. 
    */  
  def read(itemName: String) = {
    if(this.has(itemName) && itemName == "book"){
      this.canHack = true
      "You read the great Cookbook of Scala. \n\nYou feel hacker abilities growing inside you."  
    }else if(this.has(itemName) && itemName == "brochure"){
      "You read the West Metro's brochure. It says the metro system was to be operational in the August of 2016. Weird. \nMetro map: Grass Bay -> Ferry Island -> Birch Island -> Cone Cape -> Aalto University" 
    }else{
      "You don't have that!"
    }
  }
   /** Switches flashlight on and off, if player has one. */  
  def turnOn(): String = {
    if(this.has("flashlight")){
      if(this.flashlightOn){
        this.flashlightOn = false
        "You turn the flashlight off."
      }else{
        this.flashlightOn = true
        "You turn the flashlight on."
      }
    }else{
      "You do not have a flashlight."
    }
  }
  
   /** Computer terminal commands: 
    *  Player can interact with the computer in many ways. In order to interact they must gain terminal's access by hacking it.
    *  Player can turn the lights on and off from a tunnel section and print out a map of the area with the terminal. Computer
    *  also has action 'help' that lists player all the available computer commands. 
    */  
  def computer(command: String) = {
    if(computerHacked && this.location.contains("computer")){
      command match {
        case "lights on"    => this.lightsOn = true; "[TERMINAL]\n --SYSTEM REPORT-- \nAT LOCATION: BIRCH ISLAND \nLIGHTS: ON"  + "\nFIRE ALARM: OFF \nSYSTEM STATUS: OPERATIONAL"
        case "lights off"   => this.lightsOn = false;"[TERMINAL]\n --SYSTEM REPORT-- \nAT LOCATION: BIRCH ISLAND \nLIGHTS: OFF" + "\nFIRE ALARM: OFF \nSYSTEM STATUS: OPERATIONAL"
        case "map"          => "[TERMINAL]\nMAP OF WEST METRO\n" + printMap()
        case "help"         => "[TERMINAL] \nLIST OF TERMINAL COMMANDS \n 'lights on' \n 'lights off' \n 'map' \n 'firealarm on' \n 'firealarm off'"
        case "firealarm on" => "I don't want to do that."
        case _              => "[TERMINAL] \nCOMMAND NOT IDENTIFIED \n Try 'computer help'"
      }
      
    }else if(!computerHacked && command == "hack" && this.location.contains("computer")){
      if(this.canHack){
        computerHacked = true
        "Terminal access granted. \n\n--SYSTEM REPORT-- \nAT LOCATION: BIRCH ISLAND \nLIGHTS: OFF" + "\nFIRE ALARM: OFF \nSYSTEM STATUS: OPERATIONAL" 
      }else{
        "Umm... 01110011 01100011 01100001 01101100 01100001 00001010 ... what on earth is this?"
      }
      
    }else {
      "There is nothing you can hack here."
    }
  }
  
  /**Returns all the available game commands to player. */
  def help() = {
    " /* List of available commands /* \n 'go' (to direction)\n 'open' (door) \n 'exit' exits a room\n 'eat' (object from inventory) \n 'get' (object from current location) \n 'drop' (object from inventory) \n 'inventory' 'examine player inventory' \n 'examine' (object in inventory) \n 'read' (book/brochure) \n 'flashlight' switches flashlights state on and off \n 'computer' use commands on terminal \n 'quit' quits the game"  
  }
 
  /** Game items */
  val bread = new Item("metrobread", "It is a delicious bread from The Metro Bread Co.", true)
  val flashlight = new Item("flashlight", "It is a normal flashlights with batteries on it. I hope they are fully charged.", false)
  val brochure = new Item("brochure", "It looks to be a brochure of the West Metro project.", true)
  val book = new Item("book", "Cookbook of Scala. How did it end down here...?", false) 
  
  /**Admin console */
  def admin(command: String) = {
    command match {
      case "help"      => "List of all available commands: \n 'canHack' gives player the ability to hack. \n 'items' add all game items to player's location. \n 'turns' Adds ten more game turns. \n 'map' prints out a map of the West Metro\n 'stations' gives a list of Metro stations. "
      case "abilities" => this.canHack = true; "Player can hack now."
      case "items"     => this.location.addItem(bread); this.location.addItem(flashlight); this.location.addItem(brochure); this.location.addItem(book); "All game items added to player's location."
      case "stations"  => "lauttasaariA, lauttasaariB, koivusaariA, koivusaariB, keilaniemiA, keilaniemiB, aaltoA, aaltoB"
      case "map"       => this.printMap()
      case "turns"     => this.turnCount -= 10; "Added ten more turns."
      case _           => "Unknown command. Try 'help'"
    }    
    
  }
  



}


