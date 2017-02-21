package kierros3

/**
 * 
 */

object Color {
  
   def clamp(i : Int) : Int = ???
   
}

class Color(var a: Int, var r: Int, var g: Int, var b: Int, var argb : Int) {
  
  /**
   * @constructor
   * @param a
   * @param r
   * @param g
   * @param b
   */
  def this(a: Int, r: Int, g: Int, b: Int) {
    this(Color.clamp(a),
         Color.clamp(r),
         Color.clamp(g),
         Color.clamp(b),
         -1)
    this.setInt
  }
  
  /**
   * 
   * @constructor
   * @paaram argb
   */
  def this(argb: Int) {
    this(-1, -1, -1, -1, argb)
    this.setComponents()
  }
  
  
  /**
   * 
   */
  private def setInt() : Unit = ???
  
  /**
   * 
   */
  private def setComponents() : Unit = ???
  

}
