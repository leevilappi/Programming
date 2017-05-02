
/* See YOUR TASK below. */

package parallel

class Matrix(val n: Int) {
  require(n > 0, "The dimension n must be positive")
  protected[Matrix] val entries = new Array[Double](n * n)

  /* Access the elements of a matrix Z by writing Z(i,j) */
  def apply(row: Int, column: Int) = {
    require(0 <= row && row < n)
    require(0 <= column && column < n)
    entries(row * n + column)
  }

  /* Set the elements by writing Z(i,j) = v */
  def update(row: Int, column: Int, value: Double) {
    require(0 <= row && row < n)
    require(0 <= column && column < n)
    entries(row * n + column) = value
  }

  /* Gives a pretty-printed string representation for small matrices */
  override def toString = {
    val s = new StringBuilder()
    if(n <= 6) {
      for (row <- 0 until n) {
        for (column <- 0 until n) {
          s ++= " %f".format(this(row,column))
        }
        s ++= "\n"
      }
    } else {
      s ++= "[%d-by-%d matrix -- output suppressed]".format(n,n)
    }
    s.toString
  }

  /* Returns the transpose of this matrix */
  def transpose = {
    val result = new Matrix(n)
    for (row <- 0 until n; column <- 0 until n) {
      result(column, row) = this(row, column)
    }
    result
  }
    
  /* Returns a new matrix that is the sum of this and that */
  def +(that: Matrix) = {
    require(n == that.n)
    val result = new Matrix(n)
    (0 until n*n).foreach(i => { 
      result.entries(i) = this.entries(i) + that.entries(i) 
    })
    result
  }

  /* Returns a new matrix that is the product of 'this' and 'that'
   *
   * (The sequential implementation used as performance reference 
   *  -- please leave this code fragment AS IS) 
   */
  def *(that: Matrix): Matrix = {
    require(n == that.n)
    val thatT = that.transpose  // transpose 'that' to get better cache-locality
    def inner(r1: Int, r2: Int) = {
      var s = 0.0
      var i = 0
      while(i < n) {
        // the inner loop -- here is where transposing 'that' pays off
        s = s + entries(r1+i)*thatT.entries(r2+i) 
        i = i+1
      }
      s
    }
    val result = new Matrix(n)
    (0 until n*n).foreach(i => { result.entries(i) = inner((i/n)*n, (i%n)*n) })
    result
  }

  /* Returns a new matrix that is the product of 'this' and 'that'
   * 
   * (The parallel implementation -- to be prepared by you) 
   *
   * YOUR TASK is to make this code fragment run in parallel,
   * USING the Scala parallel collections library 
   *
   * Note: this is super-easy 
   *       -- all you need to do is to insert '.par' to an appropriate 
   *          place in the code, and witness the speedup on large matrices 
   *          (say, n = 1000)
   *
   * See here for more about parallel collections:
   * http://docs.scala-lang.org/overviews/parallel-collections/overview.html
   * http://infoscience.epfl.ch/record/150220/files/pc.pdf
   *
   */
  def **(that: Matrix): Matrix = {
    require(n == that.n)
    val thatT = that.transpose  // transpose 'that' to get better cache-locality
    def inner(r1: Int, r2: Int) = {
      var s = 0.0
      var i = 0
      while(i < n) {
        // the inner loop -- here is where transposing 'that' pays off
        s = s + entries(r1+i)*thatT.entries(r2+i)
        i = i+1
      }
      s
    }
    val result = new Matrix(n)
    (0 until n*n).par.foreach(i => { result.entries(i) = inner((i/n)*n, (i%n)*n) })
    result
  }
}


