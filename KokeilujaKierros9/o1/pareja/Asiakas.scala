package o1.pareja

// TÄMÄ LUOKKA LIITTYY LUKUJEN 9.1 ja 9.4 ESIMERKKEIHIN.

class Asiakas(val numero: Int, val nimi: String, val syntynyt: Int, val kuollut: Option[Int]) {
  override def toString = this.nimi + "(" + this.syntynyt + "-" + kuollut.getOrElse("") + ")"
}
