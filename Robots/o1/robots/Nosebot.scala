package o1.robots

import o1.grid.Direction

/** The class `Nosebot` represents the "brains" (or AI) of robots that move in the
  * direction their nose points, in straight lines, only turning when they have to.
  * They always turn right. 
  * @param name  the name of the nosebot
  * @param body  the robot body whose actions the nosebot brain will control */
class Nosebot(name: String, body: RobotBody) extends RobotBrain(name, body) {

  /** Moves the robot. A nosebot first looks at the next square in the direction it 
    * is currently facing. If that square is empty, it moves there and ends its turn. 
    * If the square was not empty, it turns 90 degrees clockwise and immediately 
    * tries again (without ending its turn after turning), moving a step forward if 
    * possible, and turning again to check the next direction if necessary. 
    * If the robot completes a 360-degree turnabout without finding a suitable 
    * square to move in, it ends its turn without changing its location. 
    * As a nosebot always looks where it's going, so it can never collide with 
    * anything during its own turn. A nosebot only ever moves a single square per turn.
    *
    * This method assumes that it is called only if the robot is not broken or stuck. */
  def moveBody() = {
    var attempt = 1
    while (attempt <= Direction.Count && !this.moveCarefully()) {
      this.body.spinClockwise()
      attempt += 1
    }
  }

  /*
   
  // An alternative solution that uses a return statement to break out of the loop:
  def moveBody(): Unit = {
    for (attempt <- 1 to Direction.Count) {
      if (this.moveCarefully()) {
        return 
      } else {
        this.body.spinClockwise()
      }
    } 
  }
  
   */
}
