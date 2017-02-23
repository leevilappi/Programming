
// NÄISTÄ ALKUMÄÄRITTELYISTÄ EI TARVITSE TÄSSÄ VAIHEESSA VÄLITTÄÄ MITÄÄN.
// Ne liittyvät siihen, miten nämä funktiot on sijoitettu pakkaukseen; aiheesta luvussa 4.5.   
package o1
object omiafunktioita {


  // LUVUT 1.7 JA 1.8: KIRJOITA OMAT FUNKTIOSI NÄIDEN import-KÄSKYJEN ALLE.  
  import scala.collection.mutable.Buffer // Tämän johdosta puskureita voi käyttää alla ilman pitkää pakkauksen nimeä.
  import scala.math._                    // Otetaan myös matemaattiset kirjastofunktiot käyttöön.
    
  def metreiksi(jalkaa: Double, tuumaa: Double) = (((jalkaa) * 12 + tuumaa) * 2.54)/100

  def rivi(ruutu: Int) = ((ruutu - 1) / 8) 
  def sarake(ruutu: Int) = ((ruutu - 1) % 8) + 1

  def jalat(m: Double) = {
	  import scala.math.floor
	  floor(m * 100 / 2.54 / 12 )
  }
  
  def tuuma(m: Double) = m * 100 / 2.54 % 12
  
  def saesta(lause: String, nuotit: String) = {
	  o1.soita(nuotit)
	  println(lause)
  }
  
  def kurssiarvosana(tehtava: Int, tenttiBonus: Int, aktiiviBonus: Int) = {
  	min(tehtava + tenttiBonus + aktiiviBonus, 5)
}
  
  def pisteet (voitot: Int, tasapelit: Int) = {
  val pisteTilanne = voitot * 3 + tasapelit 
  pisteTilanne
  }
  
  def joukkueenTiedot (joukkueenNimi: String, voitot: Int, tasapelit: Int, tappiot: Int) = {
  val lkm = voitot + tasapelit + tappiot
  val tVoitto = voitot + "/" + lkm + " voittoa, "
  val tTasapeli = tasapelit + "/" + lkm + " tasapeliä, "
  val tTappio = tappiot + "/" + lkm + " tappiota, "
  val tPisteet = pisteet(voitot, tasapelit) + " pistettä"

  joukkueenNimi + ": " + tVoitto + tTasapeli + tTappio + tPisteet
  }
  
  
  def sanallinenArvosana(tehtavaarvosana: Int, tenttibonus: Int, aktiivisuusbonus: Int) = {
  val kuvaukset = Buffer("hylätty", "välttävä", "tyydyttävä", "hyvä", "erittäin hyvä", "erinomainen")
  
  var pisteet = min(tehtavaarvosana + tenttibonus + aktiivisuusbonus, 5)
  var arvosana = max(pisteet, 0)
  val kirjallinenArvosana = kuvaukset(arvosana)
  kirjallinenArvosana
  }
  
  def tuplaaPisteet(pisteet: Buffer[Int], pelaaja: Int) = {
  pisteet(pelaaja - 1) = pisteet(pelaaja - 1) * 2
  pisteet(pelaaja - 1)
  }
  
  def sakko(pisteet: Buffer[Int], pelaajaValinta: Int, vahennys: Int) = {
  val pelaaja = pelaajaValinta - 1
  pisteet(pelaaja) = max(pisteet(pelaaja) - vahennys, 1)
  pisteet(pelaaja)
  }

  
  // Alla on yhteen luvun 1.7 tehtävistä liittyvä virheellinen koodi, joka korjataan tehtävässä.
  def kahdella(melodia: String, eka: Int, toka: Int, tauonPituus: Int) = {
    val melodiaEkalla = "[" + eka + "]" + melodia
    val melodiaTokalla = "[" + toka + "]" + melodia
    val tauko = " " * tauonPituus
    val kahdestiSoitettuna = melodiaEkalla + tauko + melodiaTokalla
  }
  
  // Alla on valmis pohja luvun 1.8 toiseen tehtävään. Muissa kohdissa kirjoitat funktiot tyhjästä.
  // Huomaa: tästä pohjasta puuttuu varsinainen ratkaisu mutta lisäksi siinä on yksi pieni mutta vakava virhe. 
  def sanallinenArvosana(tehtavaarvosana: Int, tenttibonus: Int, aktiivisuusbonus: Int) {
    val kuvaukset = Buffer("hylätty", "välttävä", "tyydyttävä", "hyvä", "erittäin hyvä", "erinomainen")
    // TÄYDENNÄ RATKAISUSI TÄHÄN. VOIT POISTAA TÄMÄN KOMMENTIN.
  }
  
  
  

  
  // TÄSSÄ ESIMERKKIFUNKTIOITA, JOIDEN TOTEUTUSTA KATSOTAAN LUVUISSA 1.7 JA 1.8.
  // NE ON SELITETTY TARKEMMIN LUKUJEN TEKSTISSÄ.

  def keskiarvo(eka: Double, toka: Double) = (eka + toka) / 2 

  def huuda(lausahdus: String) = lausahdus + "!"
    
  def haukiOnKala(loppukaneetti: String) = {
    println("Kun hauki on vähärasvainen, sitä voidaan säilyttää pakastettuna jopa 6 kuukautta.")
    println("Vertailun vuoksi mainittakoon, että haukea rasvaisemman lahnan vastaava")
    println("säilymisaika on vain puolet eli 3 kuukautta.")
    println(loppukaneetti)
  }
  
  def piiri(sade: Double) = 2 * Pi * sade          // ei nyt käytössä luvuissa  
   
  def etaisyys(x1: Double, y1: Double, x2: Double, y2: Double) = hypot(x2 - x1, y2 - y1)
  
  def isoinEtaisyys(x1: Double, y1: Double, x2: Double, y2: Double, x3: Double, y3: Double) = {
    val eka = etaisyys(x1, y1, x2, y2)
    val toka = etaisyys(x1, y1, x3, y3)
    val kolmas = etaisyys(x2, y2, x3, y3)
    max(max(eka, toka), kolmas)
  }
  
  def verot(tulot: Double, tuloraja: Double, perusprosentti: Double, lisaprosentti: Double) = {
    val perusosa = min(tuloraja, tulot)
    val lisaosa = max(tulot - tuloraja, 0)
    perusosa * perusprosentti + lisaosa * lisaprosentti
  } 

  def kokeilu1(luku: Int) = {
    println("Luku on: " + luku)
  }

  def kokeilu2(lukuja: Buffer[Int]) = {
    lukuja(0) = 100
  }
  
  def kokeilu3(luku: Int) = {
    println("Luku on: " + luku)
    luku + 1
  }

  def kokeilu4(sana: String) = {
    var luku = 1
    println(sana + ": " + luku)
    luku = luku + 1
    println(sana + ": " + luku)
    luku = luku + 1
    println(sana + ": " + luku)
    luku
  }
  
  def kokeilu5(aluksi: Int) = {
    var luku = aluksi
    luku = luku + 1
    luku = luku + 1
    luku = luku + 1
    luku
  }
    
  def testi1(teksti: String) = {
    println(teksti)
    "aina tämä"
  }

  def testi2(teksti: String) = {
    println(teksti)
    val vastaus = testi1(huuda(teksti))
    testi1(teksti)
    println("saatiin:")
    println(vastaus)
  }
  
  
}