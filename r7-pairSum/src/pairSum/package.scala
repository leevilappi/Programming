
package object pairSum {
  /**
   * Find whether there is an integer v1 in list l1 and an integer v2 in list l2
   * such that v1 + v2 == target.
   * If such a pair exists, return it [with "Some((v1,v2))"]; otherwise, return None.
   * This function is allowed to run in time O(l1.length * l2.length).
   */
  def hasPairSlow(l1: List[Int], l2: List[Int], target: Int): Option[Pair[Int, Int]] = {
    var answer: Option[Pair[Int, Int]] = None
    
    for(x <- l1){
      for(y <- l2) {
        if(x + y == target){
          answer = Some(x,y)
        }
      }
    }
    answer
  }

  /**
   * A faster version of hasPairSlow, must run in time
   * O(l1.length*log(l1.length) + l2.length*log(l2.length)).
   * Find whether there is an integer v1 in list l1 and an integer v2 in list l2
   * such that v1 + v2 == target.
   * If such a pair exists, return it [with "Some(( v1,v2))"]; otherwise, return None.
   */
  def hasPair(l1: List[Int], l2: List[Int], target: Int): Option[Pair[Int, Int]] = {
      import java.util.HashMap
      var answer: Option[Pair[Int, Int]] = None
      var list = Array[Int]()
      list = list ++ l1
      list = list ++ l2
      
      val pairs = new HashMap[Integer, Integer]()
      
      for (i <- 0 until list.length) {
        if (pairs.containsKey(list(i)))
          answer = Some( (list(i), pairs.get(list(i))) )
        else pairs.put(target - list(i), list(i))
      }
     
      answer
  }
}

