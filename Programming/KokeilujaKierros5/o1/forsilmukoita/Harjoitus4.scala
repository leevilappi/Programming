package o1.forsilmukoita

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 5.4.

object Harjoitus4 extends App {

  // Alla oleva ohjelma käsiteltiin luvussa 5.4. Muuta sitä niin, että
  // käytät to-metodin sijaan until-metodia (kertaa luvusta 4.4, jos et muista, mistä on kyse).
  // Ohjelman tulee toimia muuten täsmälleen samoin kuin ennenkin.

  val merkkijono = "kissa"
  for (indeksi <- 0 until merkkijono.length ) {
    val merkki = merkkijono(indeksi)
    println("Indeksillä " + indeksi + " on " + merkki + ", Unicodessa merkki #" + merkki.toInt + ".")
  }
  
  
  
}

