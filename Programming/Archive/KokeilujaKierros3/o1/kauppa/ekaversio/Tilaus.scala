package o1.kauppa.ekaversio

// TÄMÄ LUOKKA ON SELITETTY LUVUSSA 3.1. Tässä versiossa ei ole toString-metodia kuten pakkauksen o1.kauppa.parempi versiossa.

class Tilaus(val numero: Int, val tilaaja: Asiakas) {
  
  private var kokonaishinta = 0.0   // kokooja
  
  def lisaaTuote(kappalehinta: Double, lukumaara: Int) = {
    this.kokonaishinta = this.kokonaishinta + kappalehinta * lukumaara
  } 
  
  def kuvaus = "tilaus " + this.numero + ", tilaaja: " + this.tilaaja.kuvaus + ", yhteensä " + this.kokonaishinta + " euroa"  
  
}