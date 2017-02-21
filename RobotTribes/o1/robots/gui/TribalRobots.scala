package o1.robots.gui

import o1.robots.RobotWorld


////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////


/** The singleton object `TribalRobots` represents a tribe-enabled version of the 
  * Robots application. The object serves as an entry point for the robot simulator, 
  * and can be run to start up the user interface. 
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this object works 
  * or can be used, apart from the fact that you can use this file to start the program.''' */
object TribalRobots extends RobotProgram(new TribalRobotWindow with TribalScenarios)
  
