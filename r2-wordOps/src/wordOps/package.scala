
/*
 * Assignment:  Word operations
 * 
 * Description:
 * This assignment asks you to implement common word operations that are 
 * not available in the Scala programming language. The intent is to practice
 * your skills at working with bits. 
 *
 */

package object wordOps {

  /*
   * Task 1: Population count (number of 1-bits in a word)
   *
   * Complete the following function that takes as parameter 
   * a 32-bit word, w, and returns __the number of 1-bits__ in w.
   * 
   */

  def popCount(w: Int): Int = {
      var count = 0
      while(w > 0){
        if((w & 1) == 1){
          count +=1 
        }
        w >> 1
      }
      count
  }
  
  /*
   * Task 2: Reverse bit positions
   *
   * Complete the following function that takes as parameter 
   * a 16-bit word, w, and returns a 16-bit word, r, such that
   * for every j=0,1,...,15,
   * the value of the bit at position j in r is equal to 
   * the value of the bit at position 15-j in w.
   * 
   */

  def reverse(w: Short): Short = ???
  
  /*
   * Task 3: Left rotation
   *
   * Complete the following function that takes two parameters
   *
   * 1) a 64-bit word, w, and 
   * 2) a 32-bit word, k.
   *
   * The function returns a 64-bit word, r, such that 
   * for all j=0,1,...,63
   * the value of the bit at position (j+k)%64 in r is equal to 
   * the value of the bit at position j in w.
   * 
   */

  def leftRotate(w: Long, k: Int): Long = ???

}

