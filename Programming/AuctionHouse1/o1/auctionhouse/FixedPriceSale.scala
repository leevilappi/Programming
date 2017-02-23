package o1.auctionhouse

class FixedPriceSale(val description: String, val price: Int, duration: Int)  {
  
  //description: a short description of the item 
  //      price: the price the item is being sold for
  //   duration: the maximum number of days the sale will remain open
 
  
  private var remainingDays = this.duration
  private var auctioner: Option[String] = None
    
  def advanceOneDay() = {
    if (remainingDays > 0)
      remainingDays = remainingDays - 1
    else
      None
  }
  
  def buy(buyer: String) = {
    if (!this.isExpired && this.isOpen)
      auctioner = Some[String](buyer)
      true
  }
  
  def buyer = {
    if (auctioner == None)
      None
    else
      this.auctioner.get: String
  }
  
  def daysLeft = this.remainingDays
       
  def isExpired = if (daysLeft == 0 ) true else false
  
  def isOpen = if (!isExpired && buyer == None ) true else false
  
  override def toString = this.description 
  
}