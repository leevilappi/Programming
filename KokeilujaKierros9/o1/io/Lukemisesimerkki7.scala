package o1.io

import scala.io.Source

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 9.3.

object Lukemisesimerkki7 extends App {

  val tiedosto = Source.fromFile("teksti.txt")

  try {
  
    val vektorillinenRiveja = tiedosto.getLines.toVector
   
    println("TEKSTIRIVIT:")
    var rivinumero = 1              // askeltaja: 1, 2, 3, ...
    for (rivi <- vektorillinenRiveja) {
      println(rivinumero + ": " + rivi)
      rivinumero += 1
    }

    println("RIVIEN PITUUDET:")
    for (rivi <- vektorillinenRiveja) {
      println(rivi.length)
    }
    println("LOPPU")
    
  } finally {
    tiedosto.close()
  }
    
  
}