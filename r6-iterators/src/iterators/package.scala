
package object iterators {

  /**
   * Returns an iterator that generates the prime numbers
   * 1,2,3,5,7,11,13,17,19,23,...
   * (Strictly speaking, 1 is not considered prime in most of the modern definitions as including it causes problems in some other definitions but let's consider it to be a prime in this exercise.)
   */
  def primesIterator: Iterator[Int] = new Iterator[Int] {
    /* You'll need some private var or val to store the internal state.
	 * Put it/them here */
    
    import scala.math.ceil
    import scala.math.sqrt
    var counter = 1

    /**
     * There is always the next prime number.
     * In reality, we should return false when the next prime would be larger than that can
     * be represented with an Int but, for the sake of focusing the exercise on the essential parts,
     * this consideration is not required for getting the points.
     */
    def hasNext = true

    /**
     * Get the next prime number.
     * The first call should return 1, the next 2, then 3, 5, 7, 11 and so on.
     *
     * These numbers must be computed by the code here, one is not allowed to pre-compute and
     * store them in an Array or similar data structure.
     */
    def next(): Int = { 
      if(counter <= 2){
        val prime = counter
        counter += 1
        prime  
      } else {
        while(isPrime) {
          counter += 2
        }
        val prime = counter
        counter += 2
        prime
      }
    }
    def isPrime = !(3 to sqrt(counter).ceil.toInt by 2).forall(counter % _ != 0)
  }
  

  /**
   * Return a new iterator whose "next" method returns randomly an element that
   * - is in the non-empty constructor argument set s, and
   * - has not been returned during the blockingTime last calls to "next".
   * That is, after being selected and returned by "next", an element is blocked in the
   * the "blockingTime" subsequent calls to "next".
   * The argument seed is the seed given to the pseudo-random number generator
   * (fix it to have repeatable runs and set to System.nanoTime etc to have true pseudo-random sequences).
   */
  def blockingRandomIterator[T](s: Set[T], blockingTime: Int, seed: Int = 2105): Iterator[T] = new Iterator[T] {
    require(0 <= blockingTime && blockingTime < s.size)
    
    /* Pseudo-random number generator.
     * See http://www.scala-lang.org/api/current/index.html#scala.util.Random */
    val r = new scala.util.Random(seed)
       
    /* You'll need some var or val to store the internal state.
     * 
     * Perhaps a queue to keep track of the blocked elements?
     * See http://en.wikipedia.org/wiki/Queue_%28abstract_data_type%29 and
     * http://docs.scala-lang.org/overviews/collections/concrete-mutable-collection-classes.html
     * 
     * And perhaps a mutable Buffer to store the elements that are not blocked?
     * By the way, a fast method to remove an element at index i from an unordered buffer
     * is to just replace that element with the last element of the buffer and then shrink
     * the buffer by dropping the last element (which is now a copy of the one in the i:th position)
     * with trimEnd(1)
     * 
		 * Put your state vars/vals here. */
    
    import scala.collection.mutable.Queue
    
    var queue = Queue[T]()
    var buf = s.toBuffer
    
  
    /** There is always the next element */
    def hasNext = true

    /** Returns a random, non-blocked element */
    def next(): T = {

        var index = r.nextInt(buf.length)    
    
        var a = buf(index)
        
        
        if(queue.length == blockingTime){
          buf(index) = buf.last
          buf.trimEnd(1)
          queue.enqueue(a)
          buf += queue.dequeue()
          
          a
          
        } else {
          buf(index) = buf.last
          buf.trimEnd(1)
          queue.enqueue(a)
          a
        }
        
    }
  }
}

