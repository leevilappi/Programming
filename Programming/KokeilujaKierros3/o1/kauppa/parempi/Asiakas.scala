package o1.kauppa.parempi

// TÄMÄ LUOKKA ON SELITETTY LUVUSSA 3.1.

class Asiakas(val nimi: String, val asiakasnumero: Int, val email: String, val osoite: String) {

  override def toString = "#" + this.asiakasnumero + " " + this.nimi + " <" + this.email + ">" 

}




