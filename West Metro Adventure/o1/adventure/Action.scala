package o1.adventure


/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect, 
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as "go east" or "rest" */
class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  
  /** Causes the given player to take the action represented by this object, assuming 
    * that the command was understood. Returns a description of what happened as a result 
    * of the action (such as "You go west."). The description is returned in an `Option` 
    * wrapper; if the command was not recognized, `None` is returned. 
    * Commands 'flashlight', 'computer', 'help' and 'admin' will not diminish player's fatigue.*/
  
  def execute(actor: Player): Option[String]  = {                             
   
     this.verb match{
      case "go"         => Some(actor.go(this.modifiers))
      case "rest"       => Some(actor.rest())
      case "quit"       => Some(actor.quit())
      case "get"        => Some(actor.get(this.modifiers))
      case "drop"       => Some(actor.drop(this.modifiers))
      case "inventory"  => Some(actor.inventory)
      case "examine"    => Some(actor.examine(modifiers))
      case "eat"        => Some(actor.eat(this.modifiers))
      case "read"       => Some(actor.read(this.modifiers))
      case "flashlight" => actor.turnCount -= 1; Some(actor.turnOn)
      case "exit"       => Some(actor.exit())
      case "open"       => Some(actor.open(this.modifiers))
      case "switch"     => Some(actor.switch(this.modifiers))
      case "computer"   => actor.turnCount -= 1; Some(actor.computer(this.modifiers))
      case "help"       => actor.turnCount -= 1; Some(actor.help())
      case "admin"      => actor.turnCount -= 1; Some(actor.admin(this.modifiers))
      case _            => None
    } 
    

  }


  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"  

  
}

