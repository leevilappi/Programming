package o1.luokkia

// TÄMÄN LUOKAN KÄYTTÖTAPA ON SELITETTY LUVUSSA 2.3.

class Puhelinlasku(var asiakas: String) {

  private var soitetut = List[Puhelu]()
  
  def lisaaPuhelu(uusiPuhelu: Puhelu) = {
    this.soitetut = uusiPuhelu :: soitetut
  }

  def kokonaishinta = this.soitetut.map( _.kokonaishinta ).sum
  
  def erittely = "LASKU --- " + this.asiakas + ":" + this.soitetut.map( "\n - " + _.kuvaus ).mkString("")
    
}
