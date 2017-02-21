package o1.viinaharava

import o1.grid._
import scala.util.Random


/** Each instance of the class `GameBoard` represents a board for a game of Viinaharava.
  * A game board is a `Grid` whose elements are `Glass` objects. 
  *
  * Some of these glasses --- in random locations --- will be initialized to contain booze; 
  * the rest keep their default content of water. The glasses are created and filled
  * as part of creating a `GameBoard`. 
  *
  * @param width       the number of glasses in each row of the game board
  * @param height      the number of rows of glasses in the game board
  * @param boozeCount  the number of booze glasses on the game board. When a `GameBoard` object 
  *                    is created, it randomly chooses this many different locations on the board
  *                    and pours booze in them.
  *
  * @see [[o1.grid.Grid]] 
  * @see [[Glass.pourBooze]] */
class GameBoard(width: Int, height: Int, boozeCount: Int) extends Grid[Glass](width, height) {

  this.placeGlasses(boozeCount)   
  
  private def placeGlasses(boozeCount: Int) = {
  
    for(xCoord <- 0 until this.width){
      for(yCoord <- 0 until this.height){
        this.update(new Coords(xCoord, yCoord), new Glass(this, new Coords(xCoord, yCoord)))
      }
    }
    
    val rng = new Random
    var counter = boozeCount
    
    while(counter > 0){
      val x = rng.nextInt(this.width)
      val y = rng.nextInt(this.height)
      
      val boozeLoc = new Coords(x,y)
      
      if(this.apply(boozeLoc).isBooze == false ){
        this.apply(boozeLoc).pourBooze()
        counter -= 1
      }else {
        
        val x = rng.nextInt(this.width)
        val y = rng.nextInt(this.height)
      }
      
      
      
    }
    
//    val topLeft = new Coords(0, 0)                  // This is just an example of how to place a single glass...       
//    this.update(topLeft, new Glass(this, topLeft))  // ... in the top left corner. Replace this with your own code.
//    // TODO: Replace the two lines above with code that properly initializes the game board; see Chapter 7.2 
  }
  

  
  
  // Note to students: You don't need to (and shouldn't) modify the following methods.
  
  /** Returns a collection of all the booze glasses currently on the board. */
  def boozeGlasses = this.allElements.filter( _.isBooze ).toVector
  

  /** Determines whether all the water on the board has been drunk already. */ 
  def isOutOfWater = this.allElements.forall( glass => glass.isBooze || glass.isEmpty )

  
  
  
}

