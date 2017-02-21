package o1.auctionhouse

import scala.collection.mutable.Buffer

class AuctionHouse(val name: String) {
  
  private val items = Buffer[ItemForSale]()  

  def addItem(item: ItemForSale): Unit = {
    this.items += item
  }

  def removeItem(item: ItemForSale): Unit = {
    this.items -= item
  }
 
  override def toString = if (this.items.isEmpty) this.name else this.name + ":\n" + this.items.mkString("\n")

  def nextDay() = {
    for (current <- this.items) {
      current.advanceOneDay()
    }
  }

  def totalPrice = {
    var totalSoFar = 0
    for (current <- this.items) {
      totalSoFar += current.price
    }
    totalSoFar
  }

  def averagePrice = this.totalPrice.toDouble / this.items.size

  def numberOfOpenItems = {
    var openCount = 0
    for (current <- this.items) {
      if (current.isOpen) {
        openCount += 1
      }
    }
    openCount
  }

  def priciest = {
    if (this.items.isEmpty) {
      None 
    } else {
      var priciestSoFar = this.items(0) 
      for (current <- this.items) {  
        if (current.price > priciestSoFar.price) {
          priciestSoFar = current
        }
      }
      Some(priciestSoFar)
    }
  }

  def purchasesOf(buyer: String) = {
    val purchases = Buffer[ItemForSale]()
    for (current <- this.items) {
      if (current.buyer == Some(buyer)) {
        purchases += current
      }
    }
    purchases
  }
  
  def findAll(checkCriterion: ItemForSale => Boolean) = {
    val found = Buffer[ItemForSale]()
    for (currentItem <- this.items) {
      if (checkCriterion(currentItem)) {
        found += currentItem
      }
    }
    found.toVector
  }

}

object FindAllTest extends App {

  def checkIfOpen(candidate: ItemForSale) = candidate.isOpen

  def checkIfHandbag(candidate: ItemForSale) = candidate.description.toLowerCase.contains("handbag")

  val house = new AuctionHouse("ReBay")
  house.addItem(new EnglishAuction("A glorious handbag", 100, 14))
  house.addItem(new EnglishAuction("Collectible Easter Bunny China Thimble", 1, 10))

  println(house.findAll(j1 => j1.isOpen) )    // finds both auctions
  println(house.findAll(j2 => j2.description.toLowerCase.contains("handbag"))) // finds only the first auction
  
  println(house.findAll(_.isOpen)) 
}

