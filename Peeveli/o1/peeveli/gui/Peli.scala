package o1.peeveli.gui

import scala.swing._
import javax.swing.UIManager
import o1.peeveli.Tilanne
import scala.io.Source
import o1.gui.Dialog._
import o1.gui.layout._
import o1.util.useAndClose
import java.awt.Font
import java.awt.font.TextAttribute
import scala.swing.Swing._
import java.awt.Color
import scala.swing.event._

////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////


/** The singleton object `Peli` represents the Peeveli application. The object serves 
  * as an entry point for the game, and can be run to start up the user interface. 
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this object works 
  * or can be used, apart from the fact that you can use this file to start the program.''' */
object Peli extends SimpleSwingApplication {

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName) 
  UIManager.put("OptionPane.cancelButtonText", "Peruuta")
  
  def top = new MainFrame {
    
    type Sanasto = Map[Int, Vector[String]]
    
    this.title = "Hirsipuu"
    this.location = new Point(100, 100)
    this.resizable = false 
 
    var debugtulosteet = false
    val sanastot = Map("suomi" -> "sanastot/kotus/suomen_sanoja.txt", 
                       "testi: AAVA, AIKA, AIVO, ALLI, KULU, RIMA, RÄKÄ, SOLA, SUMU, TAAS" -> "sanastot/kotus/testisanat.txt",
                       "englanti" -> "sanastot/dict/english_words.txt")
    val Oletussanasto = "suomi"
    
      
    def lataaSanasto(sanastonNimi: String) = {
      val sanasto = Source.fromFile(sanastot(sanastonNimi))
      useAndClose(sanasto)( tiedosto => tiedosto.getLines.toVector.groupBy( _.length ) )
    }
    
    
    def uusiPeli(sanasto: Sanasto) = {
      for {
        pituus    <- if (sanasto.size > 1) requestInt("Montako merkkiä haluat sanaan (pitkät ovat helpompia)?", sanasto.contains(_), "En tunne sanoja, joissa olisi tuo määrä merkkejä.", RelativeTo(this)) 
                     else                  sanasto.keys.headOption
        arvauksia <- requestInt("Montako väärää arvausta sallitaan (perinteisesti viisi)?", _ >= 0, "Anna ei-negatiivinen kokonaisluku.", RelativeTo(this))
      } yield new Tilanne(arvauksia, pituus, sanasto(pituus))
    }
    
    
    val piilosana = new Label {
      val attrib = new java.util.HashMap[TextAttribute, Double]
      attrib.put(TextAttribute.TRACKING, 0.5)
      this.font = new Font("Monospaced", Font.PLAIN, 25).deriveFont(attrib)
      border = EmptyBorder(25, 25, 25, 25)
    }
    
    val kehote = new Label
    val palaute = new Label
    val tehdytArvaukset = new TextField(50) {
      this.editable = false
      this.focusable = false  
      this.border = EmptyBorder
    }
    val uusintatoiminto = Action("Uusi peli...") { 
      for (peli <- uusiPeli(sanasto)) { 
        tilanne = peli
        alustaPelinakyma(tilanne)
      }
    }
    
    
    val uusintanappi = new Button(uusintatoiminto)
    val hirsipuu = new Peevelinaytto
    this.defaultButton = uusintanappi

    this.contents = new EasyPanel {
      this.border = EmptyBorder(20, 20, 20, 20)
      placeC(piilosana,                     (0, 0), OneSlot, Slight, (0, 0, 0, 0))
      placeW(kehote,                        (0, 1), OneSlot, Slight, (2, 2, 2, 2))
      placeNW(palaute,                      (0, 2), OneSlot, Slight, (0, 0, 0, 0))
      placeNW(new Label("Arvatut merkit:"), (0, 3), OneSlot, Slight, (0, 0, 0, 0))
      placeNW(tehdytArvaukset,              (0, 4), OneSlot, Slight, (0, 0, 0, 0))
      placeC(Swing.VStrut(140),             (0, 5), TwoHigh, Slight, (0, 0, 0, 0))
      placeC(hirsipuu,                      (0, 5), OneSlot, Slight, (20, 0, 0, 0))
      placeC(uusintanappi,                  (0, 6), OneSlot, Slight, (20, 0, 0, 0))
      
      focusable = true
      listenTo(this.keys)
      reactions += {
        case KeyTyped(_, char, _, _) =>
          if (onKaynnissa) {
            arvaa(char)
          }
      }
    }
    
    this.menuBar = new MenuBar {
      contents += new Menu("Peli") {
        contents += new MenuItem(uusintatoiminto)
        contents += new MenuItem(Action("Lopeta") { dispose() })
      }
      contents += new Menu("Sanasto") {
        val sanastovalinnat = for (nimi <- sanastot.keys.toSeq.sorted) yield {
          new RadioMenuItem(nimi) { 
            selected = (nimi == Oletussanasto)
            action = Action(nimi) { 
              sanasto = lataaSanasto(nimi)
              uusintatoiminto.apply()
            } 
          }
        }
        val sanastoryhma = new ButtonGroup(sanastovalinnat: _*)
        contents ++= sanastoryhma.buttons      
      }
      contents += new Menu("Testaus") {
        contents += new CheckMenuItem("") {
          action = Action("Tulosta sanalistat tekstikonsoliin") {
            debugtulosteet = this.selected 
            aputuloste(tilanne)
          }
        } 
      }
    }

    var sanasto = lataaSanasto(Oletussanasto)
    var tilanne = new Tilanne(5, 10, sanasto(10))
    this.alustaPelinakyma(tilanne)
    

    def aputuloste(tilanne: Tilanne) = {
      if (this.debugtulosteet) {
        println(tilanne.sopiviaSanojaJaljella + " sopivaa sanaa: " + tilanne.sopivatSanat.mkString(", ").take(5000))
      }
    }
    
    
    def alustaPelinakyma(tilanne: Tilanne): Unit = {
      this.kehote.text = "Mitä " + tilanne.sananPituus + "-merkkistä sanaa ajattelen? Arvaa kirjain näppäintä painamalla."
      this.palaute.text = "Löytämäsi merkit ilmestyvät tuohon yläpuolelle."
      this.palaute.foreground = Color.black
      this.tehdytArvaukset.text = "(ei vielä arvauksia)"
      this.piilosana.text = tilanne.piilosana
      this.hirsipuu.paivita(tilanne)
      this.uusintanappi.visible = !this.onKaynnissa
      this.aputuloste(this.tilanne)  
      this.pack()
    }
    
    
    def onKaynnissa = !this.tilanne.onTappio && !this.tilanne.onVoitto
    
    def muodostaPalaute(vanhaTilanne: Tilanne, uusiTilanne: Tilanne) = {
      val goodColor = Color.green.darker.darker.darker
      val badColor = Color.red.darker
      val arvauksia = uusiTilanne.vaariaSallitaan
      if (uusiTilanne.onVoitto) {
        this.palaute.text = "Oikein! Sana on: " + oikeaVastaus() + "!"
        this.palaute.foreground = goodColor
      } else if (arvauksia >= vanhaTilanne.vaariaSallitaan) {
        this.palaute.text = "Löytyi! Etsimääsi sanaa on päivitetty yllä!"
        this.palaute.foreground = goodColor
      } else if (uusiTilanne.onTappio) {
        this.palaute.text = "Hävisit. Ajattelin sanaa: " + oikeaVastaus() + "."
        this.palaute.foreground = badColor
      } else if (this.hirsipuu.visible) {
        val uhkakuva = "Kohta roikut hirressä. "
        val raportti = if (arvauksia > 0) "Virheitä sallitaan enää " + uusiTilanne.vaariaSallitaan + "." else "Seuraava virhe on viimeisesi."   
        this.palaute.text = uhkakuva + raportti
        this.palaute.foreground = badColor
      } else {
        this.palaute.text = "Ei löydy sanasta! Vääriä arvauksia sallitaan vielä " + uusiTilanne.vaariaSallitaan + " kpl."
        this.palaute.foreground = badColor
      }
    }
    
    
    def oikeaVastaus() = {
      import scala.util.Random
      val uskottavat = this.tilanne.sopivatSanat
      uskottavat(Random.nextInt(uskottavat.size))
    }
    
    
    def arvaa(arvaus: Char): Unit = {
      val vanhaTilanne = this.tilanne
      this.tilanne = this.tilanne.arvaa(arvaus)
      this.tehdytArvaukset.text = this.tilanne.arvatut.mkString(" ")
      this.piilosana.text = this.tilanne.piilosana
      this.hirsipuu.paivita(tilanne)
      this.muodostaPalaute(vanhaTilanne, this.tilanne)
      this.aputuloste(this.tilanne) 
      this.uusintanappi.visible = !this.onKaynnissa 
      this.pack()
    }  
  
    
    class Peevelinaytto extends Label(" ") {
      
      this.border = EmptyBorder(10, 10, 10, 10)
      
      this.font = new Font("Monospaced", Font.BOLD, 40)
      this.foreground = Color.red
      this.background = Color.black
      this.opaque = true
      
      val kokoTeksti = "PEEVELI!" 
      val paljastettavat = "!PLVIE"

      def paivita(tilanne: Tilanne) = {
        val montakoNaytetaan = paljastettavat.size - tilanne.vaariaSallitaan - 1
        this.visible = montakoNaytetaan > 0
        val nytNakyviin = this.paljastettavat.take(montakoNaytetaan)
        this.text = kokoTeksti.map( kirjain => if (nytNakyviin.contains(kirjain)) kirjain else ' ' )
      }
      
    }
  
  }
  
  
}

  
