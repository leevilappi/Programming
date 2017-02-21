package o1.autoilu


import scala.collection.mutable.Buffer

// TÄMÄ OHJELMAKOODI LIITTYY KURSSIMATERIAALIN LUKUUN 6.4.  

object Autoilutarina extends App {
  
  val auto = new Auto
  auto.otaSisaan(new Koululainen("K. Koululainen"))
  auto.otaSisaan(new KemianTeekkari)
  auto.otaSisaan(new Koneteekkari)
  auto.otaSisaan(new Sahkoteekkari)
  auto.otaSisaan(new Tietoteekkari)

  auto.kaynnista()
}

class Auto {
  
  private val matkustajat = Buffer[Matkustaja]()
  
  def otaSisaan(matkustaja: Matkustaja) = {
    matkustaja.istuudu()
    this.matkustajat += matkustaja
  }
  
  def kaynnista() = {
    println("(Auto ei käynnisty.)")
    for (matkustaja <- this.matkustajat) {
      matkustaja.kommentoi()
    }
  }  
  
}


abstract class Matkustaja(val nimi: String) {

  def istuudu() = {
    println(this.nimi + " istuu autoon.")
  }
 
  def puhu(lause: String) = {
    println(this.nimi + ": " + lause)
  }
  
  def diagnoosi: String
  
  def kommentoi() = {
    this.puhu(this.diagnoosi)
  }
  
}

abstract class Opiskelija(nimi: String) extends Matkustaja(nimi) {
    
  def diagnoosi = "Ei aavistustakaan, mikä on vikana."

}


class Koululainen(nimi: String) extends Opiskelija(nimi)


abstract class Teekkari(nimi: String) extends Opiskelija(nimi) {
  
  override def kommentoi() = {
    super.kommentoi()
    this.puhu("Näin on.")
  }
  
}


class KemianTeekkari extends Teekkari("K. Kemisti") {

  override def diagnoosi = "Väärät oktaanit, ei olis pitänyt päästää koneteekkaria tankkaamaan."  
  
}


class Koneteekkari extends Teekkari("K. Koneteekkari") {

  override def diagnoosi = "Tankkaus on ihan kunnossa. Mäntä on leikannut kiinni."

  override def puhu(lause: String) = {
    super.puhu(lause.replace(".", "!"))
  }
    
}

class Sahkoteekkari extends Teekkari("S. Sähköteekkari") {

  override def istuudu() = {
    println(this.nimi + " loikkaa sisään.")
  }

  override def diagnoosi = "Höpsis. Sytytystulpat ei tuota kipinää."
  
}

class Tietoteekkari extends Teekkari("T. Tietoteekkari") {
 
  override def kommentoi() = {
    this.puhu("Ei mitään hajua, mikä mättää.")
    this.puhu(this.diagnoosi)
  }
  
  override def diagnoosi = "Noustaan kaikki autosta ja sitten takaisin sisään ja koitetaan käynnistää uudestaan."
  
}


