package armlet
import java.io.{File,FileReader,BufferedReader,IOException}

object launchTicker {
  def main(args: Array[String]): Unit = {
    val reader = new BufferedReader(new FileReader(new File("assignment.s")))
    val lines = new StringBuilder()
    var line = reader.readLine()
    while (line != null) {
      lines ++= line + "\n"
      line = reader.readLine()
    }
    new Ticker(lines.mkString)
  }
}
