package o1.carsim

class Car(val fuelConsumption: Double, val tankSize: Double, initialFuel: Double, initialLocation: Location) {

  def location: Location = ???                                         // TODO:: replace ??? with a working implementation in Chapter 5.1
 
  def fuel(toBeAdded: Double): Double = ???                            // TODO:: replace ??? with a working implementation in Chapter 5.1
  
  def fillUp(): Double = ???                                           // TODO:: replace ??? with a working implementation in Chapter 5.1
  
  def fuelRatio: Double = ???                                          // TODO:: replace ??? with a working implementation in Chapter 5.1
    
  def metersDriven: Double = ???                                       // TODO:: replace ??? with a working implementation in Chapter 5.1

  def fuelRange: Double = ???                                          // TODO:: replace ??? with a working implementation in Chapter 5.1
  
  def drive(destination: Location, metersToDestination: Double) = ???  // TODO:: replace ??? with a working implementation in Chapter 5.1
  
}

