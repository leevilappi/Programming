
package futures

import org.junit.Test
import org.junit.Assert._

import scala.concurrent._
import ExecutionContext.Implicits.global


object Helper {
  val r = new scala.util.Random()
  def randInt(m: Int) = r.nextInt(m)
  val inf = duration.Duration.Inf
  val timeOut = 10000

  def res(p: Promise[Int], z: Int, e: Throwable) { 
    if(e != null) {
      p failure e
    } else {
      p success z
    }
  }

  def stateStr(z: Int, e: Throwable) = {
    if(e != null) {
      "completes with %s (failure)".format(e.toString)
    } else {
      "completes with %d (success)".format(z)
    }
  }

  def trial2(fn: (String) => Unit,
             a: Int, b: Int, 
             ae: Throwable, be: Throwable, 
             shouldFail: Throwable, result: Int,
             trialfunc: (Future[Int],Future[Int])=>Future[Int]) = {

    import scala.util.Success
    import scala.util.Failure

    var ok = true
    var errmsg = ""
    val magic = Promise[Int] // do not touch...

    val pa = Promise[Int]
    val pb = Promise[Int]
    val fa = pa.future
    val fb = pb.future
    try {
      val f = trialfunc(fa,fb)
      res(pa, a, ae)
      res(pb, b, be)
      Await.ready(f,duration.Duration(Helper.timeOut, "millis"))
      f.value match {
        case Some(Success(v)) =>
          if(shouldFail != null) {
            errmsg = "Succeeded with %d but should have failed with %s".format(v, shouldFail.toString)
            ok = false
          } else {
            if(v != result) {
              errmsg = "Succeeded with %d but should have succeeded with %d".format(v, result)
              ok = false
            }
          }
        case Some(Failure(t)) =>
          if(shouldFail == null) {
            errmsg = "Failed with %s but should have succeeded with %d".format(t.toString, result)
            ok = false
          } else {
            if(shouldFail != t) {
              errmsg = "Failed with %s but should have failed with %s".format(t.toString, shouldFail.toString)
              ok = false
            }
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
      fn("... a first future that %s".format(stateStr(a, ae)))
      fn("... a second future that %s".format(stateStr(b, be)))
      fn(errmsg)
      fn("TEST FAILED") 
    }
    ok
  }

  def trial1(fn: (String) => Unit,
             a: Int,
             ae: Throwable,
             shouldFail: Throwable, result: Int,
             trialfunc: (Future[Int])=>Future[Int]) = {

    import scala.util.Success
    import scala.util.Failure

    var ok = true
    var errmsg = ""
    val magic = Promise[Int] // do not touch...

    val pa = Promise[Int]
    val fa = pa.future
    try {
      val f = trialfunc(fa)
      res(pa, a, ae)
      Await.ready(f,duration.Duration(Helper.timeOut, "millis"))
      f.value match {
        case Some(Success(v)) =>
          if(shouldFail != null) {
            errmsg = "Succeeded with %d but should have failed with %s".format(v, shouldFail.toString)
            ok = false
          } else {
            if(v != result) {
              errmsg = "Succeeded with %d but should have succeeded with %d".format(v, result)
              ok = false
            }
          }
        case Some(Failure(t)) =>
          if(shouldFail == null) {
            errmsg = "Failed with %s but should have succeeded with %d".format(t.toString, result)
            ok = false
          } else {
            if(shouldFail != t) {
              errmsg = "Failed with %s but should have failed with %s".format(t.toString, shouldFail.toString)
              ok = false
            }
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
      fn("The parameter future was completed with ...")
      fn("... a future that %s".format(stateStr(a, ae)))
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

    def trialR(): Boolean = {
      val a = Helper.randInt(100)
      Helper.trial1(fn(_), a, null, null, a+1, incrementF(_))
    }

    for(i <- 0 until 20) { if(!trialR()) { return 0 } }
    1
  }

  def test2(): Int = {
    fn("Unit test for Task 2 ...")

    def trialR2(): Boolean = {
      val a = Helper.randInt(100)
      val b = Helper.randInt(100)
      Helper.trial2(fn(_), a, b, null, null, null, a+b, sumF(_,_))
    }

    for(i <- 0 until 20) { if(!trialR2()) { return 0 } }
    1
  }

  def test3(): Int = {
    fn("Unit test for Task 3 ...")

    def trialR1(lab:String, tf:(Future[Int])=>Future[Int], f:(Int)=>Int): Boolean = {
      val a = Helper.randInt(100)
      Helper.trial1((t:String)=>fn("%s: %s".format(lab,t)), a, null, null, f(a), tf)
    }

    def trialR2(lab:String, tf:(Future[Int],Future[Int])=>Future[Int], f:(Int,Int)=>Int): Boolean = {
      val a = Helper.randInt(100)
      val b = Helper.randInt(100)
      Helper.trial2((t:String)=>fn("%s: %s".format(lab,t)), a, b, null, null, null, f(a,b), tf)
    }

    Services.dosim = false

    if(!trialR1("ServicesF.A",ServicesF.A(_),(x:Int)=>x)) { return 0 }
    if(!trialR1("ServicesF.B",ServicesF.B(_),2*_)) { return 0 }
    if(!trialR1("ServicesF.C",ServicesF.C(_),_+1)) { return 0 }
    if(!trialR1("ServicesF.D",ServicesF.D(_),-_)) { return 0 }
    if(!trialR1("ServicesF.E",ServicesF.E(_),3*_)) { return 0 }
    if(!trialR2("ServicesF.P",ServicesF.P(_,_),_+_)) { return 0 }
    if(!trialR2("ServicesF.Q",ServicesF.Q(_,_),_*_)) { return 0 }
    1
  }

  def test4(): Int = {
    fn("Unit test for Task 4 ...")

    def trialRx(): Boolean = {
      val a = Helper.randInt(100)
      val b = Helper.randInt(100)
      Helper.trial2(fn(_), a, b, null, null, null, a, eitherF(_,_))
    }

    def trialRy(): Boolean = {
      val a = Helper.randInt(100)
      val b = Helper.randInt(100)
      val t = new java.lang.ArithmeticException("failed")
      Helper.trial2(fn(_), a, b, t, null, null, b, eitherF(_,_))
    }

    def trialRf(): Boolean = {
      val a = Helper.randInt(100)
      val b = Helper.randInt(100)
      val tx = new java.lang.ArithmeticException("failed x")
      val ty = new java.lang.ArithmeticException("failed y")
      Helper.trial2(fn(_), a, b, tx, ty, ty, a, eitherF(_,_))
    }

    for(i <- 0 until 10) { if(!trialRx()) { return 0 } }
    for(i <- 0 until 10) { if(!trialRy()) { return 0 } }
    for(i <- 0 until 1) { if(!trialRf()) { return 0 } }
    1
  }

  def test5(): Int = {
    fn("Unit test for Task 5 ...")

    def trialRx(): Boolean = {
      val a = Helper.randInt(100)
      Helper.trial1(fn(_), a, null, null, a, zeroOnArithmeticException(_))
    }

    def trialRfa(): Boolean = {
      val a = 1+Helper.randInt(100)
      val t = new java.lang.ArithmeticException("failed")
      Helper.trial1(fn(_), a, t, null, 0, zeroOnArithmeticException(_))
    }

    def trialRfb(): Boolean = {
      val a = 1+Helper.randInt(100)
      val t = new java.io.IOException("failed")
      Helper.trial1(fn(_), a, t, t, a, zeroOnArithmeticException(_))
    }

    for(i <- 0 until 10) { if(!trialRx()) { return 0 } }
    for(i <- 0 until 10) { if(!trialRfa()) { return 0 } }
    for(i <- 0 until 1) { if(!trialRfb()) { return 0 } }
    1
  }

  @Test def testT1() { assertTrue("testT1() failed", test1() != 0) }
  @Test def testT2() { assertTrue("testT2() failed", test2() != 0) }
  @Test def testT3() { assertTrue("testT3() failed", test3() != 0) }
  @Test def testT4() { assertTrue("testT4() failed", test4() != 0) }
  @Test def testT5() { assertTrue("testT5() failed", test5() != 0) }
}


