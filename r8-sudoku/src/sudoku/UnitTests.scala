
package sudoku

import org.junit.Test
import org.junit.Assert._

class UnitTests {
  @Test def testIsConsistentSmall1() {
    val grid = Grid(List(
      "1 ? ? 3",
      "2 ? ? ?",
      "? ? ? 1",
      "? 1 ? ?"))
    assertTrue(grid.isConsistent)
  }
  @Test def testIsConsistentSmall2() {
    val grid = Grid(List(
      "1 ? ? 3",
      "2 ? ? 2",
      "? ? ? 1",
      "? 1 ? ?"))
    assertFalse(grid.isConsistent)
  }
  @Test def testIsConsistentSmall3() {
    val grid = Grid(List(
      "1 ? ? 3",
      "2 ? ? ?",
      "? ? ? 1",
      "2 1 ? ?"))
    assertFalse(grid.isConsistent)
  }
  @Test def testIsConsistentSmall4() {
    val grid = Grid(List(
      "1 2 ? 3",
      "2 ? ? ?",
      "? ? ? 1",
      "? 1 ? ?"))
    assertFalse(grid.isConsistent)
  }
  @Test def testIsConsistent1() {
    val grid = Grid(List(
      "? ? ? ? ? 6 ? 8 ?",
      "? 4 3 9 ? ? 1 ? 7",
      "7 2 ? 1 5 ? 9 ? ?",
      "1 ? ? 2 ? ? 6 ? ?",
      "3 ? ? ? ? 4 ? ? ?",
      "? ? ? ? 6 8 ? ? 5",
      "? ? 5 ? ? ? ? 7 ?",
      "? ? ? ? 2 ? ? 6 1",
      "? ? 7 6 ? ? 2 ? ?"))
    assertTrue(grid.isConsistent)
  }
  @Test def testIsConsistent2() {
    val grid = Grid(List(
      "? ? ? ? ? 6 ? 8 ?",
      "? 4 3 9 ? ? 1 ? 7",
      "7 2 ? 1 5 ? 9 ? ?",
      "1 ? ? 2 ? ? 6 ? ?",
      "3 ? ? ? ? 4 ? ? ?",
      "? ? ? ? 6 8 ? ? 5",
      "? ? 5 ? ? 2 ? 7 ?",
      "? ? ? ? 2 ? ? 6 1",
      "? ? 7 6 ? ? 2 ? ?"))
    assertFalse(grid.isConsistent)
  }
  /* Feel free to write your own tests if you wish */

  @Test def testSolveSmall1() {
    val grid = Grid(List(
      "2 ? ? ?",
      "? ? 1 ?",
      "? 2 ? ?",
      "? ? ? 4"))
    val (solution, nofCalls) = Solver.solve(grid, Solver.getBestCellSimple)
    assertTrue(solution.isDefined)
    assertTrue(grid.isSolution(solution.get))
    val ref = Grid(List(
      "2 1 4 3",
      "3 4 1 2",
      "4 2 3 1",
      "1 3 2 4"))
    assertEquals(ref, solution.get)
  }

  @Test def testSolve1() {
    val grid = Grid(List(
      "? ? ? ? ? 6 ? 8 ?",
      "? 4 3 9 ? ? 1 ? 7",
      "7 2 ? 1 5 ? 9 ? ?",
      "1 ? ? 2 ? ? 6 ? ?",
      "3 ? ? ? ? 4 ? ? ?",
      "? ? ? ? 6 8 ? ? 5",
      "? ? 5 ? ? ? ? 7 ?",
      "? ? ? ? 2 ? ? 6 1",
      "? ? 7 6 ? ? 2 ? ?"))
    val (solution, nofCalls) = Solver.solve(grid, Solver.getBestCellSimple)
    assertTrue(solution.isDefined)
    assertTrue(grid.isSolution(solution.get))
    val ref = Grid(List(
      "5 9 1 7 4 6 3 8 2",
      "6 4 3 9 8 2 1 5 7",
      "7 2 8 1 5 3 9 4 6",
      "1 5 4 2 7 9 6 3 8",
      "3 8 6 5 1 4 7 2 9",
      "9 7 2 3 6 8 4 1 5",
      "2 6 5 4 9 1 8 7 3",
      "4 3 9 8 2 7 5 6 1",
      "8 1 7 6 3 5 2 9 4"))
    assertEquals(ref, solution.get)
  }

  @Test def testSolve2() {
    val grid = Grid(List(
      "? ? ? ? ? 6 ? 8 ?",
      "? 4 3 9 ? ? 1 ? 7",
      "7 2 ? 1 5 ? 9 ? ?",
      "1 ? ? 2 ? ? 6 ? ?",
      "3 ? ? ? ? 4 ? ? ?",
      "? ? ? ? 6 8 ? ? 5",
      "? ? 5 ? ? ? ? 7 ?",
      "? ? ? ? 2 ? ? 6 3",
      "? ? 7 6 ? ? 2 ? ?"))
    val (solution, nofCalls) = Solver.solve(grid, Solver.getBestCellSimple)
    assertTrue(!solution.isDefined)
  }

  /* Unit tests for greedy heuristics are left as a voluntary exercise, start with 4*4 grids */

}

