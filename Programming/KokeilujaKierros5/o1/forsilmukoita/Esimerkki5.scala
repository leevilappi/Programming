package o1.forsilmukoita

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 5.4.

object Esimerkki5 extends App {

  println(new Joulukuusi(200))
  println("\nHYVÄÄ JOULUA!")
  
}


class Joulukuusi(puunKorkeus: Int) {

  val korkeus = puunKorkeus.min(1000)                     // kiintoarvo
  private val leveinKohta = this.leveys(this.korkeus - 1) // kiintoarvo; apuna metoditoteutuksissa

  def leveys(rivi: Int) = rivi / 2 * 2 + 1

  override def toString = {
    var kuva = this.latvatahti              // kuva on kokooja: muodostetaan puun kuva pala palalta latvasta alas
    for (riviNro <- 1 until this.korkeus) { // riviNro on askeltaja
      kuva += havuja(riviNro)
    }
    kuva += this.runko
    kuva
  }

  private def latvatahti        = this.keskitetysti('*', 1)  
  private def havuja(rivi: Int) = this.keskitetysti('^', this.leveys(rivi))
  private def runko             = this.keskitetysti('|', (this.leveinKohta - 4).min(3).max(1))

  private def keskitetysti(merkki: Char, montako: Int) = {
    val tyhjaaVasemmalle = (this.leveinKohta - montako) / 2                  // tilapäissäilö
    val kokoRivi = " " * tyhjaaVasemmalle + merkki.toString * montako + "\n" // tilapäissäilö
    kokoRivi
  }

}

