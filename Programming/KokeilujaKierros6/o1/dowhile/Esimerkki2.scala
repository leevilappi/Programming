package o1.dowhile

// TÄMÄ ESIMERKKI ON SELITETTY LUVUSSA 6.1.

object Esimerkki2 extends App {

  var luku = 1
  println(luku)

  do {
    println(luku)
    luku += 4
    println(luku)
  } while (luku < 10)

  println(luku)
  
}

