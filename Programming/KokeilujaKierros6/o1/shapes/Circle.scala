
package o1.shapes

import scala.math.Pi

// TÄMÄ OHJELMAKOODI ON SELITETTY KURSSIMATERIAALIN LUVUSSA 6.2. 

class Circle(val radius: Double) extends Shape {

  def area = Pi * this.radius * this.radius

  def perimeter = 2 * Pi *  this.radius
}

