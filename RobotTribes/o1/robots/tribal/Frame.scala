package o1.robots.tribal


/** Each `Frame` object represents a data frame in a tribal bot's call stack. A frame stores data 
  * relevant to a single RoboSpeak subprogram call. A stack frame is associated not with the code 
  * of a subprogram, but with a subprogram call that happens during program execution. 
  *
  * Tribal bots' subprogram calls -- unlike Scala methods -- don't have any parameters or local 
  * variables, and very little data needs to be stored in each `CallStackFrame`. In fact, only a 
  * single line number needs to be stored to indicate where the subprogram call was made (in order
  * to determine where program execution should resume once the bot returns from the subprogram).
  *
  * @param returnLine  the number of the line on which the subprogram call was made that created this frame
  *
  * @see [[Tribe]]
  * @see [[TribalBot.callSubprogram]]
  * @see [[TribalBot.returnFromSubprogram]] */
class Frame(val returnLine: Int) {

  
  /** Returns a textual representation of this frame, for debugging purposes. */
  override def toString = "called from line " + this.returnLine
  
  
}
