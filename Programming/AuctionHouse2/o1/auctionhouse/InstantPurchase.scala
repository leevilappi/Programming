package o1.auctionhouse


abstract class InstantPurchase(description: String) extends ItemForSale(description: String) {
  
  // Classes own methods
  private var buyerName: Option[String] = None
  
  def buyer = this.buyerName
  
  def buy(buyer: String) = {
    if (this.isOpen) {
      this.buyerName = Some(buyer)
       true
     } else {
       false
     }
   }
  
  def isOpen: Boolean = {
    if (!this.isExpired && this.buyer.isEmpty){
      true
    } else {
      false
    }
  }
  
  
  
}