package o1.adventure
import scala.util.Random

/** 
 *  Developer details
 *  Course: 					CS-A1100
 *  Name: 						Leevi Lappi
 *  Student number: 	526270
 *  Email adress: 		leevi.lappi@aalto.fi
 */



/** The class `Adventure` represents text adventure games. An adventure consists of a player and 
  * a number of areas that make up the game world. It provides methods for playing the game one
  * turn at a time and for checking the state of the game.
  *
  */
class Adventure {
  
  private val RNG = new Random
  def randomInt(n: Int) = RNG.nextInt(n)
  
  /** The title of the adventure game. */
  val title = "West Metro Adventure"
  
  private def metroTunnelDescription = Vector("Standard modern metro tunnel.", "These metro tunnels go on forever!", "Is that light at the end of the tunnel?",
      "Oh wow, more metro tunnel...", "At least I'll be faster through these tunnels than the actual West Metro ", "How cool are these metro tunnels?")(randomInt(6))
 
      
  /** Creation of the game world begins here */
      
  /** Game begins here.*/
  private val turnaroundRail = new Area("Turnaround rail", "It is dark. You can distinct two tunnels.")
  
  /** The character that the player controls in the game. */
  val player = new Player(turnaroundRail)
  
  /** First set of tunnels */
  private val aTube1        = new Area("A Metro Tunnel", "Standard modern metro tunnel. West Metro looks promising."); 
  private val aTube2        = new Area("A Metro Tunnel", metroTunnelDescription); private val maintenanceRoomA1     = new Area("Maintenance room", "Small room used for storaging maintenance equipment")
  private val aTube3        = new Area("A Metro Tunnel", metroTunnelDescription); private val maintenanceRoomB1     = new Area("Maintenance room", "Small room used for storaging maintenance equipment")
  
  private val bTube1        = new Area("A Metro Tunnel", "Standard modern metro tunnel. West Metro looks promising.")
  private val bTube2        = new Area("A Metro Tunnel", metroTunnelDescription)
  private val bTube3        = new Area("A Metro Tunnel", metroTunnelDescription)
  
  private val lauttasaariA  = new Area("Ferry Island", "Metro station of Ferry Island. Metroes towards Grass Bay")
  private val lauttasaariB  = new Area("Ferry Island", "Metro station of Ferry Island. Metroes towards Birch Island")
  
  /** Second set of tunnels */
  private val aTube4        = new Area("A Metro Tunnel", metroTunnelDescription);
  private val aTube5        = new Area("A Metro Tunnel", metroTunnelDescription); private val maintenanceRoomA2     = new Area("Maintenance room", "Small room used for storaging maintenance equipment")
  private val aTube6        = new Area("A Metro Tunnel", metroTunnelDescription); 
  
  private val bTube4        = new Area("A Metro Tunnel", metroTunnelDescription)
  private val bTube5        = new Area("A Metro Tunnel", metroTunnelDescription); private val maintenanceRoomB2     = new Area("Maintenance room", "Small room used for storaging maintenance equipment")
  private val bTube6        = new Area("A Metro Tunnel", metroTunnelDescription)

  
  /** Dark tunnels. Returns a custom description whether player has a flashlight or has turned the lights on.*/
  private def koivusaariDescription = if (player.lightsOn) {
    koivusaariA.description = "Metro station of Birch Island. Metroes towards Cone Cape. \nLights are now on! I can see just fine now."
    koivusaariB.description = "Metro station of Birch Island. Metroes towards Cone Cape. \nLights are now on! I can see just fine now."
    aTube7.description = "These tunnels are now nicely lit!"
    bTube7.description = "These tunnels are now nicely lit!"
    
  }else if(player.flashlightOn){
    koivusaariA.description = "Metro station of Birch Island. Metroes towards Cone Cape. \nTunnels ahead look really dark. \nBut, I can see with the help of my flashlight."
    koivusaariB.description = "Metro station of Birch Island. Metroes towards Cone Cape. \nTunnels ahead look really dark. \nBut, I can see with the help of my flashlight."
    aTube7.description = "I can see in the tunnel with the help of my flashlight!"
    bTube7.description = "I can see in the tunnel with the help of my flashlight!"
    
  }else{
    koivusaariA.description = "Metro station of Birch Island. Metroes towards Cone Cape. \nTunnels ahead look really dark. I do not dare to enter. If I only had a flashlight"
    koivusaariB.description = "Metro station of Birch Island. Metroes towards Cone Cape. \nTunnels ahead look really dark. I do not dare to enter. If I only had a flashlight"
  }
  
  var desc = "Metro station of Birch Island. Metroes towards Cone Cape. \nTunnels ahead look really dark. I do not dare to enter."
  private var koivusaariA   = new Area("Birch Island", desc ) ; private val computerRoom = new Area("Computer room", "Small room with a running computer in it. \nThe screen light creates an eery athmosphere.")
  private var koivusaariB   = new Area("Birch Island", desc )
  
  /** Third set of tunnels */
  private val aTube7        = new Area("A Metro Tunnel", "I cannot see thing! It is so dark!!"); 
  private val aTube8        = new Area("A Metro Tunnel", metroTunnelDescription);
  private val aTube9        = new Area("A Metro Tunnel", metroTunnelDescription); 
  
  private val bTube7        = new Area("A Metro Tunnel", "I cannot see thing! It is so dark!!")
  private val bTube8        = new Area("A Metro Tunnel", metroTunnelDescription); private val maintenanceRoomB3     = new Area("Maintenance room", "Small room used for storaging maintenance equipment")
  private val bTube9        = new Area("A Metro Tunnel", metroTunnelDescription)
  
  private val keilaniemiA   = new Area("Cone Cape", "Metro station of Cone Cape. Metroes towards Aalto University")
  private val keilaniemiB   = new Area("Cone Cape", "Metro station of Cone Cape. Metroes towards Birch Island")
  
  /** Fourth set of tunnels */
  private val aTube10       = new Area("A Metro Tunnel", metroTunnelDescription) 
  private val aTube11       = new Area("A Metro Tunnel", metroTunnelDescription) 
  private val aTube12       = new Area("A Metro Tunnel", metroTunnelDescription) 
  
  private val bTube10       = new Area("A Metro Tunnel", metroTunnelDescription)
  private val bTube11       = new Area("A Metro Tunnel", metroTunnelDescription)
  private val bTube12       = new Area("A Metro Tunnel", metroTunnelDescription)
  
  private val aaltoA        = new Area("Aalto University", "Metro station of Aalto University. Metroes towards Tapiola.")
  private val aaltoB        = new Area("Aalto University", "Metro station of Aalto University. Metroes towards Cone Cape")
  private val aalto         = new Area("Aalto University", "Welcome to Aalto University!\n" + printFile("sanastot/dict/wintext.txt"))
 
  
  
  /** Set passages in tunnels. Each tunnel set consists of three tunnels. After the tunnel section there is a metro station. */  
  turnaroundRail.setNeighbors(Vector("left"  -> aTube1,               "right" -> bTube1                                              ))
  
  aTube1.setNeighbors      (Vector("forwards" -> aTube2,              "backwards" -> turnaroundRail                                  ))
  aTube2.setNeighbors      (Vector("forwards" -> aTube3,              "backwards" -> aTube1,        "door" -> maintenanceRoomA1      ))
  aTube3.setNeighbors      (Vector("forwards" -> lauttasaariA,        "backwards" -> aTube2                                          ))
  maintenanceRoomA1.setNeighbors(Vector("exit" -> aTube2                                                                             ))
  
  
  bTube1.setNeighbors      (Vector("forwards" -> bTube2,              "backwards" -> turnaroundRail                                  ))
  bTube2.setNeighbors      (Vector("forwards" -> bTube3,              "backwards" -> bTube1,        "door" -> maintenanceRoomB1      ))
  bTube3.setNeighbors      (Vector("forwards" -> lauttasaariB,        "backwards" -> bTube2                                          ))
  maintenanceRoomB1.setNeighbors(Vector("exit" -> bTube2                                                                             ))

  lauttasaariA.setNeighbors (Vector("side" -> lauttasaariB,           "forwards" -> aTube5,         "backwards" -> aTube4            ))
  lauttasaariB.setNeighbors (Vector("side" -> lauttasaariA,           "forwards" -> bTube5,         "backwards" -> bTube4            ))
  
  aTube4.setNeighbors      (Vector("forwards" -> aTube5,              "backwards" -> lauttasaariA                                    ))
  aTube5.setNeighbors      (Vector("forwards" -> aTube6,              "backwards" -> aTube4,        "door" -> maintenanceRoomA2      ))
  maintenanceRoomA2.setNeighbors(Vector("exit" -> aTube5                                                                             )) 
  aTube6.setNeighbors      (Vector("forwards" -> koivusaariA,         "backwards" -> aTube5                                          ))
  
  bTube4.setNeighbors      (Vector("forwards" -> bTube5,              "backwards" -> lauttasaariB                                    ))
  bTube5.setNeighbors      (Vector("forwards" -> bTube6,              "backwards" -> bTube4,        "door" -> maintenanceRoomB2      ))
  maintenanceRoomB2.setNeighbors(Vector("exit" -> aTube5                                                                             ))
  bTube6.setNeighbors      (Vector("forwards" -> koivusaariB,         "backwards" -> bTube5                                          ))
  
  koivusaariA.setNeighbors (Vector("side" -> koivusaariB,             "backwards" -> aTube6,        "door" -> computerRoom           ))
  koivusaariB.setNeighbors (Vector("side" -> koivusaariA,             "backwards" -> bTube6                                          ))
  computerRoom.setNeighbors(Vector("exit" -> koivusaariA))
  
  
  /** Check if player's flashlight is on or computer has turned lights on. If it is on, player can continue to the next set of tunnels */
  private def darkTunnels(){
    if(player.flashlightOn || player.lightsOn){      
      
      koivusaariA.setNeighbors (Vector("side" -> koivusaariB,             "forwards" -> aTube7, "backwards" -> aTube6, "door" -> computerRoom   ))
      koivusaariB.setNeighbors (Vector("side" -> koivusaariA,             "forwards" -> bTube7, "backwards" -> bTube6                           ))
      aTube7.setNeighbors      (Vector("forwards" -> aTube8,              "backwards" -> koivusaariA                                     ))
      aTube8.setNeighbors      (Vector("forwards" -> aTube9,              "backwards" -> aTube9                                          ))
      aTube9.setNeighbors      (Vector("forwards" -> keilaniemiA,         "backwards" -> aTube8                                          ))
    
      bTube7.setNeighbors      (Vector("forwards" -> bTube8,              "backwards" -> koivusaariB                                     ))
      bTube8.setNeighbors      (Vector("forwards" -> bTube9,              "backwards" -> bTube7                                          ))
      bTube9.setNeighbors      (Vector("forwards" -> keilaniemiB,         "backwards" -> bTube8,        "door" -> maintenanceRoomB3      ))
        
    }else{
      koivusaariA.removeNeighbor("forwards")
      koivusaariB.removeNeighbor("forwards")
      koivusaariA.setNeighbors  (Vector("side" -> koivusaariB,       "backwards" -> aTube6, "door" -> computerRoom                       ))
      koivusaariB.setNeighbors  (Vector("side" -> koivusaariA,       "backwards" -> bTube6                                               ))
    }
  }
  
  keilaniemiA.setNeighbors (Vector("side" -> keilaniemiB,      "forwards" -> aTube10, "backwards" -> aTube9                              ))
  keilaniemiB.setNeighbors (Vector("side" -> keilaniemiA,      "forwards" -> bTube10, "backwards" -> bTube9                              ))
  
  aTube10.setNeighbors     (Vector("forwards" -> aTube11,      "backwards" -> keilaniemiA                                                ))
  aTube11.setNeighbors     (Vector("forwards" -> aTube12,      "backwards" -> aTube10                                                    ))
  aTube12.setNeighbors     (Vector("forwards" -> aaltoA,       "backwards" -> aTube11                                                    ))
  
  bTube10.setNeighbors     (Vector("forwards" -> bTube11,      "backwards" -> keilaniemiB                                                ))
  bTube11.setNeighbors     (Vector("forwards" -> bTube12,      "backwards" -> bTube10                                                    ))
  bTube12.setNeighbors     (Vector("forwards" -> aaltoB,       "backwards" -> bTube8                                                     ))
  
  aaltoA.setNeighbors      (Vector("side" -> aaltoB,           "up" -> aalto, "backwards" -> aTube12                                     ))
  aaltoB.setNeighbors      (Vector("side" -> aaltoA,           "up" -> aalto, "backwards" -> bTube12                                     ))

  
  
  
 
  /** Add items to different locations of Adventure */

  /** Place flashlight to a random room. Other rooms will have a brochure.*/
  if(this.randomInt(3) == 0){
    maintenanceRoomA1.addItem(player.flashlight)
    maintenanceRoomB1.addItem(player.brochure)
    maintenanceRoomB2.addItem(player.brochure)
  }else if(this.randomInt(3) == 1){
    maintenanceRoomB1.addItem(player.flashlight)
    maintenanceRoomA1.addItem(player.brochure)
    maintenanceRoomB2.addItem(player.brochure)
  }else{
    maintenanceRoomB2.addItem(player.flashlight)
    maintenanceRoomA1.addItem(player.brochure)
    maintenanceRoomB1.addItem(player.brochure)
  }
  
  /** Place Metrobreads at random metro stations*/
  val stations = List(lauttasaariA, lauttasaariB, koivusaariA, koivusaariB, keilaniemiA, keilaniemiB, aaltoA, aaltoB)
  
  stations(randomInt(8)).addItem(player.bread)
  stations(randomInt(8)).addItem(player.bread)
  stations(randomInt(8)).addItem(player.bread)
  stations(randomInt(8)).addItem(player.bread)
  
  
  /** Place the computer to the computer room. */
  computerRoom.addItem(new Item("computer", "The computer seems to be on.", false))
  
  /** Place the cookbook of Scala to a maintenance room near Koivusaari*/
  maintenanceRoomA2.addItem(new Item("book", "Cookbook of Scala. How did it end down here...?", false) )

  /** Adding items ends here */
  
  
  
  /** Adds encounter with a metro train randomly to last tunnel section*/
  if(RNG.nextBoolean()){
    aTube11.description = "Oh no there is a metro train blocking the way!"
    aTube11.removeNeighbor("forwards")
  }else{
    bTube11.description = "Oh no there is a metro train blocking the way!"
    bTube11.removeNeighbor("forwards")
  }
    
  
  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = this.player.location == this.aalto

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */ 
  def isOver = this.isComplete || this.player.hasQuit || this.player.turnCount == this.player.timeLimit

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "You were in a metro on your route to Kamppi to catch a bus to Aalto Universty. \nYou fell asleep. \nWhen you woke up the metro was on its turnaround track. \nYour only option was to brace the West Metro's new tunnels to get to Aalto. \n\nRemember to watch out for your fatigue level. If it gets too low, you lose.\n\nOpen list of all commands by typing 'help'"

  /** Prints out a custom text */
  private def printFile(fileName: String) = {
    val source = scala.io.Source.fromFile(fileName)
    source.getLines() mkString "\n"
  }
    
  /** Returns a message that is to be displayed to the player at the end of the game. The message 
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if (this.isComplete)
      "I made to Aalto! Phew, my candidate's thesis will be easy compared to this."
    else if (this.player.turnCount == this.player.timeLimit)
      "You are too exhausted to continue the game. The West Metro tunnels got you. Game over!"
    else  // game over due to player quitting
      "Quitter!" 
  }

  
  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual 
    * report of what happened, or an error message if the command was unknown. In the latter 
    * case, no turns elapse. */
  def playTurn(command: String) = {
    val action = new Action(command)
    val outcomeReport = action.execute(this.player)
    darkTunnels()
    koivusaariDescription
    if (outcomeReport.isDefined) { 
      this.player.turnCount += 1 
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".") +  "\n\nFatigue: " + player.fatigue
  }
  
  
}

