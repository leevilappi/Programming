
////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file. 
//////////////////////////////////////////////////////////////

package o1.gui

import scala.util.Try
import scala.swing._
import scala.swing.Dialog._


object Dialog {

  
  def display(message: String, locator: Option[Component]) = {
    showMessage(locator.getOrElse(null), message, "", Message.Plain, Swing.EmptyIcon)
  }
  
  def requestInput[ResultType](prompt: String, convert: String => ResultType, isOk: ResultType => Boolean, errorMessage: String, locator: Option[Component]) = {
    val isReallyOk = (input: String) => Try(isOk(convert(input))).getOrElse(false)
    val parent = locator.getOrElse(null)
    var inputLine = showInput(parent, prompt, prompt, Message.Question, Swing.EmptyIcon, Nil, "")
    while (inputLine.exists( !isReallyOk(_) )) {
      inputLine = showInput(parent, errorMessage + "\n" + prompt, prompt, Message.Error, Swing.EmptyIcon, Nil, "")
    }
    inputLine.map( convert(_) )
  }
  
  def requestString(prompt: String, isOk: String => Boolean, errorMessage: String, locator: Option[Component]) = {
    requestInput(prompt, _.trim, isOk, errorMessage, locator)
  }
  
  
  def requestAnyLine(prompt: String, locator: Option[Component]) = {
    requestString(prompt, AnythingGoes, "", locator)
  }
  
  def requestNonEmptyLine(prompt: String, errorMessage: String, locator: Option[Component]) = {
    requestString(prompt, !_.isEmpty, errorMessage, locator)
  }

  def requestInt(prompt: String, isOk: Int => Boolean, errorMessage: String, locator: Option[Component]) = {
    requestInput(prompt, _.toInt, isOk, errorMessage, locator)
  }
  
  def requestAnyInt(prompt: String, errorMessage: String, locator: Option[Component]) = {
    requestInt(prompt, AnythingGoes, errorMessage, locator)
  }
  
  private val AnythingGoes = (_: Any) => true 
  
  def requestDouble(prompt: String, isOk: Double => Boolean, errorMessage: String, locator: Option[Component]) = {
    requestInput(prompt, _.toDouble, isOk, errorMessage, locator)
  }
  
  def requestAnyDouble(prompt: String, locator: Option[Component]) = {
    requestDouble(prompt, AnythingGoes, "", locator)
  }
  
  def requestChoice[Choice](prompt: String, options: Seq[Choice], locator: Option[Component]) = {
    showInput(locator.getOrElse(null), prompt, prompt, Message.Question, Swing.EmptyIcon, options, options.head) 
  } 
      
      
}
 
