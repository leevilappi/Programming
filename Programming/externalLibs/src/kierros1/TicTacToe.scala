
package kierros1

import processing.core._
import helper.TicTacToeHelper._

object TicTacToe extends PApplet  {

  var boxSize = 80 //in pixels
  var sideLength = 3 //3 boxes in each direction
  var gridSize = 9 //For 3x3 grid 
  
  
  val tokenCross: PImage = loadImage("X.png")
  val xStr: String = "X"
  
  val tokenCircle: PImage = loadImage("O.png")
  val oStr: String = "O"
  
  var turnToken = tokenCircle

  var gameOn: Boolean = true
  
  val grid = Array.fill(sideLength, sideLength)("")
  
  /**
   * The initialization method for the game.
   */
  override def setup() : Unit = { }
    size(250,250)

  override def draw() : Unit = { }
    background(204);
    stroke(153);
    drawGrid(TicTacToe, sideLength, boxSize);
    redrawImages(grid, tokenCircle, oStr, tokenCross, xStr,gridSize, TicTacToe);
    if (!gameOn == true) noLoop()

  /**
   * When mouse is clicked the location is counted and if the place on the board is empty player's sign is placed there.
   * If the player won, he is notified and the game ends. At the end the turn is changed.
   */
  override def mouseClicked() : Unit = { 
      
    var xCoord = mouseX
    var yCoord = mouseY

    if (gameOn == true ){
      image(turnToken, mouseX, mouseY) 
    }
      
    
    if (didGameEnd(grid, getTurn()) == true){
      if (getTurn == "X") {
        textSize(32);
        text("X-WINS!", 10, 30); 
      } else if (getTurn == "O"){
        textSize(32);
        text("O-WINS!", 10, 30); 
      }
      
      gameOn = false 
    }
   }
    
  /**
   * Returns the turn as a String.
   */
  private def getTurn() : String = {
    if (turnToken == tokenCircle){
      xStr   
    } else {
      oStr
    }
   }

  /**
   * Changes turn from O to X, and vice versa.
   */
  private def changeTurn() : Unit = { 
    if (turnToken == tokenCircle) {
      turnToken = tokenCross
    } else {
      turnToken = tokenCircle
    }
  }

  /**
   * The main method that makes your application run and show.
   */
  def main(args: Array[String]) {
    val frame = new javax.swing.JFrame("TicTacToe")

    frame.getContentPane().add(this)
    init
    frame.setSize(this.getSize())
    frame.pack
    frame.setVisible(true)
    frame.setLocationRelativeTo(null)
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE)
  }

}
