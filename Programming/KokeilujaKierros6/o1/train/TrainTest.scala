package o1.train

object TrainTest extends App{
  
  val T = new Train("IC")
  val SC = new SleepingCar(10)
  
//  //Add some train cars
//  println(T.addCar(SC))
//  println(T.addCar(SC2))
  
//  for(i <- 1 to 30){
//    println(T.addCar(new SleepingCar(10)))
//    println(T.length + "\n")
//  }
//  
//  println(T.addCar(new SleepingCar(10)))
//  //Test few car numbers
//  
//  for(i <- 0 to 30){
//
//    println("Car number: " + i + " " + T.car(i))
//  }
//  
//  //What is length of the train
//  println(T.length)
//  
  
//  
//  //Reserve some places
//  println("Reserve some places" + "\n")
//  println("Reserving 9 places, " + SC.reservePlaces(9) + "\n")
//  
//  //Test for free beds in a cabin
//  for (i <- 1 to 10) {
//  println("Cabin number " + i + " available beds:  " + SC.numberOfFreeBedsInCabin(i))
//  }
//  println("Reserving 14 places, " + SC.reservePlaces(14) + "\n") 
//  
//  //Test for free beds in a cabin
//  for (i <- 1 to 10) {
//  println("Cabin number " + i + " available beds:  " + SC.numberOfFreeBedsInCabin(i))
//  }
//  println("Reserving 7 places, " + SC.reservePlaces(7) + "\n") 
//  
//  //Test for free beds in a cabin
//  for (i <- 1 to 10) {
//  println("Cabin number " + i + " available beds:  " + SC.numberOfFreeBedsInCabin(i))
//  }
//  
//  println(SC.numberOfFreePlaces)
//  
//  //Test for free beds in a cabin
//  for (i <- 1 to 10) {
//  println("Cabin number " + i + " available beds:  " + SC.numberOfFreeBedsInCabin(i))
//  }
  
//  
//  
//  
//  
//  
//  
//    //Test if new car is empty, when created
//  println(SC.emptyCabinCount)
//
//  
//  //Reserve some cabins
//  println("Reserve some cabins" + "\n")
//  for (i <- 1 to 10){
//    println(SC.emptyCabinCount)
//    println("Reserve time: " + i)
//    println("Number of free places: " + SC.numberOfFreePlaces + " -> RESERVE CABIN NUMBER: " + i)
//    SC.reserveCabin(i)
//    println("Number of free places: " + SC.numberOfFreePlaces)
//    println("Cabin number: " + i + " is empty " + SC.isEmptyCabin(i) + "\n")
//
//    
//  }
//  
//  
//  println(SC.emptyCabinCount)
//
//  println("Number of free places: " + SC.numberOfFreePlaces)
//
//  
  println("Number of cabins:" + SC.numberOfCabins) 
  println("Number of places:" + SC.numberOfPlaces)
  
  //Test if full
  println("Fullness: " + SC.fullness )
  
  
  
  //Test for free beds in a cabin
  for (i <- 1 to 10) {
  println("Cabin number " + i + " available beds:  " + SC.numberOfFreeBedsInCabin(i))
  }
  
  
  
  
}