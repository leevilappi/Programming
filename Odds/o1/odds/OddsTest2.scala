package o1.odds

// This program is gradually developed in Chapters 2.8, 2.9, and 3.1.
// It creates two Odds objects and uses them.

import scala.io.StdIn._

object OddsTest2 extends App {

  println("Please enter the odds of the first event as two integers on separate lines.")
  val first = new Odds(readInt(), readInt())
  println("Please enter the odds of the second event as two integers on separate lines.")
  val second = new Odds(readInt(), readInt())

  /* 
  println("The first event is likelier than the second: " + first.isLikelierThan(second))
  println("The first event is odds-on to happen: " + first.isLikely)
  println("The second event is odds-on to happen: " + second.isLikely)
   */
  println("Thank you for using OddsTest2. Please come back often. Have a nice day!")
  
}