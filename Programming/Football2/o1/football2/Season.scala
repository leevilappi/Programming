package o1.football2

import scala.collection.mutable.Buffer

class Season {
  
  private var matches = Buffer[Match]() 
  private var bigWin: Option[Match] = None
    
  def addResult(newResult: Match) = {
  
    if (bigWin.isEmpty){
      bigWin = Some(newResult)
    } else {
      bigWin: Option[Match] = if (this.bigWin.get.goalDifference < newResult.goalDifference) Some(newResult)
    }
    
    matches += newResult
  }
    
  
  
  def latestMatch: Match = matches(matches.size - 1) 
  
  def matchNumber(number: Int): Option[Match] = {
    if(!matches.isEmpty && number <= matches.size){
      Some(matches(number))
    }else {
      None
    }
  }
  
  def numberOfMatches: Int = matches.size
}