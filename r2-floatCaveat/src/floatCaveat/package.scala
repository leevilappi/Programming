
/*
 * Assignment:  Caveat floating point
 * 
 * Description:
 * This assignment asks you to study the rules of floating-point 
 * arithmetic well enough so that you understand some of its 
 * caveats. 
 *
 */

package object floatCaveat {

  /* 
   * Task: 
   * Find three values x,y,z of type Double, such that
   * (x+y)+z == 1.0 and x+(y+z) == 0.0
   *
   */
  val x: Double = 1e200
  val y: Double = -x
  val z: Double = 1
  
}

