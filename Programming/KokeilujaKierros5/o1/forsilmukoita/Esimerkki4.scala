package o1.forsilmukoita

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 5.4.

object Esimerkki4 extends App {

    val merkkijono = "kissa"
    for (indeksi <- 0 to merkkijono.length - 1) {
      val merkki = merkkijono(indeksi)
      println("Indeksillä " + indeksi + " on " + merkki + ", Unicodessa merkki #" + merkki.toInt + ".")
    }

}

