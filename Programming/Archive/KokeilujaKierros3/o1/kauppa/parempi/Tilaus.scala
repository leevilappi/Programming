package o1.kauppa.parempi

// TÄMÄ LUOKKA ON SELITETTY LUVUSSA 3.1.

class Tilaus(val numero: Int, val tilaaja: Asiakas) {
  
  private var kokonaishinta = 0.0   // kokooja
  
  def lisaaTuote(kappalehinta: Double, lukumaara: Int) = {
    this.kokonaishinta = this.kokonaishinta + kappalehinta * lukumaara
  } 
  
  override def toString = "tilaus " + this.numero + ", tilaaja: " + this.tilaaja + ", yhteensä " + this.kokonaishinta + " euroa" 
  
}