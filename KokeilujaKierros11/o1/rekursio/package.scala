package o1


// TÄMÄN TIEDOSTON SISÄLTÖ ON SELITETTY KURSSIMATERIAALIN LUVUSSA 11.3.

package object rekursio  {

  
  def onKirjainpalindromi(merkkijono: String): Boolean = onPalindromi(merkkijono.filter( _.isLetter ).toLowerCase)
  
  
  def onPalindromiSilmukalla(merkkijono: String): Boolean = {
    var alkuindeksi = 0
    var loppuindeksi = merkkijono.length - 1
    while (alkuindeksi < loppuindeksi) {
      if (merkkijono(alkuindeksi) != merkkijono(loppuindeksi)) {
        return false
      } 
      alkuindeksi += 1
      loppuindeksi -= 1
    }
    true
  }
  
  
  def onPalindromi(merkkijono: String): Boolean = { 
    if (merkkijono.length <= 1) 
      true
    else if (merkkijono.head != merkkijono.last) 
      false
    else
      onPalindromi(merkkijono.substring(1, merkkijono.length - 1))
  }
  
  
  def tulostaNelioitaSilmukalla(ylaraja: Int) = {
    for (luku <- 1 to ylaraja) {
      println(luku * luku)
    }
  }
  
  
  def tulostaNelioita(ylaraja: Int): Unit = {
    if (ylaraja > 0) {
      tulostaNelioita(ylaraja - 1)
      println(ylaraja * ylaraja)
    }
  }  
  
  
  def fibo(n: Int): Int = if (n <= 1) n else fibo(n - 1) + fibo(n - 2) 
  
  
  def fiboParempi(n: Int) = {
    
    def fiboapu(tamaFibo: Int, seuraavaFibo: Int, jaljella: Int): Int = {
      if (jaljella <= 0) 
        tamaFibo
      else
        fiboapu(seuraavaFibo, tamaFibo + seuraavaFibo, jaljella - 1)    
    } 

    fiboapu(0, 1, n)
  } 
    
  
}