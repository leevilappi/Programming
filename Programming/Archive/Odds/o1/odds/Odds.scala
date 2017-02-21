package o1.odds

// This class is gradually developed between Chapters 2.4 and 3.1.

class Odds(val wont: Int, val will: Int) {
  
  override def toString = this.fractional
  
  def probability = 1.0 * this.will / (this.wont + this.will)
  
  def fractional = this.wont + "/" + this.will
  
  def decimal = {
    import math.pow
    pow(probability, -1)
  }
  def winnings(bet: Double) = bet * decimal
    
  def isLikely = {
    probability > 1 - probability
  }
  
  def isLikelierThan(another: Odds) = this.probability > another.probability
    
  def moneyline = {
    
    import math.floor
    
    val likelyHood = probability
    if (likelyHood < 0.5)
      100 * this.wont / this.will
    else if (likelyHood > 0.5)
      -100 * this.will / this.wont 
    else
      100
  }

  def both(other: Odds) = {
    new Odds(this.wont * other.wont + this.wont * other.will + this.will * other.wont,
             this.will * other.will)

  }
  
  def either(other: Odds) =
    new Odds(this.wont * other.wont,
             this.wont * other.will + this.will * other.wont + this.will * other.will)
             
  def not = {
    new Odds(this.will, this.wont)
  }
  
}
