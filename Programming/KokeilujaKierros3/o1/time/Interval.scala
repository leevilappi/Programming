package o1.time
import scala.math._

class Interval(val start: Moment, val end: Moment) {
   
  def length: Int = start.distance(end)
  
  override def toString = {
     if (length == 0) {
       start.toString
     } else if (length <= 50) {
       start.toString + "-" * length + end.toString
     } else {
       start.toString + "..." + end.toString
     }
  }
  
  def isLaterThan(another: Moment): Boolean = this.start.isLaterThan(another)
  
  def isLaterThan(another: Interval): Boolean = this.end.isLaterThan(another.start)
  
  def contains(another: Moment): Boolean = !another.isLaterThan(this.end) && another.isLaterThan(this.start) || another == this.start
      
  def contains(another: Interval): Boolean = !this.isLaterThan(another) && !another.isLaterThan(this) && !this.start.isLaterThan(another.start) && this.end.isLaterThan(another.end)

 def overlaps(another: Interval): Boolean = {
   if (this.contains(another)) {
     true
   } else if (another.contains(this)){
     true
   } else if (this.end == another.start || this.start == another.end){
     true
   } else if (this.contains(another.start) || this.contains(another.end)){
     true
   } else {
     false
   }
 }
    
    
  def union(another: Interval): Interval = {
    if (this.contains(another)){
      new Interval(this.start, this.end)
    }else if (another.contains(this)){
      new Interval(another.start, another.end)
    }else if (this.contains(another.start)){
      new Interval(this.start,another.end)
    }else if (this.contains(another.end)){
      new Interval(another.start,this.end)
    }else{
      new Interval(this.start, another.end)
    }
  }
    
   
  def intersection(another: Interval): Option[Interval] = {
    if(this.overlaps(another)) {
      
        val beginning = {
          if (this.start.isLaterThan(another.start)){
            this.start
         }else {
            another.start
         }
        }
        val ending = {
         if (this.end.isLaterThan(another.end)){
           another.end
         } else{
           this.end
         }
        }
        
        Some(new Interval(beginning,ending))
     
    } else {
      None
    }
  }
}
