package o1.io

import scala.io.Source

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 9.3.

object Lukemisesimerkki4 extends App {

  val tiedosto = Source.fromFile("running_up_that_hill.txt")

  try {
    val kokoSisalto = tiedosto.mkString
    o1.soita(kokoSisalto)
  } finally {
    tiedosto.close()
  }
  
}