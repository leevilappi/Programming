package o1.luokkia

// TÄMÄ LUOKKA LIITTYY LUVUN 2.4 TEHTÄVÄÄN JA ON SELITETTY SIELLÄ.

class Suorakaide(annettuSivunPituus: Double, annettuToinenSivunPituus: Double) {

  val sivu1 = annettuSivunPituus
  val sivu2 = annettuToinenSivunPituus

  def ala = this.sivu1 * this.sivu2

  // jne. (Täällä voisi olla muita metodeita.)

}
