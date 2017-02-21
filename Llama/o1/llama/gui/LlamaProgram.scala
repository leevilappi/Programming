package o1.llama.gui

import scala.swing._
import scala.swing.event._
import o1.llama.Llama
import javax.swing.ImageIcon
import javax.swing.UIManager

// THIS PROGRAM IS EXPLAINED IN DETAIL IN CHAPTER 11.2 IN THE COURSE MATERIALS. 

object LlamaApp extends SimpleSwingApplication {

  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName)
  // A couple of images:
  
  val alivePic = new ImageIcon("alive.jpg")
  val deadPic = new ImageIcon("dead.jpg")
  
  // Access to the internal logic of the application: 
  
  var targetLlama = new Llama 

  // Components: 
  
  val commentary = new Label
  val pictureLabel = new Label
  val startOverButton = new Button("Uudestaan!")
  this.updateLlamaView()

  // Events: 
  
  this.listenTo(startOverButton)
  this.listenTo(pictureLabel.mouse.clicks)
  this.listenTo(pictureLabel.mouse.wheel)

  this.reactions += {
    case wheelEvent: MouseWheelMoved => 
      targetLlama.scratch()
      updateLlamaView()
    
    case clickEvent: MouseClicked =>
      if (clickEvent.clicks > 1) {  // double-click (or triple, etc.)
        targetLlama.slap()
      } else { 
        targetLlama.poke()
      }
      updateLlamaView()

    case clickEvent: ButtonClicked => 
      targetLlama = new Llama
      updateLlamaView()
  }

  private def updateLlamaView() = {
    this.commentary.text = this.targetLlama.stateOfMind
    this.pictureLabel.icon = if (this.targetLlama.isOutOfPatience) this.deadPic else this.alivePic
  }

  
  // Layout: 
  
  val verticalPanel = new BoxPanel(Orientation.Vertical)
  verticalPanel.contents += commentary
  verticalPanel.contents += pictureLabel
  verticalPanel.contents += startOverButton
  
  val llamaWindow = new MainFrame
  llamaWindow.title = "Llama Plenty Fun"
  llamaWindow.resizable = false
  llamaWindow.contents = verticalPanel

  def top = this.llamaWindow
  
  
}

