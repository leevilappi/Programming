package o1.pareja

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 9.1.

object Jaotteluohjelma extends App {

  // Korvaa kysymysmerkeillä merkitty kohta sopivalla metodikutsulla.
  // Poista samalla myös kommenttimerkinnät println-käskyjen edestä
  // ohjelman lopusta.
  // 
  // Ohjelman on tulostettava ensin ne asiakkaat, joiden syntymävuosi on 
  // 1980 tai myöhemmin ja sitten ne, joiden syntymävuosi on jo aiemmin. Näin:
  // TUOREEMMAT:
  // Yona(1984-)
  // Gaga(1986-)
  // MUINAISET:
  // Bono(1960-)
  // Cher(1946-)
  // Nico(1938-1988)
  // Pelé(1940-)
  // Enya(1961-)
  // Stig(1978-)
  // Nero(37-68)
  //
  // Sinun ei tarvitse lisätä kuin yksi Vector-olion metodin kutsu 
  // ratkaistaksesi tehtävän.
  //
  // Alla käytetään tästä samasta pakkauksesta löytyvää luokkaa Asiakas.
  
  
  val asiakkaita = Vector(new Asiakas(4321, "Bono", 1960, None),
                          new Asiakas(1234, "Cher", 1946, None),
                          new Asiakas(4444, "Nico", 1938, Some(1988)),
                          new Asiakas(7777, "Yona", 1984, None),
                          new Asiakas(1111, "Pelé", 1940, None),
                          new Asiakas(6666, "Enya", 1961, None),
                          new Asiakas(5555, "Stig", 1978, None),
                          new Asiakas(2222, "Nero", 37,   Some(68)),
                          new Asiakas(3333, "Gaga", 1986, None))
                          
                          
  
  val (tuoreemmat, muinaiset) = asiakkaita.partition(_.syntynyt >= 1980) // korvaa yhtäsuuruusmerkin jälkeinen osa sopivalla metodikutsulla
//   Poista seuraavien neljän rivin kommentit.
   println("TUOREEMMAT:")
   tuoreemmat.foreach(println)
   println("MUINAISET:")
   muinaiset.foreach(println)
  
    
}