
package kierros3

object Filter {


  def lightness(factor: Float, img: Image) : Unit = ???


  def invert(img: Image) : Unit = ???


  def grayscale(img: Image) : Unit = ???


  def adjustRed(amount: Int, img: Image) : Unit = ???


  def adjustGreen(amount: Int, img: Image) : Unit = ???


  def adjustBlue(amount: Int, img: Image) : Unit = ???


  def blur(amount: Int, image: Image) : Unit = ???

  
  def sharpen(amount: Int, image: Image) : Unit = ???

  
  private def getFilter(amount: Int, seed: Float) : Array[Array[Float]] = ???

  
  private def multiplyWithFilter(x: Int, y: Int, image: Image, filter: Array[Array[Float]]) : Color = ???

}
