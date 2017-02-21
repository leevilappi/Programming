package o1.forsilmukoita

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 5.4.

object Esimerkki2 extends App {

  var indeksi = 0
  for (merkki <- "kissa") {
    println("Indeksillä " + indeksi + " on " + merkki + ", Unicodessa merkki #" + merkki.toInt + ".")
    indeksi += 1
  }

}

