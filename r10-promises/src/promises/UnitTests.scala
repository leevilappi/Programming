
package promises

import org.junit.Test
import org.junit.Assert._

import scala.concurrent._
import ExecutionContext.Implicits.global

object Helper {
  val r = new scala.util.Random()
  def randInt(m: Int) = r.nextInt(m)
  val inf = duration.Duration.Inf
  val timeOut = 1000

  def res(p: Promise[Int], fail: Boolean, z: Int, block: Boolean) { 
    if(!block) { 
      if(fail) {
        p failure new java.lang.ArithmeticException("failed")
      } else {
        p success z
      }
    }
  }

  def stateStr(block: Boolean, fail: Boolean, value: Int) = {
    if(block) { 
     "never completes"
    } else {
      if(fail) {
        "completes with an ArithmeticException"
      } else {
        "completes with %d".format(value)
      }
    }
  }

  def trialBoxed(fn: (String) => Unit,
                 a: Int, b: Int, 
                 ablock: Boolean, bblock: Boolean,
                 afail: Boolean, bfail: Boolean, 
                 shouldFail: Boolean, result: Int,
                 box: =>(Promise[Int], Promise[Int], Future[Int])) = {

    import scala.util.Success
    import scala.util.Failure

    var ok = true
    var errmsg = ""
    val magic = Promise[Int] // do not touch...
    try {
      val (p,q,f): (Promise[Int], Promise[Int], Future[Int]) = box
      res(p, afail, a, ablock)
      res(q, bfail, b, bblock)
      Await.ready(f,duration.Duration(Helper.timeOut, "millis"))
      f.value match {
        case Some(Success(v)) =>
          if(shouldFail) {
            errmsg = "Succeeded with %d but should have failed with %s".format(v, "ArithmeticException")
            ok = false
          } else {
            if(v != result) {
              errmsg = "Succeeded with %d but should have succeeded with %d".format(v, result)
              ok = false
            }
          }
        case Some(Failure(t)) =>
          if(!shouldFail) {
            errmsg = "Failed with %s but should have succeeded with %d".format(t, result)
            ok = false
          }
        case None =>
          errmsg = "Timed out while waiting for result future to complete"
          ok = false
      }
    } catch {
      case e: TimeoutException => 
        errmsg = "Timed out while waiting for result future to complete\n-- %s".format(e.toString)
        ok = false
      case e: Throwable => 
        errmsg = "Caught an exception\n-- %s".format(e.toString)
        ok = false
    }
    if(!ok) { 
      fn("The promises were completed with ...")
      fn("... a first future that %s".format(stateStr(ablock, afail, a)))
      fn("... a second future that %s".format(stateStr(bblock, bfail, b)))
      fn(errmsg)
      fn("TEST FAILED") 
    }
    ok
  }

  def trialUnboxed(fn: (String) => Unit,
                   a: Int, b: Int, 
                   ablock: Boolean, bblock: Boolean,
                   afail: Boolean, bfail: Boolean, 
                   shouldFail: Boolean, result: Int,
                   trialfunc: (Future[Int],Future[Int]) => Future[Int]) = {

    import scala.util.Success
    import scala.util.Failure

    var ok = true
    var errmsg = ""
    val magic = Promise[Int] // do not touch...
    try {
      val p = Promise[Int]
      val q = Promise[Int]
      val f = trialfunc(p.future, q.future)
      res(p, afail, a, ablock)
      res(q, bfail, b, bblock)
      Await.ready(f,duration.Duration(Helper.timeOut, "millis"))
      f.value match {
        case Some(Success(v)) =>
          if(shouldFail) {
            errmsg = "Succeeded with %d but should have failed with %s".format(v, "ArithmeticException")
            ok = false
          } else {
            if(v != result) {
              errmsg = "Succeeded with %d but should have succeeded with %d".format(v, result)
              ok = false
            }
          }
        case Some(Failure(t)) =>
          if(!shouldFail) {
            errmsg = "Failed with %s but should have succeeded with %d".format(t, result)
            ok = false
          }
        case None =>
          errmsg = "Timed out while waiting for result future to complete"
          ok = false
      }
    } catch {
      case e: TimeoutException => 
        errmsg = "Timed out while waiting for result future to complete\n-- %s".format(e.toString)
        ok = false
      case e: Throwable => 
        errmsg = "Caught an exception\n-- %s".format(e.toString)
        ok = false
    }
    if(!ok) { 
      fn("The parameter futures were completed with ...")
      fn("... a first future that %s".format(stateStr(ablock, afail, a)))
      fn("... a second future that %s".format(stateStr(bblock, bfail, b)))
      fn(errmsg)
      fn("TEST FAILED") 
    }
    ok
  }
}

class UnitTests {

  def fn(s: String) { println(s) }

  def test1(): Int = {
    fn("Unit test for Task 1 ...")

    def trialR(afail: Boolean, bfail: Boolean,                
               shouldFail: Boolean) = {
       val a = Helper.randInt(1000)
       val b = Helper.randInt(1000)
       val r = Helper.trialBoxed(fn(_), a, b, false, false, afail, bfail, shouldFail, a+b, buildPlusF())
       r
    }

    if(!trialR(false, false, false)) { return 0 }
    if(!trialR(false, true, true)) { return 0 }
    if(!trialR(true, false, true)) { return 0 }
    if(!trialR(true, true, true)) { return 0 }
    1
  }

  def test2(): Int = {
    fn("Unit test for Task 2 ...")

    def trialR(afail: Boolean, bfail: Boolean) = {
       val a = 1+Helper.randInt(1000)
       val b = 1+Helper.randInt(1000)
       val r = Helper.trialBoxed(fn(_), a, b, false, false, afail, bfail, false, if(afail || bfail) 0 else a+b, buildPlusZeroFailF())
       r
    }

    if(!trialR(false, false)) { return 0 }
    if(!trialR(false, true)) { return 0 }
    if(!trialR(true, false)) { return 0 }
    if(!trialR(true, true)) { return 0 }
    1
  }

  def test3(): Int = {
    fn("Unit test for Task 3 ...")

    def trialR(ablock: Boolean, bblock: Boolean,
               afail: Boolean, bfail: Boolean, 
               shouldFail: Boolean) = {
       val common = Helper.randInt(1000)
       val a = if(!ablock && !bblock) common else Helper.randInt(1000)
       val b = if(!ablock && !bblock) common else Helper.randInt(1000)
       val res = if(ablock) b else a
       val r = Helper.trialUnboxed(fn(_), a, b, ablock, bblock, afail, bfail, shouldFail, res, fastestOfTwo(_,_))
       r
    }

    if(!trialR(false, false, false, false, false)) { return 0 }
    if(!trialR(false, false, false, false, false)) { return 0 }
    if(!trialR(false, false, true, true, true)) { return 0 }
    if(!trialR(false, false, true, true, true)) { return 0 }
    if(!trialR(false, true, false, false, false)) { return 0 }
    if(!trialR(false, true, true, false, true)) { return 0 }
    if(!trialR(true, false, false, false, false)) { return 0 }
    if(!trialR(true, false, false, true, true)) { return 0 }
    1
  }

  def test4() : Int = {
    fn("Unit test for Task 4 ...")

    def trialR(ablock: Boolean, bblock: Boolean,
               afail: Boolean, bfail: Boolean, 
               shouldFail: Boolean) = {
       val common = Helper.randInt(1000)
       val a = if(!ablock && !bblock) common else Helper.randInt(1000)
       val b = if(!ablock && !bblock) common else Helper.randInt(1000)
       val res = if(ablock || afail) b else a
       val r = Helper.trialUnboxed(fn(_), a, b, ablock, bblock, afail, bfail, shouldFail, res, fastestSuccessfulOfTwo(_,_))
       r
    }

    if(!trialR(false, false, false, false, false)) { return 0 }
    if(!trialR(false, false, false, false, false)) { return 0 }
    if(!trialR(false, false, true, true, true)) { return 0 }
    if(!trialR(false, false, true, true, true)) { return 0 }
    if(!trialR(false, false, false, true, false)) { return 0 }
    if(!trialR(false, false, true, false, false)) { return 0 }
    if(!trialR(false, false, false, true, false)) { return 0 }
    if(!trialR(false, false, true, false, false)) { return 0 }
    if(!trialR(false, false, false, true, false)) { return 0 }
    if(!trialR(false, false, true, false, false)) { return 0 }
    if(!trialR(false, true, false, false, false)) { return 0 }
    if(!trialR(true, false, false, false, false)) { return 0 }
    1
  }

  def test5(): Int = {
    fn("Unit test for Task 5 ...")

    def trialR(ablock: Boolean, bblock: Boolean,
               afail: Boolean, bfail: Boolean, 
               az: Boolean, bz: Boolean) = {
       require(!(afail && az))
       require(!(bfail && bz))
       val a = if(az) 0 else 1+Helper.randInt(1000)
       val b = if(bz) 0 else 1+Helper.randInt(1000)
       val shouldFail = (bfail && !az) || (afail && !bz)
       val r = Helper.trialBoxed(fn(_), a, b, ablock, bblock, afail, bfail, shouldFail, a*b, buildTimesFast0F)
       r
    }

    if(!trialR(false, false, false, false, false, false)) { return 0 }
    if(!trialR(true, false, false, false, false, true)) { return 0 }
    if(!trialR(false, true, false, false, true, false)) { return 0 }
    if(!trialR(true, false, true, false, false, true)) { return 0 }
    if(!trialR(false, true, false, true, true, false)) { return 0 }
    if(!trialR(true, false, true, false, false, true)) { return 0 }
    if(!trialR(false, true, false, true, true, false)) { return 0 }
    if(!trialR(false, false, true, true, false, false)) { return 0 }
    if(!trialR(false, false, true, true, false, false)) { return 0 }
    1
  }

  @Test def testT1() { assertTrue("testT1() failed", test1() != 0) }
  @Test def testT2() { assertTrue("testT2() failed", test2() != 0) }
  @Test def testT3() { assertTrue("testT3() failed", test3() != 0) }
  @Test def testT4() { assertTrue("testT4() failed", test4() != 0) }
  @Test def testT5() { assertTrue("testT5() failed", test5() != 0) }
}


