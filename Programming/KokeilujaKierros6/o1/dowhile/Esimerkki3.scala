package o1.dowhile

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 6.1.

import scala.io.StdIn._

object Esimerkki3 extends App {

  var nimi = ""
  while (nimi.isEmpty) {
    nimi = readLine("Kerro nimesi (vähintään yksi merkki): ")
    println("Annetussa nimessä oli " + nimi.length + (if (nimi.length != 1) " merkkiä" else " merkki") + ".")
  }
  println("Nimi OK.") 
  
}

