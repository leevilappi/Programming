package o1.auctionhouse
	
	
class FixedPriceSale(description: String, val price: Int, duration: Int) extends InstantPurchase(description: String){
	  
	  private var remaining = duration                 // stepper
	  private var buyerName: Option[String] = None     // fixed value (once set) 
	
	  def advanceOneDay() = {
	    if(this.isOpen){
	      this.remaining -= 1
	    }
	  }

	  def daysLeft: Int = this.remaining
	  
	  def isExpired: Boolean = this.remaining == 0 && this.buyerName.isEmpty 
	 


	  
	}
	
	