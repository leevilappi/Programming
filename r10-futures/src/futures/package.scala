
package object futures {

  import scala.concurrent._
  import ExecutionContext.Implicits.global

  /*
   * Future-objects are created with the method 'Future.apply' 
   * (or in short form, just 'Future') that takes as parameter 
   * a function that returns a value (or fails by throwing 
   * an exception during its execution). 
   */

  val f = Future { Thread.sleep(5000); 123 }
      // f is a future that gets completed with the value 123
      //
      // (in your mind you should replace the 5-second sleep
      // with a laborious computation that takes 5 seconds) 

  /* The 'Future' method above schedules the function given as parameter for 
   * execution in its own thread. That is, we are given the guarantee that 
   * the parameter function is executed eventually, when computing resources 
   * are available. (Of course in practice the execution starts 
   * immediately if resources are available.)
   *
   * Futures enable us to compute (that is, to specify how to compute) 
   * with the future values before the values become available. 
   * For example, the future f above is immediately available for use, 
   * even if the actual value of 'f' will be known only after 5 seconds. 
   * (Or later if no resources are available to execute the function.) 
   *
   */

  /* 
   * Task 1:
   * Let us practice computing with Future-objects. Your first task is to
   * implement the following function that increments z by 1.
   * Or in more precise terms, you need to implement a function that
   * returns a Future-object such that when z completes with a value,
   * the returned Future-object completes, eventually, with that value 
   * incremented by 1. 
   *
   * Hint: perhaps z.flatMap or z.map could be used here?
   *       (one line of code suffices)
   *
   */
  def incrementF(z: Future[Int]): Future[Int] = z.map { x => x + 1 }

  /* 
   * Task 2:
   * Implement the following function that sums x and y.
   * Or in somewhat more precise terms, you must return a future 
   * that completes with the sum of the completions of x and y. 
   *
   * Hint: perhaps a for-comprehension (or a chain of flatMaps and/or maps)
   *       is useful here? (one line of code suffices)
   *
   */
  def sumF(x: Future[Int], y: Future[Int]): Future[Int] = x.zip(y).map(x => x._1 + x._2)


  /*
   * Task 3:
   * Let us now engage in systematic encapsulation of program functionality 
   * using futures (that is, "future-lifting" or "futurization"), with 
   * the objective of witnessing the power of futures. 
   * (Make sure you run the object 'play' when you have completed this task!) 
   *
   * Imagine our program needs to access a select set of "services" 
   * (for example, a database query interface and other online services) 
   * that are external to our program. Such services take time to complete 
   * (say, the database must be searched), and while waiting for 
   * completion (success or failure) our program should yield 
   * the computational resources to other uses. In short, our program 
   * should execute aggressively and use all available resources for
   * parallel execution when it has available all the values that it needs 
   * to advance its computations, and otherwise yield the resources to 
   * other use. This should happen automatically and transparently, 
   * without us as programmers paying attention to any of it. Ideally, 
   * our program should have the clarity of a sequential implementation, 
   * even if in practice the computations are carried out in parallel 
   * and scheduled aggressively. 
   *
   * Let us start with a set of "services" that for simplicity take 
   * zero or more integer parameters and an return integer value:
   *
   */

  object Services {
    var dosim = true
    def simulateWork(delay: Int) = { if(dosim) { Thread.sleep(delay) } }
    def A(x: Int): Int = { simulateWork(4000); x }
    def B(x: Int): Int = { simulateWork(2000); 2*x }
    def C(x: Int): Int = { simulateWork(2000); x+1 }
    def D(x: Int): Int = { simulateWork(2000); -x }
    def E(x: Int): Int = { simulateWork(2000); 3*x }
    def P(x: Int, y: Int): Int = { simulateWork(1000); x+y }
    def Q(x: Int, y: Int): Int = { simulateWork(1000); x*y }
  }

  /*
   * Observe that there is nothing magical or parallel about the "services" 
   * above. In fact, each "service" is a function without side effects. 
   * However, you should use your imagination and think about, say,
   * function A( ) in our program interacting with a database server over 
   * the network, possibly with a complex workflow with side effects 
   * (that however do not affect any other parts of the computation)
   * for 4 seconds until the final result (the return value) arrives and 
   * the connection to the database is closed -- 
   * for pedagogical purposes we have intentionally slowed down some of 
   * the functions so that they really do take a macroscopic amount of time. 
   *
   * Suppose we now build a more intricate compound function 
   * ("compound service") from the components above:
   *
   */

  def compoundService(x: Int, y: Int): Int = {
    import Services._
    P(Q(A(y),C(x)),D(Q(P(B(y),E(x)),P(C(x),E(y)))))
  }

  /* 
   * Again observe that there is nothing parallel about the compound
   * function. In fact, it takes 21 seconds to evaluate the compound 
   * function. Let us now change the rules of the game and bring 
   * futures into play. 
   *
   * Here comes your task. You are to write a wrapper function 
   * for each function ("service" in object Service) above. The wrapper 
   * takes future parameters and returns a future value. The returned 
   * future should complete with the value of the underlying function 
   * ("service") once all the future parameter values are completed 
   * and the underlying function returns its value. That is, when the
   * future parameter values are available, your wrapper function
   * should arrange things so that the underlying function 
   * (in object Services) gets called at that point in the future,
   * and the return value from the underlying function will then complete 
   * the future object that your wrapper function returns. 
   *
   * Hint: use maps, flatMaps, and/or for-comprehensions to 
   * carry out the wrapping. Refer back to Tasks 1 and 2 for guidance. 
   * You should not over-engineer this -- each wrap should be no 
   * more than one line of code.
   *
   */

  object ServicesF {
    def A(x: Future[Int]): Future[Int] = x 
    def B(x: Future[Int]): Future[Int] = x.map { x => 2 * x }
    def C(x: Future[Int]): Future[Int] = incrementF(x)
    def D(x: Future[Int]): Future[Int] = x.map { x => - x }
    def E(x: Future[Int]): Future[Int] = x.map { x => 3 * x }
    def P(x: Future[Int], y: Future[Int]): Future[Int] = sumF(x, y)
    def Q(x: Future[Int], y: Future[Int]): Future[Int] = x.zip(y).map(x => x._1 * x._2)
  }

  /* 
   * So let us now "futurize" the intricate compound service:
   *
   */

  def compoundServiceF(x: Future[Int], y: Future[Int]): Future[Int] = {
    import ServicesF._
    P(Q(A(y),C(x)),D(Q(P(B(y),E(x)),P(C(x),E(y)))))
  }

  /*
   * Note in particular that the code is essentially identical to its
   * sequential counterpart. That is, instead of using the original 
   * functions, we are merely using their "futurized" counterparts. 
   *
   * The following should come as a pleasant shock. 
   * If you invoke the futurized compound function with future parameters, 
   * you will observe that the return future completes about 9 seconds 
   * after the future parameters are completed.
   *
   * (Recall that the unwrapped function takes 21 seconds to finish
   * -- again make sure to run the object 'play' to witness what happens!)
   *
   * The futures are scheduled opportunistically for parallel execution
   * as soon as values become available. 
   *
   */


  /*
   * Task 4:
   * Next, let us think about failure and recovery from failure.
   * (Indeed, futures may fail during normal program operation,
   * and our program should recover from such failures.)
   *
   * A future completes either with a value (success) or with 
   * an exception (failure). In particular, the latter happens
   * when an exception is thrown during the computation
   * of the value that is supposed to complete the future,
   * in the thread that is responsible for the computation.
   *
   * A future f that completes with an exception propagates that
   * exception to other futures that depend on the value of f.
   *
   * Complete the following function so that the returned future
   *
   * 1) completes with the value of x 
   *        if x completes successfully;
   * 2) completes with the value of y 
   *        if x fails and y completes successfully; and
   * 3) completes with the exception that failed y
   *        if x fails and y fails.
   *
   * Hint: 
   * Take a look at the available methods in 
   * http://www.scala-lang.org/api/2.11.8/index.html#scala.concurrent.Future
   * 
   */

  def eitherF(x: Future[Int], y: Future[Int]): Future[Int] = x.recoverWith{case _ => y}

  /* 
   * Task 5:
   * Let us conclude this exercise with one more task on recovering
   * from failure.
   * 
   * Complete the following function so that the returned future
   * completes with x (that is, either succeeds or fails) 
   * __unless__ x fails with an ArithmeticException;
   * if x fails with an ArithmeticException, the returned future 
   * should complete with the value 0.
   *
   */

  def zeroOnArithmeticException(x: Future[Int]): Future[Int] = {
    x.recoverWith{
      case are: ArithmeticException => Future(0)
      case _ => x
      }
  }
  
}

