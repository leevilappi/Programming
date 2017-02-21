package o1.shapes

// TÄMÄ OHJELMAKOODI ON SELITETTY KURSSIMATERIAALIN LUVUSSA 6.2. 

import scala.collection.mutable.Buffer

object ShapeTest extends App {

  val shapes = Buffer(new Circle(10), new Rectangle(10, 100), new Circle(5))
    
  
  
  var sumOfAreas = 0.0
  for (current <- shapes) {
     sumOfAreas += current.area
  }
  println("Pinta-alojen summa on: " + sumOfAreas)
  
  val triangle = new RightTriangle(3.0, 4.0)
  println(new Circle(3).isBiggerThan(triangle))
  println(triangle.hypotenuse)
  println(triangle.isBiggerThan(new Rectangle(7, 5)))
}

