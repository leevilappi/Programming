package o1.io

import java.io.PrintWriter
import scala.util.Random

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 9.3.

object Kirjoittamisesimerkki1 extends App {
  
  val numerogeneraattori = new Random 
  val tiedostonNimi = "satunnaisia.txt"
  
  val tiedosto = new PrintWriter(tiedostonNimi)
  try {
    for (n <- 1 to 10000) {
      tiedosto.println(numerogeneraattori.nextInt(100))
    }
    println("Luotiin tiedosto " + tiedostonNimi + ", jossa on pseudosatunnaislukuja.")
    println("Jos tiedosto oli ennestään olemassa, sen vanha sisältö korvautui uusilla luvuilla.")
  } finally {
    tiedosto.close()
  }
}

