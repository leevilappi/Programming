package o1.funktioita

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 7.3.

object Esimerkki1 extends App {

  def seuraava(luku: Int) = luku + 1

  def tuplaa(tuplattava: Int) = 2 * tuplattava

  def kahdesti(toiminto: Int => Int, kohde: Int) = toiminto(toiminto(kohde))
                                                                
  println(kahdesti(seuraava, 1000))
  println(kahdesti(tuplaa, 1000))
  
}

