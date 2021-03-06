package o1.election
	
	import scala.collection.mutable.Buffer
	
	
	/** The class `District` represents electoral districts (Finnish: ''vaalipiiri'').
	  * Each district has a certain number of seats for which a (larger) number of 
	  * candidates compete in an election. Each district has its own candidates.  
	  *
	  * A district object is immutable.
	  *
	  * @param name        the name of the electoral district
	  * @param seats       the number of seats (elected positions) available
	  * @param candidates  the candidates vying for the seats in the district; there is always at least one candidate per district */
	class District(val name: String, val seats: Int, val candidates: Vector[Candidate]) {
	
	  
	  /** Returns a textual description of the district. The description is of the
	    * form `Name: X candidates, Y seats`, where `Name` is the name of the district,
	    * and `X` and `Y` the numbers of candidates and seats, respectively.
	    * For instance, might return the string `Helsinki: 1064 candidates, 85 seats` */
	  override def toString = this.name + ": " + this.candidates.size + " candidates, " + this.seats + " seats"   
	
	   
	  /** Prints (to the console) a description of each candidate in this district, each on
	    * a line of its own. A candidate's description is obtained by calling its `toString` method.
	    *
	    * NOTE TO STUDENTS: It's not usually a good idea to create "print some of your data"
	    * methods ("return some information" methods are better), but we're still making one 
	    * as an exercise here. More about this in later chapters. */
	  def printCandidates() = {
	    for (candidate <- this.candidates) {
	      println(candidate)
	    }
	  }  
	 
	
	  /** Returns all the candidates in this district that belong to the party named by the parameter. */ 
	  def candidatesFrom(party: String) = {
	    var fromParty = Buffer[Candidate]()
	    for (candidate <- this.candidates) {
	      if (candidate.party == party) {
	        fromParty += candidate
	      }  
	    }
	    fromParty.toVector
	  }
	
	  
	  /** Returns the top candidate (Finnish: ''ääniharava'') of the district. That is, returns the 
	    * candidate with the highest number of votes. If multiple candidates have the same number of 
	    * votes, this method chooses one of them arbitrarily. It is assumed that every district always 
	    * has at least one candidate. */
	  def topCandidate = {
	    var topSoFar = this.candidates.head 
	    for (candidate <- this.candidates.tail) {
	      if (candidate > topSoFar) {
	        topSoFar = candidate
	      }
	    }
	    topSoFar
	  }
	  
	  
	  /** Returns the total number of votes received by all the candidates,
	    * that is, the total number of votes cast in this district. */
	  def totalVotes = this.countVotes(this.candidates) 
	
	  
	  /** Returns the total number of votes received by members of the party named by the parameter. */
	  def totalVotes(party: String) = this.countVotes(this.candidatesFrom(party))
	
	  
	  /** Returns the total number of votes received by the given candidates. */
	  private def countVotes(candidates: Vector[Candidate]) = {
	    var voteCount = 0
	    
	    candidates.foreach(voteCount += _.votes )

	    voteCount
	  }
	
	  
	  /** '''NOTE TO STUDENTS: THIS METHOD IS ONLY INTENDED FOR IMPLEMENTATION IN CHAPTER 9.4!''' 
	    *
	    * Returns a mapping from parties to their candidates. That is, returns a `Map` whose keys 
	    * are the names of all the parties that have candidates in this district. For each key, 
	    * the value is a vector containing the candidates from that party in arbitrary order.
	    *
	    * @see [[rankingsWithinParties]] */
	  def candidatesByParty: Map[String, Vector[Candidate]] = this.candidates.groupBy( _.party)
	  
	
	  /** '''NOTE TO STUDENTS: THIS METHOD IS ONLY INTENDED FOR IMPLEMENTATION IN CHAPTER 9.4!''' 
	    *
	    * Returns a mapping from parties to their top candidates. That is, returns a `Map` whose 
	    * keys are the names of all the parties that have candidates in this district. For each 
	    * key, the value is the candidate from that party with the most votes.
	    *
	    * If multiple candidates from a single party received the same number of votes, this method 
	    * chooses one of them arbitrarily. */
	  def topCandidatesByParty = {
	    this.candidates.maxBy(_.votes)
	    
	  }
	  
	  /** '''NOTE TO STUDENTS: THIS METHOD IS ONLY INTENDED FOR IMPLEMENTATION IN CHAPTER 9.4!''' 
	    *
	    * Returns a mapping from parties to their vote totals. That is, returns a `Map` whose keys 
	    * are the names of all the parties that have candidates in this district. For each key, the 
	    * value is the number of votes received in total by all the members of that party in this district. */
	  def votesByParty = Map[String, Int]() // this is a non-working dummy implementation; this method will be implemented later
	
	
	  /** '''NOTE TO STUDENTS: THIS METHOD IS ONLY INTENDED FOR IMPLEMENTATION IN CHAPTER 9.4!''' 
	    *
	    * Returns a mapping from parties to ranking lists of those parties' candidates. That is, 
	    * returns a `Map` whose keys are the names of all the parties that have candidates in this 
	    * district. For each key, the value is a vector containing the candidates from that party 
	    * ''in order by the number of votes they received, starting with the highest''.
	    *
	    * If multiple candidates from a single party received the same number of votes, this method 
	    * orders them in an arbitrary way. */
	  def rankingsWithinParties = Map[String, Vector[Candidate]]() // this is a non-working dummy implementation; this method will be implemented later 
	
	  
	  /** '''NOTE TO STUDENTS: THIS METHOD IS ONLY INTENDED FOR IMPLEMENTATION IN CHAPTER 9.4!''' 
	    *
	    * Returns a vector containing the names of all the parties that have candidates in this 
	    * district, ordered by the total number of votes received by each party. Descending order 
	    * is used, so the party with the most votes comes first.
	    *
	    * If multiple parties received the same number of votes, this method orders them in an arbitrary way. */
	  def rankingOfParties = Vector[String]() // this is a non-working dummy implementation; this method will be implemented later
	  
	  
	  /** '''NOTE TO STUDENTS: THIS METHOD IS ONLY INTENDED FOR IMPLEMENTATION IN CHAPTER 9.4!''' 
	    *
	    * Returns a mapping of candidates to their distribution figures (Finnish: ''vertailuluku'').
	    * That is, returns a `Map` whose keys are all the candidates in this district and whose 
	    * values are the distribution figures of those candidates.
	    *
	    * The distribution figure of a candidate is obtained as follows. Take the position (rank) of 
	    * the candidate in the ranking list within his or her own party (as defined by 
	    * `rankingsWithinParties`). For instance, the most-voted-for candidate within a party has a 
	    * rank of 1, the second-most-voted-for has a rank of two, and so on. Divide the total number 
	    * of votes received by the candidate's party by the candidate's rank, and you have the 
	    * candidate's distribution figure.
	    *
	    * If multiple candidates from a single party received the same number of votes, the arbitrary 
	    * order chosen by `rankingsWithinParties` is used here, despite the fact that this is likely 
	    * to be a undesirable feature in a real-world system.  
	    *
	    * @see [[votesByParty]]
	    * @see [[rankingsWithinParties]]
	    * @see [[electedCandidates]] */
	  def distributionFigures = Map[Candidate, Double]() // this is a non-working dummy implementation; this method will be implemented later
	
	
	  /** '''NOTE TO STUDENTS: THIS METHOD IS ONLY INTENDED FOR IMPLEMENTATION IN CHAPTER 9.4!''' 
	    *
	    * Returns all the candidates who will be elected on the basis of the vote. The seats available 
	    * are given to the candidates with the highest distribution figures.
	    *
	    * If multiple candidates happen to have the same distribution figure, an arbitrary order is used, 
	    * despite the fact that this is likely to be a undesirable feature in a real-world system.  
	    *
	    * @return the elected candidates in descending order by distribution figure
	    * @see [[distributionFigures]] */
	  def electedCandidates = Vector[Candidate]() // this is a non-working dummy implementation; this method will be implemented later
	  
	  
	  
	}