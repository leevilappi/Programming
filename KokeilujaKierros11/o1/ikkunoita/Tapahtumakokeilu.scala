package o1.ikkunoita

import scala.swing._
import scala.swing.event._

// TÄMÄ OHJELMA ON SELITETTY KURSSIMATERIAALIN LUVUSSA 11.2. 

object Tapahtumakokeilu extends SimpleSwingApplication {

  // Pääkomponentit:
  
  val ekaNappi = new Button("Tästä sopii painaa")
  val tokaNappi = new Button("Tästä myös")
  val kehote = new Label("Paina jompaakumpaa napeista.")
  
  // Komponenttien asemointi ikkunaan:
  
  val kaikkiJutut = new BoxPanel(Orientation.Vertical)
  kaikkiJutut.contents += kehote
  kaikkiJutut.contents += ekaNappi
  kaikkiJutut.contents += tokaNappi

  val nappulaikkuna = new MainFrame
  nappulaikkuna.contents = kaikkiJutut 
  nappulaikkuna.title = "Kokeiluikkuna"
  
  // Tapahtumat:

  this.listenTo(ekaNappi)
  this.listenTo(tokaNappi)
  this.reactions += {
    case painallus: ButtonClicked =>
      Dialog.showMessage(kaikkiJutut, "Painoit nappia, jossa lukee: " + painallus.source.text, "Viesti")
  }
    
  // Sovelluksen pääikkunan palauttava metodi:
    
  def top = this.nappulaikkuna

} 

