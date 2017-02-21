package o1.funktioita

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 7.3.

object Harjoitus1 extends App {

  // Määrittele tässä sellainen onLavistajalla-funktio,
  // joka palauttaa totuusarvon, joka kertoo ovatko
  // parametreina annetut rivi- ja sarakenumerot yhtä suuret.
  // Alla on sille valmiina käyttötapaus.

  // TODO: kirjoita koodisi tähän väliin
  
  def onLavistajalla(rivi: Int, sarake: Int) = rivi == sarake

  // TODO: poista kommenttimerkit seuraavien kahden rivin alusta
  val totuusarvoja = Vector.tabulate(5, 5)(onLavistajalla)
  println(totuusarvoja.mkString("\n"))

  // Ohjelman tulosteeksi pitäisi tulla:
  // Vector(true, false, false, false, false)
  // Vector(false, true, false, false, false)
  // Vector(false, false, true, false, false)
  // Vector(false, false, false, true, false)
  // Vector(false, false, false, false, true)

}
