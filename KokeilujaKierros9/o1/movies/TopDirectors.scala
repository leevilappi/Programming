package o1.movies

// TÄMÄ OHJELMA LIITTYY LUKUUN 9.4.  

object TopDirectors extends App {

  val allMovies             = new MovieListFile("omdb_movies_2015.txt").movies // Vector[Movie]
  val allDirectors          = allMovies.flatMap( _.directors )                 // Vector[String]
  val groupedByDirector     = allDirectors.groupBy( dir => dir )               // Map[String,Vector[String]]
  val countsForEachDirector = groupedByDirector.mapValues( _.size )            // Map[String,Int]
  val directorCountPairs    = countsForEachDirector.toVector
  val directorsSorted       = directorCountPairs.sortBy( -_._2 )
  for ((director, count) <- directorsSorted) {
    println(s"$director: $count movies")
  } 
  
}