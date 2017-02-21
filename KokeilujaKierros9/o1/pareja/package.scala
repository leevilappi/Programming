package o1

// TÄMÄ KOODI LIITTYY LUKUUN 9.1.

package object pareja {

  // Tämä esimerkkifunktio käsitellään luvussa 9.1.
  def ekaEiIsompi(lukupari: (Int, Int)) = lukupari._1 <= lukupari._2
  
 
  // Kirjoita pyydetty ohjelmakoodi tänne.
  def minuuteiksiJaSekunneiksi(aika: Int) = {
    var result = (aika/60, aika % 60 )
    result
  }
  
  def onJarjestyksessa(syote: Vector[Int]) = (syote, syote.tail).zipped.forall(_ <= _)
  
}