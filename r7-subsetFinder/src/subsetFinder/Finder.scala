
package subsetFinder

import scala.collection.immutable.Set

object Finder {
  /**
   * Find one "important" element in the domain of the tester.
   * If the domain contains no important elements, return None.
   * Use binary search to implement this.
   * Sets can be split with splitAt method (see http://www.scala-lang.org/api/current/index.html#scala.collection.immutable.Set).
   * Must make at most ceil(log(tester.domain)*2)+1 calls to tester.contains.
   */
  def findOne[T](tester: Tester[T]): Option[T] = {
      var domain = tester.domain    
      
        def finder(tester: Tester[T]): Option[T] = {
          
          if (domain.size == 1) {
            Some(domain.head)  
          } else {
          
            var splitted = domain.splitAt(domain.size / 2)
            var leftDomain = splitted._1
            var rightDomain = splitted._2
            
            if(tester.contains(leftDomain) ){
              domain = leftDomain
              finder(tester)
            }else {
              domain = rightDomain
              finder(tester)
            }
          }
        }
   
    if(domain.isEmpty){
      None
    }else{
      finder(tester)
    }
      
    
  }
 
  /**
   * Find all the k "important" elements in the domain of the tester
   * (the value k is not known in advance!).
   * If the domain contains no important elements, return an empty set.
   * Use binary search to implement this.
   * Must make at most k*ceil(log(tester.domain)*2)+1 calls to tester.contains.
   */
  def findAll[T](tester: Tester[T]): Set[T] = {
    import scala.collection.mutable.ArrayBuffer
    
    var domain = tester.domain
    var elements = Set[T]()
    elements ++ domain   
      
        def finder: Set[T] = {
          
          if(tester.contains(elements)){
            elements
          }else{
            var splitted = elements.splitAt(elements.size / 2)
            var leftDomain = splitted._1
            var rightDomain = splitted._2
            
            if(tester.contains(leftDomain) ){
              elements ++ leftDomain
            }
            
            if(tester.contains(leftDomain) ){
              elements ++ rightDomain
            }
            
            finder
          }
          
            
                              
        }
   
    if(domain.isEmpty){
      Set[T]()
    }else{
      finder
    }
    
  }
}

