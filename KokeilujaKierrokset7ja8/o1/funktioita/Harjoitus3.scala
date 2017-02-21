package o1.funktioita

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 7.3.

object Harjoitus3 extends App {

  def tulostaKuutio(n: Int) = {
    println(n * n * n)
  }

  def tulostaJosPositiivinen(luku: Int) = {
    if (luku > 0) {
      println(luku)
    }
  }
  def kahdesti(toiminto: Int => Int, kohde: Int) = toiminto(toiminto(kohde))
  
  def toistaJokaAlkiolle(lukuja: Vector[Int], mitaTehdaan: Int => Unit) = {
       for(luku <- lukuja){
         mitaTehdaan(luku)
     }
  }


  val esimerkkilukuja = Vector(5, 10, -20, -10, 5)
  println("Kuutiot:")
  toistaJokaAlkiolle(esimerkkilukuja, tulostaKuutio) // tulostaa omille riveilleen: 125, 1000, -8000, -1000, 125
  println("Positiiviset:")
  toistaJokaAlkiolle(esimerkkilukuja, tulostaJosPositiivinen) // tulostaa omille riveilleen: 5, 10, 5

}
