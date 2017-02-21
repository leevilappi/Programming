package o1.rekursio

// TÄMÄN TIEDOSTON SISÄLTÖ ON SELITETTY KURSSIMATERIAALIN LUVUSSA 11.3.

abstract class Kurssi(val nimi: String, val op: Int) {

  def kokonaisOp: Int 
  
  override def toString = this.nimi + " (" + this.op + "op)"
  
} 


class Johdantokurssi(nimi: String, op: Int) extends Kurssi(nimi, op) {
  
  def kokonaisOp = this.op

}


class Jatkokurssi(nimi: String, op:Int, val esitieto: Kurssi) extends Kurssi(nimi, op) {
 
  def kokonaisOp = this.esitieto.kokonaisOp + this.op
  
  def tulostaNelioita(ylaraja: Int): Unit = {
    if (ylaraja > 0) {
      tulostaNelioita(ylaraja)
      println(ylaraja * ylaraja)
  }
}
}





// Kurssi2 on toisenlainen toteutus yllä olevalle kolmiluokkaiselle mallille:

class Kurssi2(val nimi: String, val op: Int, val esitieto: Option[Kurssi2]) {

  def kokonaisOp: Int = this.op + this.esitieto.map( _.kokonaisOp  ).getOrElse(0)
  
  override def toString = this.nimi + " (" + this.op + "op)"
  
} 

