package o1.football3

class Match(val home: Club, val away: Club, val homeScorers: Vector[Player], val awayScorers: Vector[Player]) {

 def addGoal(scorer: Player): Match = {
   if(scorer.employer == this.home ){
    new Match(home, away, homeScorers :+ scorer, awayScorers )
   }else if (scorer.employer == this.away){
    new Match(home, away, homeScorers,awayScorers :+ scorer)
   }else{
    this
   }
 }
 
 def awayGoals = this.awayScorers.size
 
 def homeGoals = this.homeScorers.size
 
 def goalDifference = this.homeScorers.size - this.awayScorers.size
 
 def totalGoals = this.homeScorers.size + this.awayScorers.size
 
 def isAwayWin = this.homeGoals < this.awayGoals
 
 def isHomeWin = this.homeGoals > this.awayGoals
 
 def isGoalless = this.totalGoals == 0

 def isHigherScoringThan(anotherMatch: Match) = this.totalGoals > anotherMatch.totalGoals
 
 def winner: Option[Club] = { 
	    if (this.goalDifference < 0)
	      Some(this.away)
	    else if (this.goalDifference > 0)
	      Some(this.home)
	    else  
	      None 
 }
 
 def winningGoalScorer: Option[Player] = {  
	    if (this.goalDifference < 0)
	      Some(this.awayScorers(this.homeGoals))
	    else if (this.goalDifference > 0)
	      Some(this.homeScorers(this.awayGoals))
	    else  
	      None 
 }
 
 def isTied = this.homeGoals == this.awayGoals
 
 def location = this.home.stadium 
 
 override def toString = {
	    val heading = this.home + " vs. " + this.away + " at " + this.location + ": " 
	    if (this.isGoalless) 
	      heading + "tied at nil-nil" 
	    else if (this.isTied) 
	      heading + "tied at " + this.homeGoals + "-all" 
	    else if (this.isHomeWin)    
	      heading + this.homeGoals + "-" + this.awayGoals + " to " + this.home
	    else     
	      heading + this.awayGoals + "-" + this.homeGoals + " to " + this.away
	  }
}