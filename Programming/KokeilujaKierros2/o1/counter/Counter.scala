package o1.counter
import scala.math.min

/** The class `Counter` represents simple counters that keep track of a number 
  * of "hits" (such as a retro visitor counter on a web page). Essentially, a 
  * counter object can be used to keep track of any value that increases 
  * stepwise and has an upper limit.
  *
  * A counter object's state is mutable. Each counter's value starts at zero 
  * and increases by one every time its `advance` method is called, as this
  * REPL session illustrates:
  *
  * {{{
  *  INPUT: val hits = new Counter(100)
  * OUTPUT: hits: o1.counter.Counter = o1.counter.Counter@b3b273
  *
  *  INPUT: hits.value
  * OUTPUT: res0: Int = 0
  *
  *  INPUT: hits.advance()
  *
  *  INPUT: hits.value
  * OUTPUT: res2: Int = 1
  *
  *  INPUT: hits.advance()
  *
  *  INPUT: hits.value
  * OUTPUT: res4: Int = 2
  * }}}  
  *
  * Each counter has a maximum value. For instance, if the maximum value of a 
  * counter is 100, the counter's value never goes past one hundred even if 
  * the `advance` method gets called more times.
  *
  * @param upperLimit  a positive integer that the new counter's value can never exceed */
class Counter(val upperLimit: Int) {

  private var currentValue = 0     // stepper
  
  
  /** Returns the counter's current value (between zero and `upperLimit`). */
  def value = this.currentValue
  
  
  /** Advances the counter by one, but only if the upper limit would not be exceeded. */
  def advance() = { 
    this.currentValue = min(this.upperLimit, this.currentValue + 1)
  }
  
}