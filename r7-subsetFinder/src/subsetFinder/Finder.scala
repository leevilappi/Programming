
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
    val set = tester.domain
    
    def inner(start: Int, end: Int) : Option[T] = {
      if(tester.contains(set)){
        
      }
    }
    
      
    ???
  }
 
  /**
   * Find all the k "important" elements in the domain of the tester
   * (the value k is not known in advance!).
   * If the domain contains no important elements, return an empty set.
   * Use binary search to implement this.
   * Must make at most k*ceil(log(tester.domain)*2)+1 calls to tester.contains.
   */
  def findAll[T](tester: Tester[T]): Set[T] = ???
}

