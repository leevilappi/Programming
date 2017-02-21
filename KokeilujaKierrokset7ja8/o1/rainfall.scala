package o1

// TÄMÄ TEHTÄVÄ LIITTYY LUKUUN 8.1.

object rainfall {

  // Kirjoita tähän alle funktio averageRainfall, joka toimii luvussa 8.1 kuvatulla tavalla. 
  
  def averageRainfall(data: Vector[Int]) = {
    if(data(0) != 999999 && !data.isEmpty) {    
      var counter = data.takeWhile(_ < 99999)
      var filtered = counter.filter( _ >= 0)
      
      var total = filtered.takeWhile(_ < 99999).sum
      
      if(filtered.size != 0) Some(total / filtered.size) else None
      
    }else{
      None
    }

  }
  
  
  
  
}

