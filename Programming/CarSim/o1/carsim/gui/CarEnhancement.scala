
////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file. 
//////////////////////////////////////////////////////////////


package o1.carsim.gui

import o1.carsim._
import CarEnhancement._
import CarSim.Directions
import Directions.Segment


/** The class `CarEnhancement` represents cars using the student-created `Car` class to do it. 
  * Its functionality contains some things we didn't want to put in the car exercise.  
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this class works or can be used.'''
  *
  * (This class is not really a part of the GUI ''per se'', but is here as an artifact of the programming assigment.) */
class CarEnhancement(consumption: Double, tankSize: Double, fuelInTank: Double, initialLocation: Location) {

  private val car = new Car(consumption, tankSize, fuelInTank, initialLocation) {
    override def fuelRatio: Double =    try { if (hasFiniteFuel) super.fuelRatio else 100.0 }                   catch { case missing: NotImplementedError => 0.0 }
    override def fuelRange: Double =    try { if (hasFiniteFuel) super.fuelRange else Double.PositiveInfinity } catch { case missing: NotImplementedError => 0.0 }
    override def location: Location =   try { super.location }                                                  catch { case missing: NotImplementedError => initialLocation }
    override def metersDriven: Double = try { super.metersDriven }                                              catch { case missing: NotImplementedError => 0.0 }
  }
  
  private var itinerary = Iterator[Segment]()
  
  private var reachedDestination = false
    
  def isAtDestination = this.reachedDestination
  
  def location = this.car.location 
   
  def isOutOfFuel = this.hasFiniteFuel && this.car.fuelRatio < 0.0000000000000000000001 
  
  def findRoute(destination: String) = {
    this.reachedDestination = false
    val segments = Directions.findRoute(this.car.location.toString, destination).toIndexedSeq
    this.itinerary = segments.iterator  
    segments
  }
  
  def fuel(amount: Double) = { 
    this.car.fuel(amount)
  }
  
  def fillUp() = {
    this.car.fillUp()
  }
  
  def advance() = {
    val currentSegment = this.itinerary.next()
    this.car.drive(currentSegment.destination, currentSegment.distance)
    if (this.itinerary.isEmpty) {
      this.reachedDestination = true
    }
    currentSegment.distance
  }
        
  def isIdle = this.itinerary.isEmpty || this.isOutOfFuel
    
  private def hasFiniteFuel: Boolean = this.car.fuelConsumption > 0
    
  def fuelRatio = this.car.fuelRatio 
  
  def kilometersDriven = this.car.metersDriven / 1000    
  
  
}
  

private object CarEnhancement {
  import scala.language.implicitConversions
  
  implicit def directionsServiceCoordsToLocation(coords: Directions.Coords): Location = Location(coords.lat, coords.lng) 
  
}