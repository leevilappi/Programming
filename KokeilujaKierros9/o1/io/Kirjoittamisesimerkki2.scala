package o1.io

import scala.io.Source
import java.io.PrintWriter

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 9.3.

object Kirjoittamisesimerkki2 extends App {

  val hakusana = "kuinka nopeasti"
  val syotetiedosto = Source.fromFile("googlen_ekat_hakusuositukset_24062015.txt")
  val kohdetiedosto = new PrintWriter("runo.txt")

  def virkkeeksi(ehdotus: String) = ehdotus.drop(hakusana.length).trim.capitalize + "."

  try {
    val googlenHakuehdotukset = syotetiedosto.getLines.toVector
    kohdetiedosto.println(hakusana.toUpperCase + "\n")
    for (rivinumero <- Vector(4, 2, 3, 7, 6, 10, 1, 5, 9, 8)) {
      kohdetiedosto.println(virkkeeksi(googlenHakuehdotukset(rivinumero - 1)))
    }
  } finally {
    syotetiedosto.close()
    kohdetiedosto.close()
  }
  
}