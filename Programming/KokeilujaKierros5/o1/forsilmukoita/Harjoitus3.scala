package o1.forsilmukoita


object Harjoitus3 extends App {

  // Tämä funktio ja siihen liittyvä tehtävä selitetään luvussa 5.4.
  
  def gcContent(dna: String) = {
    var gcCount = 0
    var totalCount = 0
    for (base <- dna) {
      if (base == 'G' || base == 'C') {
        gcCount += 1
        totalCount += 1 
      } else if (base == 'A' || base == 'T') {
        totalCount += 1 
      }
    }
    100.0 * gcCount / totalCount     
  }
  
  
  // Seuraava ohjelmakoodi lukee DNA-tietoja käyttäjän nimeämästä tekstitiedostosta,
  // välittää tiedot gcContent-funktiolle ja tulostaa palautusarvon.
  // Tätä koodia ei luvun 5.4 yhteydessä ole pakko ymmärtää tai lukea. 
  // Tiedostojen käsittelemiseen palataan luvussa 9.3.

  import scala.io.Source
  import scala.io.StdIn._
  import o1.util.useAndClose
  
  val speciesName = readLine("Enter species file name (without .mtdna extension): ")
  val mtDNAFile = Source.fromFile("mtDNA_examples/" + speciesName + ".mtdna")
  val result = useAndClose(mtDNAFile)( file => gcContent(file.mkString.toUpperCase) )
  println(s"The GC content is $result %.")
  
}

