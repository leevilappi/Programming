
////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file. 
//////////////////////////////////////////////////////////////

package o1.gui

import javax.swing.Icon
import javax.swing.ImageIcon



class ScaledPic(val filename: String, val width: Int, val height: Int) extends Icon {

  private val imageIcon = this.makeIcon()
  
  private def makeIcon() = {
    val originalImage = new ImageIcon(this.getClass.getResource("/" + filename)).getImage
    new ImageIcon(originalImage.getScaledInstance(this.width, this.height, java.awt.Image.SCALE_AREA_AVERAGING)) 
  }
  
  def paintIcon(target: java.awt.Component, graphics: java.awt.Graphics, height: Int, width: Int) = {
    this.imageIcon.paintIcon(target, graphics, height, width)
  }

  def getIconWidth = this.imageIcon.getIconWidth

  def getIconHeight = this.imageIcon.getIconHeight
  
  override def toString = this.filename + " scaled to " + this.width + " by " + this.height + " pixels" 
  
}

