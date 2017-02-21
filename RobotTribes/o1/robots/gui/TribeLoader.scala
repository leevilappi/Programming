package o1.robots.gui

import o1.robots.tribal._
import java.io.File
import java.io.FileFilter
import o1.gui.Dialog._
import scala.util._
import scala.collection.immutable.SortedMap
import scala.util.control.NonFatal
import o1.util.localURL

////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it's not necessary    
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////


/** The singleton object `TribeLoader` is capable of checking what robot
  * tribes are available in a particular folder, and loading the ones that are. 
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this object 
  * works or can be used.''' */
object TribeLoader {

  val Suffix = ".tribe"

  val All = loadTribes()
  val BunnyTribe = All.values.flatten.find( _.name == "bunny" )
  
  type TribeMap = SortedMap[File, Option[Tribe]]
  
  private def loadTribes(): TribeMap = {
    this.tribesDir.map( loadTribes(_) ).getOrElse(SortedMap())
  }
  
  private def tribesDir = {
    val name = "tribes/"
    val folderUnderWorkingDir = new File(name)
    if (folderUnderWorkingDir.exists) {
      Some(folderUnderWorkingDir)
    } else {
      val folderUnderClassDir = localURL(name).map( loc => new File(loc.getPath) )
      folderUnderClassDir.filter( _.exists )
    }
  }
  
  private def loadTribes(dir: File): TribeMap = {
    val tribeFiles = dir.listFiles(new FileFilter {
      def accept(candidate: File) = candidate.getName.endsWith(Suffix) && candidate.getName.length > Suffix.length
    })
    Sorting.quickSort(tribeFiles)(Ordering.by( -_.lastModified ))
    val nameTribePairs = for (file <- tribeFiles) yield file -> this.readTribe(file)
    SortedMap(nameTribePairs:_*)(Ordering.by( tribeFiles.indexOf(_) ))
  }
  
  
  private def readTribe(file: File): Option[Tribe] = {
    Try(new Tribe(file.getName.dropRight(Suffix.length), file.getPath)) match { 
      case Failure(roboSpeakProblem: Tribe.TribeFileException) =>
        display("Problem in the RoboSpeak file \"" + file.getName + "\":\n" + roboSpeakProblem, Centered)
        System.err.println(roboSpeakProblem)
        None
      case Failure(NonFatal(otherProblem)) =>
        System.err.println(otherProblem)
        throw otherProblem
      case Success(tribe) => 
        Some(tribe)
    }
  }

  
}

