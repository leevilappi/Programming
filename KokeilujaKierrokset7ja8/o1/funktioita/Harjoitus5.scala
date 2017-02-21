package o1.funktioita
import scala.collection.mutable.Buffer
// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 7.3.


object Harjoitus5 extends App {

  // TEHTÄVÄN VAIHE 1:
  // Toteuta koostaTulosAlkioista-funktio sellaiseksi, että se muodostaa tuloksen
  // soveltamalla kolmanneksi parametriksi annettua funktiota niihin lukuihin,
  // jotka ensimmäiseksi parametriksi annettu kokoelma sisältää. Toinen parametri
  // kertoo alkuarvon (joka palautetaan, mikäli lukuja ei ole lainkaan).
  //
  // Esimerkiksi jos "lukuja" sisältää luvut 20, 10 ja 5, "alkuarvo" on 0 ja
  // "operaatio" on funktio, joka palauttaa parametriensa summan, niin:
  //   1. Sovelletaan parametriksi saatua "operaatio"-funktiota ensin
  //      alkuarvoon ja ensimmäiseen lukuun. 0 + 20 on 20.
  //   2. Sovelletaan operaatiota sitten äskeiseen tulokseen ja seuraavaan
  //      kokoelman alkioon. 20 + 10 = 30.
  //   3. Sovelletaan operaatiota taas äskeiseen tulokseen ja seuraavaan alkioon.
  //      30 + 5 = 35.
  //   4. Viimeinenkin alkio on käsitelty, joten lopetetaan ja palautetaan 35.
  // Tämä esimerkki oli siis eräs tapa laskea alkioiden summa.
  def koostaTulosAlkioista(lukuja: Vector[Int], alkuarvo: Int, operaatio: (Int, Int) => Int) = {
    
    var tulos = alkuarvo
    
    if(lukuja.isEmpty){
      tulos
    }else{
      for(luku <- lukuja){
        tulos = operaatio(tulos, luku)
      }
    }
    tulos

  }

  def laskeSumma(vanhaTulos: Int, seuraavaLuku: Int) = vanhaTulos + seuraavaLuku


  val esimerkkilukuja = Vector(100, 25, -12, 0, 50, 0)

  println("Luvut ovat: " + esimerkkilukuja.mkString(", "))

  println("Summa: " + koostaTulosAlkioista(esimerkkilukuja, 0, laskeSumma)) // pitäisi tulostaa: Summa: 163

  
  // TEHTÄVÄN VAIHE 2:
  // Käytä koostaTulosAlkioista-funktiota laskeaksesi:
  //   - montako positiivista lukua on esimerkkilukujen joukossa, ja
  //   - mikä on kaikkien esimerkkilukujen tulo (kuitenkin ohittaen nolla-alkiot!).
  // Nimeä funktiot näin: laskePositiiviset ja laskeTulo.
  // Korvaa alla olevat nollat sopivanlaisilla koostaTulosAlkioista-kutsuilla.
  
  def laskePositiiviset(luku1: Int, luku2: Int): Int = {
    var counter = luku1
    if(luku2 > 0){
      counter += 1
    }
    
    counter
  }
  
  def laskeTulo (vanhaTulos: Int, seuraavaLuku: Int)={
    var tulos = vanhaTulos
    
    if(vanhaTulos == 0){
      tulos = 1
    }
    
    if(seuraavaLuku != 0){
      tulos = tulos * seuraavaLuku
    }
    if(tulos == 1){
      tulos = 0
    }
    tulos
  }
  
  
  
  val montakoPositiivista =  koostaTulosAlkioista(esimerkkilukuja, 0, laskePositiiviset)
  val lukujenTulo = koostaTulosAlkioista(esimerkkilukuja, 0, laskeTulo)      
  println("Positiivisia: " + montakoPositiivista + " kpl") // pitäisi tulostaa: Positiivisia: 3 kpl
  println("Tulo: " + lukujenTulo)                          // pitäisi tulostaa: Tulo: -1 500 000 
                                                                                      

}
