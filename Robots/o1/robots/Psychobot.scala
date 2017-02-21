package o1.robots

import o1.grid.Direction

/** The class `Psychobot` represents the "brains" (or AI) of robots which stand still 
  * until they see another unbroken robot on their radar. When this happens, they ram 
  * the victim with incredible speed. 
  * @param name  the name of the psychobot
  * @param body  the robot body whose actions the psychobot brain will control */
class Psychobot(name: String, body: RobotBody) extends RobotBrain(name, body) {

  
  /** Finds the first non-empty square in a direction, that is, what the bot "sees" in that direction. */
  private def firstNonEmptyIn(direction: Direction) = {
    var radarLocation = this.location
    do {
      radarLocation = radarLocation.neighbor(direction)
    } while (this.world(radarLocation).isEmpty)
    this.world(radarLocation)
  }
  
  
  /** Determines whether this psychobot sees a suitable victim (mobile robot) in the given direction. */
  private def seesVictim(dir: Direction) = this.firstNonEmptyIn(dir).robot.exists( _.isMobile )
  

  /** Moves in the given direction for as long as possible. */
  private def charge(direction: Direction) = {
    while (this.body.moveTowards(direction)) {
      // Keep charging!
    }
  }


  /** Moves the robot. During its turn, a psychobot uses its radar to scan the four main 
    * compass directions, always starting with north and continuing clockwise. (It does  
    * not change facing while doing this.) When the psychobot notices a mobile robot,  
    * it turns to face it, instantly moves an unlimited number of squares towards the robot, 
    * and rams into it, causing the victim to break. After the collision, the psychobot   
    * remains in the square adjacent to its victim, and stops scanning for the rest of the  
    * turn. During its next turn, it will start over by looking north. 
    *
    * If there are no victims in any of the four main directions, the robot waits still. 
    * It does not attack broken robots. A psychobot can not scan through walls and it can 
    * never collide with a wall. It also does not see through robots, not even broken ones.
    *
    * This method assumes that it is called only if the robot is not broken or stuck. */
  def moveBody() = {
    for (directionOfVictim <- Direction.Clockwise.find( this.seesVictim(_) )) {
      this.charge(directionOfVictim)
    }
  }


}
