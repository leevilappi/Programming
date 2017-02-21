package o1.io

import scala.io.Source

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 9.3.

object Lukemisesimerkki3 extends App {

  val tiedosto = Source.fromFile("teksti.txt")
  
  try {
    var merkkinumero = 1              // askeltaja: 1, 2, ...
    for (merkki <- tiedosto) {
      println("Merkki " + merkkinumero + " on " + merkki + ".")
      merkkinumero += 1
    }
  } finally {
    tiedosto.close()
  }
  
}