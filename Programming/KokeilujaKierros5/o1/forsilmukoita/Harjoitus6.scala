package o1.forsilmukoita

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 5.4.

object Harjoitus6 extends App {

  // Kokeile ajaa tämä ohjelma ja katso, mitä se tulostaa. 
  // Muuta for-alkuista riviä siten, että ohjelma tulostaa nykyisen 
  // tulosteensa sijaan kaikki tasakymmenluvut väliltä 10-1000.
  
  val alku = 10
  val loppu = 1000
  
  for (luku <- 10 to loppu by 10) {
    println(luku)
  }
  
}

