package o1.time

object TimeTest extends App {
  
  val test1 = new Moment(1900)
  val test2 = new Moment(2000)
  val test3 = new Moment(1950)
  val test4 = new Moment(2000)
  val test5 = new Moment(1960)
  val test6 = new Moment(2050)
  
  val tests1 = new Interval(test1,test2) // 1900 - 2000
  val tests2 = new Interval(test3,test4) // 1950 - 2000
  val tests3 = new Interval(test3,test5) // 1950 - 1960
  val tests4 = new Interval(test3,test6) // 1950 - 2050
  
  println(tests1.contains(test3))
  println(tests1.contains(test4))
  println(tests1.contains(test2))
  println(tests1.contains(tests3))
  println(tests1.contains(tests2))
  println(tests1.contains(tests4))
  
}