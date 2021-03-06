package o1.auctionhouse
import  scala.math._


class DutchAuction(description: String, val startingPrice: Int, val decrement: Int, val minimumPrice: Int) extends InstantPurchase(description: String) { 
	  
	  private var reducingPrice = this.startingPrice   // stepper: downwards from starting price to minimumPrice 
	  private var stagnantDays = 0                     // stepper: 0, 1, 2, ...
	  private var buyerName: Option[String] = None     // fixed value (once set) 
	
	  def advanceOneDay() = {
	    if (this.isOpen) {
	      if (this.reducingPrice > this.minimumPrice) {
	        this.reducingPrice = max(this.minimumPrice, this.reducingPrice - this.decrement)
	      } else {
	        this.stagnantDays += 1
	      }
	    }
	  }  
	  
	   def isExpired = {
	    val StagnationLimit = 3
	    this.stagnantDays > StagnationLimit 
	  }
	 
	  def price = this.reducingPrice 
	  	  
	  def priceRatio = this.price.toDouble / this.startingPrice
	
	  
	
	
	  
}