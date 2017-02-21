package o1.odds

// This class is gradually developed between Chapters 2.4 and 3.1.

class Odds(val wont: Int, val will: Int) {
  
  def probability = 1.0 * this.will / (this.wont + this.will)
  
  // TODO: other methods missing
  
}
