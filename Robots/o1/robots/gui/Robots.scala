package o1.robots.gui

import scala.swing.SimpleSwingApplication
import scala.swing.Frame
import o1.robots.RobotWorld


////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////


/** The singleton object `Robots` represents (the basic version of) the Robots application. 
  * The object serves as an entry point for the robot simulator, and can be run to start up 
  * the user interface. 
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this object works 
  * or can be used, apart from the fact that you can use this file to start the program.''' */
object Robots extends RobotProgram(new RobotWindow with RobotScenarios)


/** The class `RobotsProgram` represents (variations of) the robot simulators application.
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this class works or can be used.''' */
class RobotProgram(window: RobotWindow, initialWorld: RobotWorld = new RobotWorld(12, 9)) extends SimpleSwingApplication {
  
  this.window.displayWorld(initialWorld)
  
  def top = this.window
  
}


