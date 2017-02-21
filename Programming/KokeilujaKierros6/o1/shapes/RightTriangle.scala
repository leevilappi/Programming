package o1.shapes
import math._

class RightTriangle(val leg1: Double, val leg2: Double) extends Shape {
  
  def area = (this.leg1 * this.leg2) / 2
  
  def hypotenuse = sqrt(pow(this.leg1,2) + pow(this.leg2,2))
  
  def perimeter = this.leg1 + this.leg2 + this.hypotenuse
  
}