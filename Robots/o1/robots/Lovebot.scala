package o1.robots

import o1.grid._

/** The class `Lovebot` represents the "brains" (or AI) of robots which have another 
  * robot as their "beloved". A lovebot tries to home in on its beloved.                                                      
  * @param name     the name of the lovebot
  * @param body     the robot body whose actions the lovebot brain will control 
  * @param beloved  another robot that the lovebot targets */
class Lovebot(name: String, body: RobotBody, val beloved: RobotBody) extends RobotBrain(name, body) {

  
  /** Moves the robot. A lovebot tries to home in on its beloved unless it already is in 
    * an adjacent square (diagonally adjacent is not good enough). The lovebot blindly  
    * follows its primitive urges and doesn't know how to avoid obstacles. If there is  
    * a wall or another bot in the chosen direction, the lovebot will collide with it  
    * (as per [[RobotBody.moveTowards the `moveTowards` method of its body]], possibly 
    * colliding and breaking itself or another robot). 
    *
    * The path of movement is chosen as follows. First the lovebot calculates its distance 
    * to its target in both the x and y dimensions. It moves one square at a time so that 
    * either the horizontal (x) or the vertical (y) distance decreases by one, choosing 
    * the one that is greater. In case the distances are equal, the horizontal distance  
    * is chosen. The lovebot only moves one square per turn. It turns to face the 
    * direction it attempts to move in, even if that attempt fails because of a collision. 
    * The bot does not turn or move if it is already close enough to its beloved.
    *
    * This method assumes that it is called only if the robot is not broken or stuck. */
  def moveBody() = {
    this.directionOfMovement.foreach( this.body.moveTowards(_) )
  }
    
  
  /** Returns the next direction of movement for the lovebot based on the current state 
    * of the robot world. */
  private def directionOfMovement = {
    val (dx, dy) = this.distanceToBeloved  // assigns the returned pair of values to two variables
    val xDistance = dx.abs         
    val yDistance = dy.abs         
    if (xDistance + yDistance < 2) {
      None
    } else {
      val direction = if (xDistance >= yDistance) { if (dx < 0) West  else East  } 
                      else                        { if (dy < 0) North else South }
      Some(direction)
    }
  }
  
  /** Returns a pair of integers that represents the distance between this lovebot and its beloved. */
  private def distanceToBeloved = { 
    val from = this.location   
    val to   = this.beloved.location  
    (to.x - from.x, to.y - from.y)         
  }
    
    
}
