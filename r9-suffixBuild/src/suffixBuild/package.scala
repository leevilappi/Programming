
package object suffixBuild {
  def suffixArray(s: String): Seq[Int] = ( 0 until s.length()).map( x => s.drop(x) ).zipWithIndex.sorted.map(_._2).toSeq
    

 
  
    
}

