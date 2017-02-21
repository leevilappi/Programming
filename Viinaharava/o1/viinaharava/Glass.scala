package o1.viinaharava

import o1.grid.Coords
import scala.collection.mutable.Buffer

/** The class `Glass` represents glasses in a game of Viinaharava. A glass object 
  * is mutable: it is initially full of water but can be modified to contain booze 
  * instead and can be drunk to empty its contents.
  * @param board     the game board that the glass is located on
  * @param location  the location of the glass on the game board */
class Glass(val board: GameBoard, val location: Coords) {
  
  private var isFull = true
  private var isWater = true     
  private var danger = 0    

  
  /** Determines whether the glass is empty. */
  def isEmpty = !this.isFull

  
  /** Determines whether the glass is a glass of booze. A glass counts as a booze class 
    * even if the booze has already been drunk. */
  def isBooze = !this.isWater 
  
  
  /** Returns the number of neigboring booze glasses (diagonals included).
    *
    * Note to students: This method only returns the danger level of the glass. 
    * It does NOT change the level; that is the job of method [[pourBooze]]. */
  def dangerLevel = this.danger
  
  
  /** Returns this glass's neighboring glasses (diagonals included) on the game board. */
  def neighbors: Vector[Glass] = this.board.neighbors(location, true)
  
  /** If the glass is a water glass, fills it with booze instead. This raises the danger 
    * levels of neighboring glasses. If the glass was a booze glass to begin with, this 
    * method does nothing.
    * @see [[dangerLevel]] */
  def pourBooze() = {
    if(!this.isBooze || !this.isFull){
      this.isWater = false
      this.isFull = true
      for(item <- this.neighbors){
        item.danger += 1
      }
    }
  }
  
  
  /** Virtually drinks the contents of the glass and returns a value indicating
    * if it contained water or not. 
    *
    * In case of a glass of water, "drinking" it just means that the glass is emptied. 
    * (If the water glass was already empty, this method does nothing but return `true`.)
    *
    * In case of a glass of booze, "drinking" it not only sets the glass to be empty but also
    * empties all the other glasses of booze on the board. (This ends the game
    * of Viinaharava and all the locations of booze glasses are revealed as a result.) 
    *
    * @return `true` if the glass was a water glass, `false` if it was a booze glass */
  def drink(): Boolean = {
	    if (this.isWater) {
	      this.isFull = false
	      if(this.dangerLevel == 0){
	        this.neighbors.map { x => if(x.isFull == true) x.drink() }
	      }   
	      true
	    } else {
	      for (booze <- this.board.boozeGlasses) {
	        booze.isFull = false
	      }
	      false
	    }
	  }
   
  
}



