package o1.odds

// This program is developed in Chapters 2.6 and 2.9.
// It creates a single Odds object and uses some of its methods.

import scala.io.StdIn._

object OddsTest1 extends App {
  
  println("Please enter the odds as two integers on separate lines.")
  println("For instance, to enter the odds 5/1 (one in six chance of happening), write 5 and 1 on separate lines.")
  val firstInput = readInt()
  val secondInput = readInt() 
  
  val input = new Odds(firstInput, secondInput)
  
  println("The odds you entered are:")
  println("In fractional format: " + input.fractional)
  println("In decimal format: " + input.decimal) 
  println("In moneyline format:" + input.moneyline.toInt)
  println("Event probability: " + input.probability)    

  println("Please enter the size of a bet:")
  val thirdInput = readDouble()
  println("If successful, the bettor would claim " + thirdInput * input.decimal )

}