package o1.io

import scala.io.Source

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 9.3.
// HUOM. Tämä on esimerkki toimimattomasta toteutuksesta.

object Lukemisesimerkki6 extends App {

  val tiedosto = Source.fromFile("teksti.txt")

  try {
  
    println("TEKSTIRIVIT:")
    var rivinumero = 1              // askeltaja: 1, 2, 3 ...
    for (rivi <- tiedosto.getLines) {
      println(rivinumero + ": " + rivi)
      rivinumero += 1
    }
    println("RIVIEN PITUUDET:")
    for (rivi <- tiedosto.getLines) {
      println(rivi.length + " merkkiä")
    }
    println("LOPPU")

  } finally {
    tiedosto.close()
  }
  
}