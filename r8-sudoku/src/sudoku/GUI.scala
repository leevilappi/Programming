
/* 
 * Author: Tommi Junttila
 */

package sudoku
import scala.swing._
import scala.swing.event._
//import scala.swing.RichWindow.Undecorated
import GridBagPanel._
import javax.swing.border._
import java.awt.Color

/**
 * A very simple sudoku solver GUI.
 */
class GUI(val g: Grid) extends SimpleSwingApplication {
  var currentGrid = g
  val n = g.n
  val m = g.subDim
  // Background colors of the grid 
  val subGridColors = Vector(new Color(230, 230, 255), new Color(255, 255, 255), new Color(230, 255, 230))

  val preferredCellSize = 35

  /* A helper function */
  protected def cellAsString(r: Int, c: Int): String = {
    val v = currentGrid(r, c)
    if (v == Grid.unknown) "" else v.toString
  }
  /* Another one: allows the editor work even though Grid.isConsistent is not implemented */
  def isConsistent(grid: Grid) = {
    try { grid.isConsistent }
    catch {
      case e: NotImplementedError => true
    }
  }

  val buttonToCoord = new scala.collection.mutable.HashMap[AbstractButton, Pair[Int, Int]]()

  // The actual user interface
  lazy val ui = new GridPanel(m, m) {
    for (sr <- 0 until m; sc <- 0 until m) {
      val subGrid = new GridPanel(m, m)
      val subGridIndex = sr * m + sc
      subGrid.border = new MatteBorder(1, 1, 1, 1, Color.black)
      for (ssr <- 0 until m; ssc <- 0 until m) {
        val r = sr * m + ssr
        val c = sc * m + ssc
        val button = new Button(cellAsString(r, c))
        button.foreground = Color.red
        button.border = new MatteBorder(1, 1, 1, 1, Color.gray)
        button.background = subGridColors((sr + sc) % subGridColors.length)
        button.preferredSize = new Dimension(preferredCellSize, preferredCellSize)
        subGrid.contents += button
        buttonToCoord(button) = (r, c)
      }
      contents += subGrid
    }
    def updateGrid = {
      var sgi = 0
      for (sr <- 0 until m; sc <- 0 until m) {
        val subGrid = contents(sgi).asInstanceOf[GridPanel]
        sgi += 1
        val subGridIndex = sr * m + sc
        var bi = 0
        for (ssr <- 0 until m; ssc <- 0 until m) {
          val r = sr * m + ssr
          val c = sc * m + ssc
          val button = subGrid.contents(bi).asInstanceOf[Button]
          bi += 1
          button.text = cellAsString(r, c)
          button.foreground = if (g.defined(r, c)) Color.red else Color.black
        }
      }

    }
  }

  lazy val popup = new Frame {
    title = "Select"
    val buttonToVal = new scala.collection.mutable.HashMap[AbstractButton, Int]()
    var row = 0
    var column = 0
    val valuePanel = new GridPanel(m, m) {
      for (sr <- 0 until m; sc <- 0 until m) {
        val v = sr * m + sc + 1
        val button = new Button(v.toString)
        button.border = new MatteBorder(1, 1, 1, 1, Color.gray)
        button.background = Color.yellow
        button.preferredSize = new Dimension(preferredCellSize, preferredCellSize)
        contents += button
        buttonToVal(button) = v
      }
    }
    val resetButton = new Button("Reset")

    contents = new GridBagPanel() {
      val c = new Constraints
      c.fill = Fill.Horizontal
      c.gridx = 0
      c.gridy = 0
      layout(valuePanel) = c

      c.gridx = 0
      c.gridy = 1
      layout(resetButton) = c
    }
    reactions += {
      case ButtonClicked(resetButton) => {
        assert(!g.defined(row, column))
        this.visible = false
        val newGrid = currentGrid.updated(row, column, Grid.unknown)
        assert(isConsistent(newGrid))
        currentGrid = newGrid
        ui.updateGrid
      }
    }
    reactions += {
      case ButtonClicked(b) if b != resetButton => {
        assert(!g.defined(row, column))
        val v = buttonToVal(b)
        assert(1 <= v && v <= n)
        this.visible = false
        val newGrid = currentGrid.updated(row, column, v)
        assert(isConsistent(newGrid))
        currentGrid = newGrid
        ui.updateGrid
      }
    }
    listenTo(resetButton)

    def updateCands(r: Int, c: Int, cands: Set[Int]) = {
      row = r
      column = c
      for (i <- 0 until n) {
        val button = valuePanel.contents(i).asInstanceOf[Button]
        deafTo(button)
        if (cands.contains(i + 1)) {
          button.text = (i + 1).toString
          listenTo(button)
        } else {
          button.text = ""
        }
      }
    }
  }

  def launchPopup(r: Int, c: Int) = {
    require(!g.defined(r, c))
    if (popup.size == new Dimension(0, 0)) popup.pack()
    popup.centerOnScreen()
    popup.setLocationRelativeTo(ui)
    //popup.location = new Point((ui.location.getX() + ui.size.getWidth()/2).toInt, 100)
    val tempGrid = if (currentGrid.defined(r, c)) currentGrid.updated(r, c, Grid.unknown) else currentGrid
    val cands = (1 to n).filter(v => isConsistent(tempGrid.updated(r, c, v)))
    popup.updateCands(r, c, cands.toSet)
    popup.visible = true
  }

  def top = new MainFrame {
    title = "Sudoku editor"
    contents = ui
    reactions += {
      case ButtonClicked(b) => {
        val coord = buttonToCoord(b)
        if (!g.defined(coord._1, coord._2)) {
          launchPopup(coord._1, coord._2)
        }
      }
    }
    buttonToCoord.keys.foreach(b => listenTo(b))
  }

  def go() {
    val t = top
    if (t.size == new Dimension(0, 0)) t.pack()
    t.centerOnScreen()
    t.visible = true
  }

}

