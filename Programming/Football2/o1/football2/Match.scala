package o1.football2

import scala.collection.mutable.Buffer
import scala.math._


/** The class `Match` represents match results in a football match statistics program. 
  * A match is played between teams from two clubs: a home club and an away club. 
  * Goals scored by players of either team can be added to the match object with the 
  * method `addGoal`.
  *
  * The class is expected to be used so that a match object with no goals is initially 
  * created as a real-life match starts. Goals are added incrementally as the match 
  * progresses. (A match object has mutable state.)
  *
  * @param home  the club whose team plays at home in the match
  * @param away  the club whose team plays away in the match */
class Match(val home: Club, val away: Club) {

  private val homeScorers = Buffer[Player]()    // container: goalscorers of the home team are added here
  private val awayScorers = Buffer[Player]()    // container: goalscorers of the away team are added here
  
  def winner = { 
    if (this.goalDifference < 0)
      Some(this.away)
    else if (this.goalDifference > 0)
      Some(this.home)
    else  
      None 
  }

  def addGoal(scorer: Player): Unit = {
    if (scorer.employer == this.home) {
      this.homeScorers += scorer
    } else {
      this.awayScorers += scorer
    }
  }
  
	def homeGoals : Int = this.homeScorers.length
	
	def awayGoals : Int = this.awayScorers.length
	
	def goalDifference: Int = this.homeGoals - this.awayGoals
	
	def totalGoals: Int = homeGoals + awayGoals
	 
	def isHomeWin = this.homeGoals > this.awayGoals	
  def isAwayWin = this.homeGoals < this.awayGoals
	
  def isTied = this.homeGoals == this.awayGoals
	
	def isGoalless = this.totalGoals == 0
	
	def isHigherScoringThan(anotherMatch: Match): Boolean = this.totalGoals > anotherMatch.totalGoals 
	
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
  
  def winningGoalScorer: Option[Player] = {
    if (isHomeWin) {
      Some(homeScorers(homeGoals - abs(goalDifference) ) )
    } else if (isAwayWin) {
      Some(awayScorers(awayGoals - abs(goalDifference) ) )
    } else {
      None
    }
  }
    
  
}

  
  
