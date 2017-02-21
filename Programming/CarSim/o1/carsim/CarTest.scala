package o1.carsim

/** A small program that uses the class `Car`. May be extended and customized by the student. */
object CarTest extends App {

  private def printReport(car: Car) = {
    println(s"Now at (${car.location})")
    println(s"Fuel ratio: ${car.fuelRatio} %")
    println(s"Fuel range: ${car.fuelRange} m")
    println(s"Driven: ${car.metersDriven} m")
  }
  
  val testCar = new Car(10.0, 200.0, 35.0, Location(-100, 200)) // using (weird) round figures for simplicity
  println("Car created.")
  printReport(testCar)
  
  println(s"\nAdding 5 liters.")
  testCar.fuel(5) 
  printReport(testCar)
  
  println(s"\nDriving.")
  // This 1000 km drive drains all the fuel, which is only enough for 40% of the trip. Should end up at (-140.0, 180.0).
  testCar.drive(Location(-200, 150), 1000000) 
  printReport(testCar)
  
  // Modify the above and/or add your own test code here, if you wish.
  

}
