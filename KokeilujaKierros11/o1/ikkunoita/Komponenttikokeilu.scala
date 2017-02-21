package o1.ikkunoita

import scala.swing._

// TÄMÄ OHJELMA ON SELITETTY KURSSIMATERIAALIN LUVUSSA 11.2. 

object Komponenttikokeilu extends SimpleSwingApplication {

  // Pääkomponentit:
  
  val ekaNappi = new Button("Tästä sopii painaa")
  val tokaNappi = new Button("Tästä myös")
  val kehote = new Label("Paina jompaakumpaa napeista.")
  
  // Komponenttien asemointi ikkunaan:
  
  val kaikkiJutut = new BoxPanel(Orientation.Horizontal)
  kaikkiJutut.contents += kehote
  kaikkiJutut.contents += ekaNappi
  kaikkiJutut.contents += tokaNappi

  val nappulaikkuna = new MainFrame
  nappulaikkuna.contents = kaikkiJutut 
  nappulaikkuna.title = "Kokeiluikkuna"
  
  // Sovelluksen pääikkunan palauttava metodi:
    
  def top = this.nappulaikkuna

} 

