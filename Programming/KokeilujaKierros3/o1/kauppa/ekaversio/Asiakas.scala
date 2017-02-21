package o1.kauppa.ekaversio

// TÄMÄ LUOKKA ON SELITETTY LUVUSSA 3.1. Tässä versiossa ei ole toString-metodia kuten pakkauksen o1.kauppa.parempi versiossa.

class Asiakas(val nimi: String, val asiakasnumero: Int, val email: String, val osoite: String) {
  
  def kuvaus = "#" + this.asiakasnumero + " " + this.nimi + " <" + this.email + ">" 

}




