package o1.forsilmukoita

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 5.4.

import scala.math._

object Harjoitus7 extends App {

  // Tutki seuraavaa ohjelmaa ja kokeile ajaa se. Se tuottaa kuvan sinikäyrästä.
  // Ymmärrätkö miten ohjelma toimii?
  // Kokeile muuttaa ohjelman lukuarvoja toisenlaiseksi. Miten se vaikuttaa piirtyvään kuvaan?
  // 
  // Palauta jokin toisia lukuarvoja käyttävä versio tästä koodista kuitataksesi 
  // itsellesi muutaman tehtäväpisteen.  

  for (luku <- 1.3 to (30 * Pi) by (Pi / 20)) {
    val skaalattuSini = (sin(luku) * 40).round.toInt   
    println("*" * (40 + skaalattuSini))      
  }


}

