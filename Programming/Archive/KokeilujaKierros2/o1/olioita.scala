// NÄISTÄ ALKUMÄÄRITTELYISTÄ EI TARVITSE TÄSSÄ VAIHEESSA VÄLITTÄÄ MITÄÄN:
package o1
object olioita {
 

  // LUKU 2.2: KIRJOITA ITSE TILIOLION MÄÄRITTELY ALLE MERKITTYYN KOHTAAN.
  import scala.math._   // matematiikkafunktioista on tässä apua
  
  object tili {

   var saldo = 0
   val numero = "15903000000776FI00"

  def talleta(lisays: Int) = {
    saldo = saldo + max(lisays, 0)
  }
  
  def nosta(nosto: Int) = {
    saldo = max(saldo - max(nosto, 0), 0)
    nosto
  }

  }
    // Kirjoita koodisi tänne.
 

  

  

  
  
  
  
  
  
  // TÄMÄ ESIMERKKI KÄSITELLÄÄN LUVUSSA 2.2.  
 
  object tyontekija {

    val nimi = "Matti Mikälienen"
    val syntynyt = 1965
    var kkpalkka = 5000.0
    var tyoaika = 1.0 
    
    def ikaVuonna(vuosi: Int) = vuosi - this.syntynyt
    
    def kuukausikulut(kulukerroin: Double) = this.kkpalkka * this.tyoaika * kulukerroin
    
    def korotaPalkkaa(kerroin: Double) = {
      this.kkpalkka = this.kkpalkka * kerroin
    }
    
    def kuvaus = 
      this.nimi + " (s. " + this.syntynyt + "), palkka " + this.tyoaika + " * " + this.kkpalkka + " euroa" 

  } 
  
  
  
  
  
  
  // ALLA LUKUUN 2.1 LIITTYVIÄ ESIMERKKEJÄ. NÄIDEN SISÄISTÄ TOIMINTAA EI TARVITSE 
  // YMMÄRTÄÄ. KAIKKEA SEURAAVASTA KOODISTA EI MYÖSKÄÄN OLE KIRJOITETTU 
  // ALOITTELIJAYSTÄVÄLLISEEN TYYLIIN.
  
  // Esimerkki 1
  
  object papukaija {

    private var repertuaari = List("Huh hellettä ja rommia pullo", "Polly tahtoo keksin")
    
    def vastaa(kuultuLause: String) = {
      def siisti(lause: String) = lause.replaceAll(raw"[^\w åäöÅÄÖ]+", "")  
      def sanoiksi(lause: String) = siisti(lause).toLowerCase.split(" ").filter( _.length > 2 )
      val kuullutSanat = sanoiksi(kuultuLause)
      def samantapainen(sana: String, toinen: String) = o1.util.editDistance(sana, toinen, 1) <= 1
      def sisaltaaKuulluntapaisenSanan(lause: String) = 
        sanoiksi(lause).exists( tuttuSana => kuullutSanat.exists( samantapainen(_, tuttuSana)) )
      val valmisRepliikki = this.repertuaari.find(sisaltaaKuulluntapaisenSanan)
      val vastaus = valmisRepliikki.getOrElse(siisti(kuultuLause).takeWhile( _ != ' ' ))
      if (vastaus.nonEmpty) vastaus + "!" else "" 
    }
    
    def opiLause(uusiLause: String) = {
      this.repertuaari = uusiLause :: this.repertuaari
    }
    
  }

  
  
  
  
  // Esimerkki 2
  
  object radio {

    private val pikavalinnat = Vector(87900, 94000, 94900, 98500)
    private val asemat = Map(
      98500  -> ("Radio Helsinki", // with apologies to Claire Boucher:
                 "[33]<<" + "CC  CC  CC  CC  " * 2 + "&P:<<                  D   D   D   D &[39]<" + "cc>c<gccc>c<|cc>c<gc>c<gg" * 2 + "/150"),
      94000  -> ("Radio Suomi",    // with apologies to Fucked Up:
                 "[41]>g-g-gg-g-g#-g---f-f--gfd#-d#-----c-c-cc<hb-----hb>ddd--dd#-d#---/180"),
      87900  -> ("YLE 1",          // with apologies to Chairlift:
                 "[17]edc<a>c-d<a-------hageg-ad-c<a----/150"),
      94900  -> ("Radio Rock",     // with apologies to the old Ludwig Van:
                 "[2](<G>G)(<G>G)(<G>G)(<Eb>Eb)---------- (<F>F)(<F>F)(<F>F)(<D>D)-------------- &[2]<<(<G>G)(<G>G)(<G>G)(<Eb>Eb)-----------(<F>F)(<F>F)(<F>F)(<D>D)---------------/180"),  
      106200 -> ("Radio Nova",     // with apologies to Fiona Apple:
                 "[33]<<c# c#e# e#d de# e#c# c#e# e#c#&[46]<   g# g#   g# g#   g# g#>[113]  >E---"), 
      91900  -> ("YleX",           // with apologies to Heikki Kuula:
                 "[110]>E-E-EDC-<A-G->E-E-EDC-<A->C-<A-G-    >E-E-EGE-D-C-E-E-EGE-D-C-[112]e-d-/150")
    ).withDefaultValue(("kohinaa", ""))

    val pykalaKHz = 100 
    private var taajuus = pikavalinnat(1)

    def taajuusKHz_=(uusiTaajuus: Int) = {
      this.taajuus = uusiTaajuus
      o1.soita(this.biisiNyt)
    }
    
    def taajuusKHz = this.taajuus
    
    private def asemaNyt = this.asemat(this.taajuusKHz)._1
    private def biisiNyt = this.asemat(this.taajuusKHz)._2

    def virita(pykalat: Int) = {
      this.taajuusKHz += pykalaKHz * pykalat
      this.kuvaus
    }
    
    def valitse(monesko: Int) = {
      for (valittu <- this.pikavalinnat.lift(monesko - 1)) {
        this.taajuusKHz = valittu  
      }
      this.kuvaus
    }
    
    private def kuvaus = "%.1f MHz: ".format(this.taajuusKHz / 1000.0) + this.asemaNyt
  
  }
  
  
  
  // Esimerkki 3 (ei käytössä materiaalin nykyversiossa)
  
  object luola {
    val nimi = "Peikkola"
    
    def lahetaPeikko(arjahdys: String) = new Peikko(arjahdys)
  }
  
  class Peikko(val arjahdys: String) {
    val nimi = "iso" * Peikko.luotuja + "peikko"
    val ase = "karttu"
    
    Peikko.luotuja += 1
    
    override def toString = super.toString.replaceAll("package\\$", ".")
  }
   
  object Peikko {
    var luotuja = 0
  }

  


}  
  
  
