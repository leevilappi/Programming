package o1.family

object PersonTest extends App {
  val mukula = new Person("Mukula")
  val jeesus = new Person("Jeesus", Vector(mukula))
  val maria = new Person("Maria", Vector(jeesus))  
  println(maria.numberOfDescendants)
}