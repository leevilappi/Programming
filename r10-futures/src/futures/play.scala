
package futures

import scala.concurrent._
import ExecutionContext.Implicits.global

object play {

  def withWallClock[T](work: =>T) = { 
    val start = System.nanoTime
    val r = work
    val stop = System.nanoTime
    (r,(stop-start)/1e9) 
  }

  def main(args: Array[String]) {
    println("Running the compound service without futures...")
    val xx = 1
    val yy = 2
    val (rr,t) = withWallClock(compoundService(xx,yy))
    println("... xx = %d, yy = %d gives output %d in time %f seconds".format(xx,yy,rr,t))
    println("Now running the compound service WITH futures...")
    val x = Future { xx }
    val y = Future { yy }
    val (rrf,tf) = withWallClock(Await.result(compoundServiceF(x,y),duration.Duration.Inf))
    println("... xx = %d, yy = %d gives output %d in time %f seconds".format(xx,yy,rrf,tf))
  }

}


