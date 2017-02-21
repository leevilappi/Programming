package o1.forsilmukoita

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 5.4.

import scala.math._

object Harjoitus8 extends App {

  val ylaraja = 9 // älä muuta tätä
  
  // Kirjoita tähän ohjelma, joka tulostaa hypotenuusan pituuden kaikille kateettipareille,
  // joissa kummankin kateetin pituus on kokonaisluku väliltä 1-ylaraja.
  // Käytä sisäkkäisiä for-silmukoita. 
  // Jos yläraja olisi 3, tuloste olisi seuraava:
  // 1 ja 1 -----> 1.4142135623730951
  // 1 ja 2 -----> 2.23606797749979
  // 1 ja 3 -----> 3.1622776601683795
  // 2 ja 2 -----> 2.8284271247461903
  // 2 ja 3 -----> 3.605551275463989
  // 3 ja 3 -----> 4.242640687119285
  // Muotoile tuloste samalla tavoin. Tulosteessa eivät saa toistua samat kateettiparit;
  // esim. yllä ei ole sekä tapausta "1 ja 3" että tapausta "3 ja 1". 
  
  // Vinkki: käytä apuna alla valmiina annettua koodiriviä.
  // Sijoita tämä rivi kahden sisäkkäisen silmukan sisään:
  // println(eka + " ja " + toka + " -----> " + hypot(eka, toka))
  
  for (eka <- 1 to ylaraja){
    for(toka <- 1 to ylaraja){
      if(toka > eka || eka == toka)
        println(eka + " ja " + toka + " -----> " + hypot(eka, toka))
    }
  }
  
}

