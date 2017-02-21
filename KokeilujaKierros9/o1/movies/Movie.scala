package o1.movies

// TÄMÄ LUOKKA LIITTYY LUVUN 9.4 ESIMERKKIIN.

class Movie(val name: String, val year: Int, val position: Int, val rating: Double, val directors: Vector[String]) {
  override def toString = this.name
}
