package o1.io

import scala.io.Source

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 9.3.

object Lukemisesimerkki5 extends App {

  val tiedosto = Source.fromFile("teksti.txt")

  try {
    for ((rivi, indeksi) <- tiedosto.getLines.zipWithIndex) {
      println((indeksi + 1) + ": " + rivi)
    }
  } finally {
    tiedosto.close()
  }
  
}


// Vaihtoehtoinen, imperatiivisempi toteutustapa try-lohkolle:

//    var rivinumero = 1              // askeltaja: 1, 2, 3, ...
//    for (rivi <- tiedosto.getLines) {
//      println(rivinumero + ": " + rivi)
//      rivinumero += 1
//    }

