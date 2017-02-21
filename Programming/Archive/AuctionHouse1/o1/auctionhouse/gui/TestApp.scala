
////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file. 
//////////////////////////////////////////////////////////////

package o1.auctionhouse.gui

import scala.swing._
import scala.util._
import scala.swing.event._
import javax.swing.UIManager
import o1.auctionhouse._
import FlowPanel.Alignment
import BorderPanel.Position
import o1.gui.Dialog._
import o1.assignments._
import GridBagPanel.Anchor._
import GridBagPanel._
import java.util.Locale
import scala.util.Try
import java.lang.reflect.InvocationTargetException


/** The singleton object `TestApp` represents a simple application that a programmer can use 
  * to experiment with the main functionality of the classes from package `o1.auctionhouse`. 
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this object works 
  * or can be used, apart from the fact that you can use this file to start the program.''' 
  */
object TestApp extends SimpleSwingApplication {

  // This class is a bit convoluted as a result of jumping through some hoops in order to allow 
  // it to work unmodified whether or not all auction types have been implemented.
  
  UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName) 

  def top = new MainFrame {

    title = """A Simple UI for Playing Around with an "Auction House""""
    location = new Point(100, 100)

    val itemTabs = new TabbedPane
    
    val buttonRow = new FlowPanel(FlowPanel.Alignment.Center)()
    val buttonPairings = Seq[(AbstractButton, ItemType)](new Button("Add a fixed-price item")       -> FPS, 
                                                         new Button("Add a Dutch-style auction")    -> DA,
                                                         new Button("Add an English-style auction") -> EA) 
    val buttons = buttonPairings.toMap

    {
      val buttonSeq = buttonPairings.map( _._1 )
      buttonSeq.foreach( buttonRow.contents += _ )
      listenTo(buttonSeq:_*)
    }
    
    listenTo(itemTabs.selection)
    reactions += { 
      case ButtonClicked(source) =>
        withStudentSolution(buttonRow) { 
          buttons(source).requestInstanceInPanel().foreach(this.addTab)
        }
      case SelectionChanged(_) => 
        defaultButton = itemTabs.selection.page.content.asInstanceOf[ItemPanel].defaultButton
    }

    Try(this.addTab(FPS.panelForParams("Example item", 10000, 3)))  
    Try(this.addTab(DA.panelForParams("Dutch auction example", 100000, 1000, 70000)))
    Try(this.addTab(EA.panelForParams("English auction example", 50000, 10))) 
    
    contents = new BorderPanel {
      layout(buttonRow) = Position.North
      layout(itemTabs) = Position.Center
    }
    
    this.resizable = false
    this.pack()

    
    def addTab(panel: ItemPanel) = {
      val itemCount = this.itemTabs.pages.size
      this.itemTabs.pages += new TabbedPane.Page("Item #" + (itemCount + 1), panel, panel.tooltipText)
      this.itemTabs.selection.index = itemCount
      panel.requestFocus()
      this.pack()
    }

    
    class ItemWrapper(actual: AnyRef) extends DynamicObject(actual) { 
      def saleType = this.actual.getClass.getSimpleName        
    }    
    
    abstract class ItemPanel(actualItem: AnyRef) extends GridBagPanel {
      val item = new ItemWrapper(actualItem)
      val tooltipText = item.description[String]

      def defaultButton: Button 
      
      val open, expired, buyer = new Label
      val title = new Label(s"<html><b>${item.description[String]}</b> (${item.saleType})</html>")
      val advanceButton = new Button("Advance one day") {
        listenTo(this)
        reactions += {
          case ButtonClicked(_) => withStudentSolution(buttonRow) {
            item.advanceOneDay[Unit]() 
            refresh()
          }
        }
      }
      val customerSelector = new ComboBox(Seq("Melchior", "Caspar", "Balthazar"))
      // customerSelector.makeEditable()
      val buyAction = new FlowPanel(Alignment.Left)(new Label("Select customer: "), customerSelector)
      val advanceAction = new FlowPanel(Alignment.Left)(advanceButton)

      layout += title         -> new Constraints(0, 0, 3, 1, 3, 0, NorthWest.id, Fill.None.id, new Insets(20, 20, 8, 8), 0, 0) // later: switch to Table or something
      layout += open          -> new Constraints(0, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  20, 0, 0), 0, 0)
      layout += expired       -> new Constraints(1, 1, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  0,  0, 0), 0, 0)
      layout += buyer         -> new Constraints(0, 4, 3, 1, 3, 0, NorthWest.id, Fill.None.id, new Insets(0,  20, 0, 0), 0, 0)
      layout += buyAction     -> new Constraints(0, 5, 3, 1, 3, 0, NorthWest.id, Fill.None.id, new Insets(5,  20, 0, 0), 0, 0)
      layout += advanceAction -> new Constraints(0, 6, 3, 1, 3, 0, NorthWest.id, Fill.None.id, new Insets(5,  15, 0, 0), 0, 0)
      
      def refresh() = {
        this.open.text    = s"<html>Open: ${if (this.item.isOpen[Boolean]) "yes" else "<font color=\"red\">no</font>"}</html>"
        this.buyer.text   = s"<html>Buyer: ${this.item.buyer[Option[String]].map( name => s"""<font color="green">$name</font>""" ).getOrElse("(none)")}</html>"
        this.expired.text = s"<html>Expired: ${if (this.item.isExpired[Boolean]) "<font color=\"red\">yes</font>" else "no"}</html>"
      }
        
    }
    
    
    private abstract class IPPanel(actualItem: AnyRef) extends ItemPanel(actualItem) {
      val buyButton = new Button("Buy") {
        listenTo(this)
        reactions += {
          case ButtonClicked(_) => 
            withStudentSolution(buttonRow) {
              val result = item.buy[Boolean](classOf[String] -> arg(customerSelector.selection.item))
              if (!result) {
                display("Failed to buy the item.", Some(this))
              }
              refresh()
            }
        }
      }
      buyAction.contents += buyButton
      
      def defaultButton = this.buyButton 
    }
      
      
    private sealed abstract class ItemType(className: String, constructorParameters: Class[_]*) extends DynamicClass[AnyRef](className, constructorParameters) with RequestArguments[AnyRef] {

      def requestInstanceInPanel(): Option[ItemPanel] = {
        this.requestInstance().map(this.panelForInstance)
      }
       
      protected def descriptionParam() = {
        requestNonEmptyLine("Item description: ", "Please enter a reasonable integer value.", Some(buttonRow))
      }

      protected def intParam(prompt: String) = {
        requestInt(prompt + ": ", _ >= 0, "Please enter a non-negative number.", Some(buttonRow)).map( new Integer(_) )
      }

      def panelForInstance(actualItem: AnyRef): ItemPanel
      
      def panelForParams(itemParameters: Any*) = {
        this.panelForInstance(this.instantiate(itemParameters.map(arg): _*))
      }
      
    }
    
    
    private object FPS extends ItemType("o1.auctionhouse.FixedPriceSale", classOf[String], classOf[Int], classOf[Int]) {
      val argumentRequesters = Seq( () => descriptionParam(), () => intParam("Price (in cents)"), () => intParam("Duration (in days)"))
      
      def panelForInstance(actualItem: AnyRef) = { 
        new IPPanel(actualItem) {
          val daysRemaining = new Label
          val price = new Label("Price: %.2f €".formatLocal(Locale.US, this.item.price[Int] / 100.0))
          refresh()
          layout += price         -> new Constraints(0, 2, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  20, 0, 0), 0, 0) 
          layout += daysRemaining -> new Constraints(1, 2, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  0,  0, 0), 0, 0)
            
          override def refresh() = {
            super.refresh()
            this.daysRemaining.text = s"Days remaining: ${this.item.daysLeft[Int]}"
          }
        }
      }
    }

    private object DA extends ItemType("o1.auctionhouse.DutchAuction", classOf[String], classOf[Int], classOf[Int], classOf[Int]) {
      val argumentRequesters = Seq( () => descriptionParam(), () => intParam("Starting price (in cents)"), () => intParam("Decrement (in cents)"), () => intParam("Minimum price (in cents)") )
        
      def panelForInstance(actualItem: AnyRef) = {
        new IPPanel(actualItem) {
          val priceRatio, currentPrice = new Label
          val startingPrice = new Label("Starting price: %.2f €".formatLocal(Locale.US, this.item.startingPrice[Int] / 100.0))
          val minimumPrice  = new Label( "Minimum price: %.2f €".formatLocal(Locale.US, this.item.minimumPrice[Int]  / 100.0))
          refresh()
          layout += startingPrice -> new Constraints(0, 2, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  20, 0, 0), 0, 0)  
          layout += minimumPrice  -> new Constraints(1, 2, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  0,  0, 0), 0, 0)
          layout += currentPrice  -> new Constraints(0, 3, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  20, 0, 0), 0, 0)  
          layout += priceRatio    -> new Constraints(1, 3, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  0,  0, 0), 0, 0)
          
          override def refresh() = {
            super.refresh()
            this.currentPrice.text = "Price now: %.2f €".formatLocal(Locale.US, this.item.price[Int] / 100.0)
            this.priceRatio.text   = "Price/starting: %.2f".formatLocal(Locale.US, this.item.priceRatio[Double])
          }
        }
      }
    }

    private object EA extends ItemType("o1.auctionhouse.EnglishAuction", classOf[String], classOf[Int], classOf[Int]) {
      val argumentRequesters = Seq( () => descriptionParam(), () => intParam("Starting price (in cents)"), () => intParam("Duration (in days)") )
        
      def panelForInstance(actualItem: AnyRef) = {
        new ItemPanel(actualItem) {
          val requiredBid, currentPrice, daysRemaining = new Label
          val startingPrice = new Label("Starting price: %.2f €".formatLocal(Locale.US, this.item.startingPrice[Int] / 100.0))
          val amountField = new TextField((this.item.price[Int] * 1.1).toInt.toString)
          amountField.columns = 8
          val bidButton = new Button("Place bid") {
            listenTo(this)
            reactions += { 
              case ButtonClicked(_) =>
                withStudentSolution(buttonRow) {
                  val result = item.bid[Boolean](classOf[String] -> arg(customerSelector.selection.item), 
                                                 classOf[Int]    -> arg(Try(amountField.text.toInt).getOrElse(0)))
                  if (!result) {
                    display("Didn't become the highest bidder.", Some(this))
                  }
                  refresh()
                }
            }
          }
          buyAction.contents += new Label("Amount (in cents): ") += amountField += bidButton
          refresh() 
          layout += startingPrice -> new Constraints(0, 2, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  20, 0, 0), 0, 0)  
          layout += daysRemaining -> new Constraints(1, 2, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  0,  0, 0), 0, 0)
          layout += currentPrice  -> new Constraints(0, 3, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  20, 0, 0), 0, 0)  
          layout += requiredBid   -> new Constraints(1, 3, 1, 1, 1, 0, NorthWest.id, Fill.None.id, new Insets(0,  0,  0, 0), 0, 0)
            
          override def refresh() = {
            super.refresh()
            this.currentPrice.text =   "Price now: %.2f €".formatLocal(Locale.US, this.item.price[Int] / 100.0)
            this.requiredBid.text =    "Bid at least: %.2f €".formatLocal(Locale.US, this.item.requiredBid[Int] / 100.0)
            this.daysRemaining.text = s"Days remaining: ${this.item.daysLeft[Int]}"
          }
          
          def defaultButton = this.bidButton
          
          override def requestFocus() = {
            this.amountField.requestFocus()
          }
          
        }
      }
    }

  }

}
