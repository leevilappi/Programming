package o1.dowhile

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 6.1.

import scala.io.StdIn._

object Esimerkki1 extends App {

  var nimi = ""
  do {
    nimi = readLine("Kerro nimesi (vähintään yksi merkki): ")
    println("Nimessä oli " + nimi.length + (if (nimi.length != 1) " merkkiä" else " merkki") + ".")
  } while (nimi.isEmpty) 

  println("Nimi OK.")
  
}

