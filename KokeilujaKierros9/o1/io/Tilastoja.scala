package o1.io

import scala.io.Source
import scala.io.StdIn._
import java.io.PrintWriter

// TÄMÄ TEHTÄVÄ ON ESITELTY KURSSIMATERIAALIN LUVUSSA 9.3.

object Tilastoja extends App {
  
  val tiedostonNimi = readLine("Syötetiedosto: ")
  
  // TÄYDENNÄ TIEDOSTONKÄSITTELYKOODISI TÄHÄN ALLE
  val syotetiedosto = Source.fromFile(tiedostonNimi)
  
  try{
    val input = syotetiedosto.getLines.toVector
 
    val lukuja = input.map(_.toDouble)
  
    println("Lukumäärä: " + lukuja.size)
    println("Keskiarvo: " + this.keskiarvo(lukuja))
    println("Mediaani: " + this.mediaani(lukuja))
    println("Yleisimmän määrä: " + this.yleisimmanMaara(lukuja))
  } finally{
    syotetiedosto.close()
    
  }
  
  
  // VOIT KÄYTTÄÄ NÄITÄ VALMIITA FUNKTIOITA:
  
  def keskiarvo(lukuja: Vector[Double]) = lukuja.sum / lukuja.size
  
  def mediaani(lukuja: Vector[Double]) = {
    val puolivali = lukuja.size / 2
    val jarjestetyt = lukuja.sorted
    if (lukuja.size % 2 == 1) 
      jarjestetyt(puolivali)
    else
      (jarjestetyt(puolivali - 1) + jarjestetyt(puolivali)) / 2
  }
  
  def yleisimmanMaara(lukuja: Vector[Double]) = lukuja.groupBy(identity).values.map( _.size ).max

}