
package object sorting {
  // Returns a sorted copy of the array d
  def radixSortDirectImmutable(d: Array[Int]): Array[Int] = {
    val n = d.length
    val m = d.foldLeft(0)(_ max _)+1
    require(d.forall(_ >= 0) && m <= n)
    val aa = new Array[Int](m)
    val output = new Array[Int](n) 
    
    for(i <- d) {
      aa(i) += 1
    }
    var counter = 0
    // Kuinka monta numerolydst on tietyll indeksill
    for(i <- 0 until aa.size){
      for(z <- 0 until aa(i)){
        output(counter) = i
        counter += 1
      }
    }
    output
  }
  
  // Sorts the array d
  def radixSortDirectMutable(d: Array[Int]) {
    val n = d.length
    val m = d.foldLeft(0)(_ max _)+1
    require(d.forall(_ >= 0) && m <= n)

    val aa = new Array[Int](m)
    
    for(i <- d) {
      aa(i) += 1
    }
    var counter = 0

    for(i <- 0 until aa.size){
      for(z <- 0 until aa(i)){
        d(counter) = i
        counter += 1
      }
    }
  }

  // Returns a permutation that is a ranking of d
  def radixSortIndirect(d: Array[Int]): Array[Int] = {
    import scala.collection.mutable.Buffer
    
    val n = d.length
    val m = d.foldLeft(0)(_ max _) + 1
    require(d.forall(_ >= 0) && m <= n)

    val aa = Array.fill(m)(Buffer[Int]())
    val output = new Array[Int](n)
    for (i <- 0 until n) {
      aa(d(i)) += (i)
    }
    var index = 0
    for (i <- 0 until aa.length) {
      for (j <- 0 until aa(i).length) {
        output(index) = aa(i)(j)
        index += 1
      }
    }
    output
  }
  
  // Returns a permutation that is a stable ranking of d
  def radixSortIndirectStable(d: Array[Int]): Array[Int] = {
    val n = d.length
    val m = d.foldLeft(0)(_ max _)+1
    require(d.forall(_ >= 0) && m <= n)
    radixSortIndirect(d)
  }
  
  // Returns a permutation that unranks d 
  def radixSortUnrankPerm(d: Array[Int]): Array[Int] = {
    val n = d.length
    val m = d.foldLeft(0)(_ max _)+1
    require(d.forall(_ >= 0) && m <= n)
    
    radixSortIndirectStable(radixSortIndirectStable(d))
    
  }
  
  // Returns a cell unrank array for d
  def radixSortCellUnrankArray(d: Array[Int]): Array[Int] = {
    val n = d.length
    val m = d.foldLeft(0)(_ max _)+1
    require(d.forall(_ >= 0) && m <= n)
    
    val u = new Array[Int](d.length)
    (0 until u.length).foreach(j => u(d(j)) = j)
    
    u
  }
}

