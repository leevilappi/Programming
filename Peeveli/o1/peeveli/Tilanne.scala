package o1.peeveli

import Tilanne.Piilokirjain


/** Kukin luokan `Tilanne` ilmentymä kuvaa yhtä Peeveli-hirsipuupelin pelitilannetta:
  * Minkä näköinen on arvaajalle näytettävä (osin paljastettu) piilosana? Montako 
  * arvausta on jäljellä? Mitä arvauksia on jo tehty? Sekä huijaavalle Peevelille 
  * tärkeä tieto: Mitkä kaikki sanat ovat edelleen mahdollisia vastauksia?  
  *
  * Peeveli-pelin toiminta on selitetty tarkemmin kurssimateriaalin luvussa 10.1.
  *
  * Vaikka pelin aikana pelitilanne vaihteleekin, on kukin `Tilanne`-olio tilaltaan täysin 
  * muuttumaton. Uusi pelitilanne muodostetaan uutena `Tilanne`-oliona kutsumalla vanhalle 
  * tilanteelle `arvaa`-metodia. (Tämä luokka edustaa funktionaalista ohjelmointityyliä.)
  *
  * @param vaariaSallitaan  se määrä vääriä arvauksia, joka vielä sallitaan ennen pelin päättymistä.
  *                         Negatiivinen luku tarkoittaa, että peli on päättynyt.
  * @param arvatut          merkkijono, joka sisältää järjestyksessä kaikki toistaiseksi arvatut merkit
  * @param piilosana        merkkijono, jossa on piilosanan arvaajalle näkyvä muoto. Pelin alussa 
  *                         piilosanassa on vain piilossa olevia merkkejä (ks. [[Tilanne.Piilokirjain]]), 
  *                         mutta merkkejä paljastuu vähitellen.
  * @param sopivatSanat     kaikki ne käytetyn sanaston sanat, jotka sopivat yhteen `piilosana`-parametrin 
  *                         kanssa eli ovat mahdollisia oikeita vastauksia */
class Tilanne(val vaariaSallitaan: Int, val arvatut: String, val piilosana: String, val sopivatSanat: Vector[String])  {
  
  
  /** Luo uuden `Tilanne`-olion, joka kuvaa juuri alkaneen uuden `Peeveli`-pelin tilaa.
    * Pelin alkaessa koko piilosana on vielä piilossa ja kaikki annetun sanaston sopivan  
    * mittaiset sanat ovat mahdollisia oikeita ratkaisuja.
    *
    * Lisätieto opiskelijoille: Huomaa, miten tämä '''toinen konstruktori''' on määritelty 
    * annetussa ohjelmakoodissa. Tällä tavoin voidaan määritellä vaihtoehtoinen tapa luoda 
    * `Tilanne`-olio sen "oletustavan" lisäksi, joka on määritelty luokan otsikkorivillä. 
    * Voidaan siis luoda tilanneolio joko käskyllä `new Tilanne(arvauksia, arvatut, piilosana, sopivat)` 
    * (oletustapa) tai käskyllä `new Tilanne(arvauksia, pituus, sanasto)`. 
    *
    * @param arvauksiaKaytettavissa  se määrä vääriä arvauksia, joka yhteensä sallitaan ennen pelin päättymistä
    * @param pituus                  uuden piilosanan pituus
    * @param sanasto                 sanasto, jonka `pituus`-mittaiset sanat ovat mahdollisia oikeita vastauksia */
  def this(arvauksiaKaytettavissa: Int, pituus: Int, sanasto: Vector[String]) = { 
    // Seuraava tarkoittaa: luo olio käyttäen "oletustapaa" ja antaen seuraavat konstruktoriparametrit:
    this(arvauksiaKaytettavissa, "", Piilokirjain.toString * pituus, sanasto.map( _.toUpperCase ))
  }

  private def eiPiilokirjaimia: Boolean = {
    this.piilosana.find { x => x == Piilokirjain }.isEmpty
  }

  
  /** Palauttaa piilosanan pituuden. */
  def sananPituus = this.piilosana.length 
  
  
  /** Palauttaa niiden käytetystä sanastosta löytyvien sanojen lukumäärän, jotka ovat 
    * (edelleen) mahdollisia ratkaisuja piilosanaan. */
  def sopiviaSanojaJaljella = this.sopivatSanat.size 

  
  /** Palauttaa `true` jos arvaaja on arvannut väärin jo enemmän kertoja kuin sallittiin  
    * ja on siis hävinnyt pelin; palauttaa `false`, jos näin ei ole. */
  def onTappio = if(this.vaariaSallitaan < 0) true else false

  
  /** Palauttaa `true`, jos arvaaja on voittanut pelin eli ei ole arvannut liian monta kertaa väärin 
    * ja kaikki piilosanan kirjaimet ovat näkyvissä; muutoin palauttaa `false`. */
  def onVoitto = if(!this.onTappio && eiPiilokirjaimia ) true else false
  
  
  /** Palauttaa piilosanasta sellaisen version, josta on paljastettu osoitetut merkit. Esimerkiksi 
    * jos piilosana on `"K___A"` ja parametrimerkkijono on `"__SS_"`, palauttaa `"K_SSA"`. */
  private def paljasta(paljastettavat: String) = {
    var uusiPiilosana = ""                                
    for (indeksi <- this.piilosana.indices) {
      if (paljastettavat(indeksi) != Piilokirjain) {
        uusiPiilosana += paljastettavat(indeksi)    
      } else {
        uusiPiilosana += this.piilosana(indeksi) 
      } 
    }
    uusiPiilosana
  }

  
  /** Palauttaa uuden pelitilanteen, joka seuraa nykyisestä, kun arvaaja arvaa annetun merkin.
    * Uusi pelitilanne valitaan periaatteella, joka on selostettu kurssimateriaalin luvussa 10.1. 
    * Uudessa tilanteessa on ainakin yksi tehty arvaus enemmän kuin nykyisessä; lisäksi siinä saattaa 
    * olla enemmän näkyviä merkkejä piilosanassa, vähemmän vääriä arvauksia jäljellä ja/tai vähemmän 
    * mahdollisia oikeita vastauksia.
    * @param arvaus  viimeksi arvattu merkki; voi olla iso tai pieni kirjain, mutta tulkitaan aina isoksi.
    * @return uusi pelitilanne */
  def arvaa(arvaus: Char) = {
    import scala.collection.mutable.Buffer
    val arvausIsona = arvaus.toUpper
    
    //Kertoo arvatun kirjaimen indeksit sanassa
    def indeksit(sana: String): Vector[Int] = {
      var indeksit: Buffer[Int] = Buffer()
      var indeksi = sana.indexOf(arvausIsona)
      while(indeksi >= 0){
        indeksit += indeksi
        indeksi = sana.indexOf(arvausIsona, indeksi + 1 )
      }
      indeksit.toVector
    }
    
    val(sisaltaaKirjaimen, eiKirjainta) = this.sopivatSanat.partition( _.contains(arvausIsona))    
    
    //Palauttaa sanalistan, jossa arvatut kirjaimet ensin jaotellaan sanalistoihin sen mukaan, montako kertaa niissä 
    //esiintyy arvattu kirjain. Listoista valitaan suurin. 
    var isoinLista = this.sopivatSanat.groupBy(x => x.count(_ == arvausIsona) ).values.maxBy( _.size)

    //Järjestää sanalistan indeksien mukaan, valitsee joukon, joka on suurin
    val isoinSijainninMukaan = isoinLista.groupBy(x => indeksit(x) ).values.maxBy(_.size)
     
    val ratkaisu: String= {
      
      if(isoinSijainninMukaan.size > eiKirjainta.size) {
        
       var sana = isoinSijainninMukaan.head
       var sijainnit = indeksit(sana)
       var paljastettavat = piilosana
       var test = paljastettavat.toBuffer
       var paljastettavaSana = ""
       
       for(i <- sijainnit){
         test(i) = arvausIsona
       }
    
       for(k <- 0 until test.size){
         paljastettavaSana += test(k)
       }
       
       paljastettavaSana
     
      }else{
        piilosana
      }
     
    }
    isoinLista = if(isoinSijainninMukaan.size > eiKirjainta.size) isoinSijainninMukaan else eiKirjainta 
    if(isoinSijainninMukaan.size > eiKirjainta.size){
      new Tilanne(this.vaariaSallitaan, this.arvatut + arvausIsona, ratkaisu, isoinLista)
    }else{
      new Tilanne(this.vaariaSallitaan - 1, this.arvatut + arvausIsona, ratkaisu, isoinLista)
    }
    

  }
  
  
  /** Palauttaa tekstimuotoisen kuvauksen tästä pelitilanteesta. */
  override def toString = 
    this.piilosana + ", " + 
    "vääriä sallitaan vielä: " + this.vaariaSallitaan + ", " + 
    "arvatut: " + (if (this.arvatut.isEmpty) "ei ole" else this.arvatut) + ", " + 
    "vaihtoehtoja: " + this.sopiviaSanojaJaljella
  
}


/** Tämä `Tilanne`-luokan kumppaniolio vain tarjoaa yhden vakioarvon. 
  * @see [[Tilanne]]-luokka */
object Tilanne {
  
  /** merkki, jota käytetään piilossa olevien kirjainten merkitsemiseen Peeveli-pelissä */
  val Piilokirjain = '_'
    
}
