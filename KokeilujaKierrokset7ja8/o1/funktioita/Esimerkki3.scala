package o1.funktioita

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 7.3.

object Esimerkki3 extends App {

  def summa(rivi: Int, sarake: Int) = rivi + sarake
  
  val summataulukko = Array.tabulate(3, 5)(summa)
  
  for (rivi <- summataulukko) {
    println(rivi.mkString("\t"))
  }
  
}

