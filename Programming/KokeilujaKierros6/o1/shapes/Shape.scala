
package o1.shapes

// TÄMÄ OHJELMAKOODI ON SELITETTY KURSSIMATERIAALIN LUVUSSA 6.2. 

trait Shape {

  def isBiggerThan(another: Shape) = this.area > another.area
  
  def area: Double
  
  def perimeter: Double

}

