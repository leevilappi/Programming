
////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file. 
//////////////////////////////////////////////////////////////

package o1

import scala.language.reflectiveCalls
import scala.util._
import scala.io.Source
import java.net.URL
import java.io.FileNotFoundException

package object util {

  
  def useAndClose[Resource <: Closeable, Result](resource: Resource)(operation: Resource => Result) = {
    try {
      operation(resource)
    } finally {
      resource.close()
    }
  }

  type Closeable = { def close(): Unit }

  
  implicit class ConvenientCollection[Element](val underlying: Traversable[Element]) {
    def mapify[Key, Value](formKey: Element => Key)(formValue: Element => Value): Map[Key, Value] =
      this.underlying.map( elem => formKey(elem) -> formValue(elem) )(collection.breakOut)
  }
  
  

  def editDistance(text1: String, text2: String, threshold: Int): Int =
    if (text1.isEmpty)
      if (text2.length <= threshold) text2.length else Int.MaxValue
    else if (text2.isEmpty)
      if (text1.length <= threshold) text1.length else Int.MaxValue
    else if (text1.head == text2.head)
      editDistance(text1.tail, text2.tail, threshold)
    else if (threshold == 0) 
      Int.MaxValue 
    else { 
        val deletion     = editDistance(text1.tail, text2     , threshold - 1)
        val insertion    = editDistance(text1,      text2.tail, threshold - 1)
        val substitution = editDistance(text1.tail, text2.tail, threshold - 1)
        val shortest = Seq(deletion, insertion, substitution).min
        if (shortest == Int.MaxValue) Int.MaxValue else shortest + 1
    }

  
  // these two lines are based on http://etorreborre.blogspot.fi/2011/11/practical-uses-for-unboxed-tagged-types.html
  type Tagged[TagType] = { type Tag = TagType }
  type @@[BaseType, TagType] = BaseType with Tagged[TagType]


  
  def findResource[Result](id: String, transform: URL => Result) = {
    def withinProject(filename: String)                 = Try { Option(this.getClass.getResource("/" + filename)).map(transform).getOrElse(throw new FileNotFoundException) }
    def url(url: String, prefix: Option[String] = None) = Try { transform(new URL(prefix.getOrElse("") + url)) }
    withinProject(id) orElse url(id) orElse url(id, Some("http://")) orElse url(id, Some("https://"))
  }
  
  def findURL(id: String) = findResource(id, identity) 
  
  def findSource(id: String) = findResource(id, url => Source.fromURL(url, "UTF-8"))
  

  
  implicit class RegexContext(interpolated: StringContext) {
    import scala.util.matching.Regex
    def r = new Regex(interpolated.parts.mkString, interpolated.parts.tail.map( _ => "unnamedGroup" ): _*)
  }
  
}
