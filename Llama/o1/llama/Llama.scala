package o1.llama

import scala.collection.immutable.SortedMap

////////////////// NOTE TO STUDENTS //////////////////////////
// Feel free to examine this file, but for the purposes of 
// this course,  it's enough to know what's said about it in 
// Chapter 11.2.    
//////////////////////////////////////////////////////////////


/** A `Llama` is a mutable object that has a reducing amount of patience left. 
  * Strangely enough, it knows how to speak Finnish. */
class Llama {
    
  private val comments = SortedMap(0 -> "Au.", 100 -> "Varo, räjähdän kohta!", 250 -> "Nyt alkaa sappi kiehua.", 
                                   500 -> "Soitan kohta poliisin.", 750 -> "Lopeta heti!", 900 -> "Olet aika ärsyttävä.")
  private val originalPatience = 1000
  private var remainingPatience = originalPatience;   // stepper

  
  /** Returns a comment that indicates what the llama thinks of the world and the program user. */
  def stateOfMind = this.comments.find( this.remainingPatience <= _._1 ).map( _._2 ).getOrElse("On ihanaa olla laama")

  
  /** Greatly reduces the llama's patience. */
  def slap() = {
    this.reducePatience(200)
  }


  /** Substantially reduces the llama's patience. */
  def poke() = {
    this.reducePatience(50)
  }

  
  /** Reduces the llama's patience somewhat. */
  def scratch() = {
    this.reducePatience(5)
  }

  
  /** Reduces the llama's patience by the given amount. */
  private def reducePatience(howMuch: Int) = {
    this.remainingPatience = 0.max(this.remainingPatience - howMuch)
  }
  
  
  /** Returns the patience level of the llama as a percentage between 0 and 1. */
  def patienceLevel = this.remainingPatience.toDouble / this.originalPatience

  
  /** Determines whether the llama's patience has entirely run out. */
  def isOutOfPatience = this.remainingPatience == 0

  
  /** Returns a short textual description of the llama. */
  override def toString = "@" + 100 * this.patienceLevel + "%: " + this.stateOfMind
  
  
}  
