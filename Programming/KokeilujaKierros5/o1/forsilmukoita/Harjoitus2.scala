package o1.forsilmukoita

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 5.4.

object Harjoitus2 extends App {

  val data = "Urho Kaleva Kekkonen, John Fitzgerald Kennedy, North Atlantic Treaty Organization"
    
  // Täydennä alla oleva silmukka siten, että se poimii tulosmuuttujaan data-muuttujan 
  // osoittamasta merkkijonosta kaiken paitsi pienet kirjoitusmerkit. 
  // Alla käytetty Char-luokan parametriton metodi isLower palauttaa totuusarvon, 
  // joka kertoo, onko kyseessä pieni kirjoitusmerkki.

  var tulos = ""  // kokooja: kokoaa tuloksen merkki kerrallaan lähtien tyhjästä
  for (kirjain <- data) {
    if (!kirjain.isLower) {
      tulos += kirjain
    }    
  }
  println(tulos)  // pitäisi tulostaa: U K K, J F K, N A T O
    
}

