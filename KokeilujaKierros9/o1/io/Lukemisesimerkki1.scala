package o1.io

import scala.io.Source

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 9.3.

object Lukemisesimerkki1 extends App {

  val tiedosto = Source.fromFile("teksti.txt")
  
  println("Tiedoston eka merkki on " + tiedosto.next() + ".")
  println("Tiedoston toka merkki on " + tiedosto.next() + ".")
  println("Tiedoston kolmas merkki on " + tiedosto.next() + ".")
  println("Tiedoston neljäs merkki on " + tiedosto.next() + ".")
  println("Tiedoston viides merkki on " + tiedosto.next() + ".")
  println("Tiedoston kuudes merkki on " + tiedosto.next() + ".")
  
  tiedosto.close()
  
}