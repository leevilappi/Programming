package o1.luokkia

class Puhelu(alkuhintaIlmanPvm: Double, minuuttihintaIlmanPvm: Double, val kesto: Double) {
  
  val VerkkomaksuAloitus = 0.0099
  val VerkkomaksuMinuutilta = 0.0199
  val alkuhinta = alkuhintaIlmanPvm + VerkkomaksuAloitus
  val minuuttihinta = minuuttihintaIlmanPvm + VerkkomaksuMinuutilta
   
  def kokonaishinta = this.alkuhinta + this.minuuttihinta * this.kesto
  
  def kuvaus =
    "%.2f min, %.5fe (%.5fe + %.5fe/min)".format(this.kesto, this.kokonaishinta, this.alkuhinta, this.minuuttihinta) 
    
}
