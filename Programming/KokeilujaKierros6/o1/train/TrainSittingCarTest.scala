package o1.train

object TrainSittingCarTest extends App{
  
  val SC = new SittingCar(5, 4)
    
  val places = "abcdef"
             // 012345
  
  println("Number of places: " + SC.numberOfPlaces)
  println("Number of free places: " + SC.numberOfFreePlaces)
  println("Fullness: " + SC.fullness)

//  println(SC.reservePlace(5, places(3)))
//  println(SC.reservePlace(1, places(2))) //1b
//  println(SC.reservePlace(2, places(2))) //2b
//  println(SC.reservePlace(3, places(2))) //3b
//  println(SC.reservePlace(4, places(2))) //4b
//  println(SC.reservePlace(5, places(2))) //5b
//  
//  for(i <- 0 to 3){
//    println(SC.reservePlace(1, places(i)))
//  }
  
//  SC.reservePlaces(1)
//  
//  SC.reservePlaces(4)
  
  println(SC.isReservedSeat(1, 'a'))
  println(SC.isReservedSeat(1, 'b'))
    
  println(SC.reservePlaces(4))
  println(SC.reservePlaces(2))
  println(SC.reservePlaces(2))
  
  println("2a " + SC.isReservedSeat(2, 'a'))
  println("2b " + SC.isReservedSeat(2, 'b'))
  println("2c " + SC.isReservedSeat(2, 'c'))
  println("2d " + SC.isReservedSeat(2, 'd'))

//  SC.reservePlaces(2)
  
  println("Fullness: " + SC.fullness)
  println("Number of free places: " + SC.numberOfFreePlaces)
 
  
  
//  println("Try to find adjacents")
//  for(i <- 0 to 5){
//    println("Current row in check: " +  (i + 1).toInt + " , found seats: " + SC.findAdjacents(i,0,3))
//  }
   
  
}