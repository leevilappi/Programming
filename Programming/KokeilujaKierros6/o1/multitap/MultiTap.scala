package o1.multitap

import scala.io.StdIn._
 
/** This singleton object is a runnable program that allows the user to experiment with 
  * multi-tap input. See Chapter 6.1 in the course materials for details. */
object MultiTap extends App {

  val pad = new Keypad(Vector(" .,!?", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ", "ÅÄÖ"))

  
  // Below are some lines that call the methods of class KeyPad. If you wish, you can run the program 
  // and see what they print out. However, you should eventually remove these lines (or comment them out) 
  // and write new ones so that the program repeatedly asks the user for multi-tap input strings and 
  // prints out the corresponding text. 
  println(pad.charFor('5', 1))
  println(pad.charFor('5', 3))
  println(pad.charFor('3', 6))
  println(pad.toText("5555 55533300000000000000"))


}
