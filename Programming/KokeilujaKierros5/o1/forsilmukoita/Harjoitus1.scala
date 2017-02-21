package o1.forsilmukoita

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 5.3.

import scala.collection.mutable.Buffer

object Harjoitus1 extends App {
 
  val lukuja = Buffer(3.4, 6.5, 2.3, 3.1, 19.2, 5.41)   // Jätä ainakin tämä rivi ennalleen. 

  // Käytä alla olevien rivien sijaan silmukkaa siten, että jokaista alkiota 
  // ei erikseen tarvitse käskeä tulostettavaksi. 
  println("Alku.")
  for (luku <- lukuja){
    println("-----")
    println(luku * 2)
  }
  println("Loppu.")



}

