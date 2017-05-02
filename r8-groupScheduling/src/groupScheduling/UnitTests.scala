
package groupScheduling

import org.junit.Test
import org.junit.Assert._

class UnitTests {
  @Test def testSchedule1() {
    val groups = Vector("Mon 10-12", "Tue 12-14", "Wed 8-10", "Wed 14-16", "Thu 18-20", "Fri 8-10")
    val prefs = Map(
      "a1" -> "   x  ",
      "a2" -> " x x  ",
      "a3" -> "xxx xx",
      "a4" -> "x  x x",
      "a5" -> " x x x",
      "a6" -> "xx xx ")
    val data = Preferences(groups, prefs)
    val result = scheduler.schedule(data)
    assertTrue("A solution was expected", result.nonEmpty)
    val (isOk, error) = scheduler.isValidSolution(result.get, data)
    assertTrue("A solution found but validation failed with error: "+error, isOk)
  }
  @Test def testSchedule2() {
    val groups = Vector("Mon 10-12", "Tue 12-14", "Wed 8-10", "Wed 14-16", "Thu 18-20", "Fri 8-10")
    val prefs = Map(
      "a1" -> "   x  ",
      "a2" -> " x x  ",
      "a3" -> "xxx xx",
      "a4" -> "   x x",
      "a5" -> " x x x",
      "a6" -> "xx xx ")
    val data = Preferences(groups, prefs)
    val result = scheduler.schedule(data)
    assertTrue("A solution returned when there is no solution", result == None)
  }
}

