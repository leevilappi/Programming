package o1.funktioita

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 7.3.

object Esimerkki2 extends App {

  def vertaaPituuksia(jono1: String, jono2: String) = jono1.length - jono2.length
  
  def vertaaIntArvoja(jono1: String, jono2: String) = jono1.toInt - jono2.toInt

  def vertaaMerkkeja(jono1: String, jono2: String) = jono1.compareToIgnoreCase(jono2)
  
  def onkoJarjestyksessa(eka: String, toka: String, kolmas: String, vertaa: (String, String) => Int) = 
    vertaa(eka, toka) <= 0 && vertaa(toka, kolmas) <= 0
  
  println(onkoJarjestyksessa("Java", "Scala", "Haskell", vertaaPituuksia))
  println(onkoJarjestyksessa("Haskell", "Java", "Scala", vertaaPituuksia))
  println(onkoJarjestyksessa("Java", "Scala", "Haskell", vertaaMerkkeja))
  println(onkoJarjestyksessa("Haskell", "Java", "Scala", vertaaMerkkeja))
  println(onkoJarjestyksessa("200", "123", "1000", vertaaIntArvoja))
  println(onkoJarjestyksessa("200", "123", "1000", vertaaPituuksia))
  
  
  
}

