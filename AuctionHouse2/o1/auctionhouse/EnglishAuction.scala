package o1.auctionhouse
	
	import scala.math._

	class EnglishAuction(description: String, val startingPrice: Int, duration: Int) extends ItemForSale(description: String){
	
	  private var highestBid = new Bid(None, startingPrice)       // most-wanted holder
	  private var secondHighestBid = new Bid(None, startingPrice) // most-wanted holder
	  private var remainingDays = duration                            // stepper
	  
	  
	  /** Returns the number of days left before the auction closes. A return value of 
	    * zero indicates that time has run out and the auction has closed. */
	  def daysLeft = this.remainingDays
	
	
	  /** Records one day as having passed. For an English auction, this simply means that 
	    * if the auction is still open, its remaining duration is shortened by one day. */
	  def advanceOneDay() = {
	    if (this.isOpen) {
	      this.remainingDays -= 1
	    }
	  }
	
	  
	  /** Determines if the auction is open, that is, if the item can still be bought. An 
	    * English auction is always open if its allocated duration has not been reached yet. */
	  def isOpen = this.remainingDays > 0 
	
	  
	  /** Determines if the auction has expired, that is, if it has ended without the item 
	    * being sold. An English auction expires if the allocated duration has passed and no
	    * bids have been made. */
	  def isExpired = !this.isOpen && this.highestBid.isInitialBid
	
	  
	  /** Returns the winner of the auction, or the current highest bidder if the auction has 
	    * not closed yet. The person's name is wrapped in an `Option`; `None` is returned if 
	    * no bids have been made. */
	  def buyer = this.highestBid.bidder
	  
	   
	  /** Returns the price the item is currently selling for, that is, the money that the 
	    * currently winning bidder would have to pay if the auction were to close now (or 
	    * must pay if the auction is already closed).
	    *
	    * If there are no bids or there is just a single bid on the item, the current price 
	    * equals the starting price of the item. If there is more than one bid, then the 
	    * current price is the price that is just enough for the highest bidder to beat the 
	    * second highest bidder (as described in the introduction to this class). 
	    *
	    * Note that if the two highest bids are equal, the first bidder always wins over the 
	    * second. The first bidder does not have to pay over their bid limit simply because 
	    * someone else also bid the same amount. */
	  def price =
	    if (this.secondHighestBid.isInitialBid) 
	      this.startingPrice 
	    else 
	      min(this.secondHighestBid.limit + 1, this.highestBid.limit) 
	
	  
	  /** Returns the lowest amount that a would-be bidder will have to bid for this item so
	    * as to stand a chance of winning the auction. If there are no bids on the item yet, 
	    * the minimum bid amount equals the starting price of the auction. Otherwise, the bid 
	    * amount must be higher than the current price of the auction. (See the introduction 
	    * to this class for an example.)
	    * @see [[price]] */
	  def requiredBid = if (this.highestBid.isInitialBid) this.startingPrice else this.price + 1
	    
	
	  /** Places a bid on the item, assuming that the auction is open. If the auction is not 
	    * open, this method does nothing except return `false`.
	    * @param bidder  the name of the bidder
	    * @param amount  the maximum amount the bidder is willing to pay for the item
	    * @return a boolean value indicating whether the new bid is now the leading bid for the item */
	  def bid(bidder: String, amount: Int) = {
	    val newBid = new Bid(Some(bidder), amount)
	    if (this.isOpen) {
	      if (newBid.beats(this.highestBid)) {
	        this.secondHighestBid = this.highestBid
	        this.highestBid = newBid
	      } else if (newBid.beats(this.secondHighestBid)) {
	        this.secondHighestBid = newBid
	      }
	    } 
	    this.highestBid == newBid
	  }

	  
	}
	
	