
// TÄSSÄ TIEDOSTOSSA ON MÄÄRITELTY FUNKTIOITA, JOITA KÄYTETÄÄN LUVUSSA 1.6. 
// NIIDEN SISÄISTÄ TOIMINTAA EI TARVITSE YMMÄRTÄÄ.
// Seuraavaa koodia ei ole kirjoitettu aloittelijaystävälliseen tyyliin.
  
// Ne liittyvät siihen, miten nämä funktiot on sijoitettu pakkaukseen; aiheesta luvussa 4.5. 
package o1
object aliohjelmia {

  import scala.collection.mutable.Buffer 
  import scala.math._

  
  def poistaNegatiiviset(lukuja: Buffer[Int]): Unit = {
    lukuja --= lukuja.filter( _ < 0 )
  }
  
  
  def keskiarvo(eka: Double, toka: Double) = (eka + toka) / 2 

  
  def imdbLeffa(sija: Int) = movieData.sortBy( _._3 ).apply(sija - 1)._1     
    
  def imdbAikavalinParas(alkuvuosi: Int, loppuvuosi: Int) = 
    movieData
      .filter( leffa => leffa._2 >= alkuvuosi && leffa._2 <= loppuvuosi )
      .sortBy( _._3 )
      .apply(0)._1
    
  def imdbParhaatOhjaajat(leffojaVahintaan: Int) =  
    movieData
      .flatMap { case (_, _, _, _, ohjaajat) => ohjaajat.toList }                                        
      .groupBy(identity).mapValues( _.size )
      .filter( _._2 >= leffojaVahintaan )
      .toList.sortBy( -_._2 ) 
      .map { case (ohjaaja, leffoja) => ohjaaja + " (" + leffoja + ")" }
      .mkString(", ")
      
  private lazy val movieData = {
    val Subdir   = "top_movies" 
    val FileName = "omdb_movies_2015.txt" 
    val source   = o1.util.localSource(s"$Subdir/$FileName").getOrElse(throw new Exception(s"Could not read the file $FileName, which is expected to be under $Subdir.")) 
    val lines    = source.getLines.toList.map( _.split(";") )
    lines.map( line => (line(0), line(1).toInt, line(2).toInt, line(3).toDouble, line(4).split(",")) )
  }
  
      
  def editointietaisyys(teksti1: String, teksti2: String) = o1.util.editDistance(teksti1, teksti2, 12)
      
  
  def kaanon(biisi: String, soittimet: Iterable[Int], viive: Int) = {
    import o1.sound.Music._
    import o1.util._
    
    val (melodia, tempo) = biisi match { 
      case r"(.*?)$melodia(?:/([\d]+)$tempoOrNull)?" => (melodia, Option(tempoOrNull)) 
    }
    def alkutauko(monesko: Int) = " " * (monesko * viive.max(0).min(melodia.length))
    val eriaikaiset = for ((soitin, monesko) <- soittimet.take(MaxVoices).zipWithIndex) 
                        yield s"${alkutauko(monesko)}[$soitin]$melodia"
    eriaikaiset.mkString("&") + tempo.map( "/" + _ ).getOrElse("")
  }

  
  def sensuroi(teksti: String, rumatSanat: Traversable[String]) = 
    rumatSanat.foldLeft(teksti)( (versio, rumaSana) => versio.replaceAll(rumaSana, "[P" + "I" * max(0, rumaSana.length - 2) + "P]") )

  
  
  def pelaaPylpyrapelia(pelaaja: String) = {
    import o1.gui.Dialog._
    
    def parasValinta(jaljella: Int) = jaljella % 3
    
    def pyydaValinta(jaljella: Int) = {
      requestInt("Jäljellä on " + jaljella + " pylpyrää. Montako otat?", luku => (luku == 1 || luku == 2), "Ota 1 tai 2.", Centered)
    }
    
    display("Tervetuloa PYLPYRÄÄTTÖRIIN, " + pelaaja + "!\nAlussa on kaksitoista pylpyrää.\n" +
            "Pelaajat ottavat vuorotellen 1 tai 2 pylpyrää.\nViimeisen pylpyrän saanut voittaa.", Centered) 
    var jaljella = 12
    while (jaljella > 0) {
      pyydaValinta(jaljella) match {
        case Some(valittu) =>
          jaljella -= valittu 
          val koneOtti = parasValinta(jaljella)
          display("Otan " + koneOtti + " " + (if (koneOtti == 1) "pylpyrän" else "pylpyrää") + ".", Centered)
          jaljella -= koneOtti
        case None =>
          jaljella = 0
      } 
    }
    display("Valitettavasti hävisit. Sori, " + pelaaja + ".\n", Centered)
    
  }
    
  
  def nayta(sana: String) = {
    println("Parametriksi saatiin: " + sana + ".")
    sana.length
  }
  
  
}
