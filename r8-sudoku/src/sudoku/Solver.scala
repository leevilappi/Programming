
/*
 * Author: Tommi Junttila
 */

package sudoku

/**
 * The sudoku solver object.
 * Your task is to implement the "solve" and "getBestCellGreedy" methods.
 * You are allowed to use local variables (var declarations) and mutable data structures
 * inside the methods but may not introduce any variables in the object itself or any new objects.
 * That is, the interface of the object should be "functional" but the internals can use
 * whatever technique is considered most convenient/efficient/...
 * (if you are curious, see, e.g., how the "find" method is implemented in scala.Iterator)
 */
object Solver {
  /**
   * The most trivial cell selection heuristics.
   * Note that this is a function, not a method.
   * Given a grid, the function returns the coordinate of the first cell having no value.
   * If all the cells already have a value, returns None.
   */
  val getBestCellSimple: Grid => Option[Pair[Int, Int]] = g => {
    val n = g.n
    var r = 0
    var bestCell: Option[Pair[Int, Int]] = None
    while (bestCell.isEmpty && r < n) {
      var c = 0
      while (bestCell.isEmpty && c < n) {
        if (!g.defined(r, c)) bestCell = Some((r, c))
        c = c + 1
      }
      r = r + 1
    }
    bestCell
  }

  /**
   * Greedy cell selection heuristics.
   * Note that this is a function, not a method.
   * Given a grid, returns the coordinate of a cell with no value yet and having fewest candidate values,
   * where a candidate value for a cell is a number in {1,2,...,n} that
   * - does not occur in the same row,
   * - does not occur in the same column, and
   * - does not occur in the same sub-grid.
   * If all the cells already have a value, returns None.
   */
  val getBestCellGreedy: Grid => Option[Pair[Int, Int]] = g => {
    var bestSoFar: Option[Pair[Int, Int]] = None
    var bestCount: Int = 10
    
    val n = g.n
    var r = 0
    
    while (r < n) {
      var c = 0
      while (c < n) {
        if (!g.defined(r, c)) {
          var count = 0
          for(i <- 1 to n){
            if(g.updated(r,c,i).isConsistent){
              count += 1
            }
          }
          if(count <= bestCount){
//             println("Previous best: " + bestSoFar + " and its count: " + count + " / " + bestCount)
             bestCount = count
             bestSoFar = Some((r, c))
//             println("Now the best: " + bestSoFar)
          }
        }
        c = c + 1
      }
      r = r + 1
    }
    
    bestSoFar
  }
  
  /**
   * Solve the given sudoku grid.
   * @parameter g the grid to be solved
   * @parameter getBestCell the cell selection heuristics, i.e. a function that returns
   *            a cell with no value yet (or None if all cells already have values)
   * @returns a pair (sol, nofCalls), where
   * * sol is the solution (None if the grid has no solution), and
   * * nofCalls is the number of recursive calls made
   */
  
  def solve(g: Grid, getBestCell: Grid => Option[Pair[Int, Int]]): (Option[Grid], Int) = {
     var nof = 1
     val n = g.n

     var solution: (Option[Grid], Int) = (None, nof)
    
    if(!g.isConsistent){
      (None, nof)
      
    }else if(getBestCell(g).isEmpty) {
      (Option(g), nof)
      
    }else{
     nof += 1
     val r = getBestCell(g).get._1
     val c = getBestCell(g).get._2
     
     for(i <- 1 to n) {
        solution = solve(g.updated(r, c, i), getBestCell)
        if(solution._1.isDefined) {
          return solution
        }
      }
     solution 
    }
     
  }

}

