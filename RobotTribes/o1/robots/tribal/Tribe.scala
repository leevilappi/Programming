package o1.robots.tribal


import scala.io.Source
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import o1.robots.RobotBrain
import java.io.IOException
import scala.util.Random
import Tribe._


////////////////// NOTE TO STUDENTS //////////////////////////
// Much of what is in this file goes well beyond what this
// course covers. You'll need to implement one method into
// this class, however.
//////////////////////////////////////////////////////////////

/** A "tribe" of robots is essentially a program which makes any robots who follow that program 
  * --- the "members" of the tribe --- to behave in a certain way.
  *
  * '''NOTE TO STUDENTS: In this course, you don't need to understand every aspect of this relatively
  * complex class. However, you should read the text below so you know how to use the RoboSpeak language. 
  * You'll also need to modify the source code of this class a bit, as described in Chapter 12.1 
  * of the course materials.'''
  *
  *
  * ==RoboSpeak==
  *
  * As a Plan A, tribal robots spend their turn attacking nearby enemies (see class [[TribalBot]]).
  * However, when there is no enemy directly in front, the tribal bot will instead follow its
  * tribe's program to maneuver itself.   
  *
  * Tribal programs are written in a programming language called '''RoboSpeak'''. RoboSpeak can be 
  * described as "machine code for tribal robots". It features a smallish number of simple instructions 
  * (commands) which can be combined to build programs that direct tribal robots' actions.
  * A description of RoboSpeak appears below. 
  *
  *
  * ==Action Instructions==
  *
  * There are three "action instructions" that cause a tribal bot to spend its turn on something concrete:
  *
  *  - ''move'': Attempt to move forward one step into an empty square. 
  *  - ''spin'': Spin 90 degrees. Unless otherwise specified (see below), the robot spins clockwise.
  *  - ''wait'': Stand still: don't move, don't spin.  
  *
  * Even if a robot fails to execute an action instruction (this happens if there is something 
  * blocking movement), the attempt ends the robot's turn.
  *
  *
  * ==An Introductory Example==
  *
  * By themselves, action instructions are not enough to write interesting RoboSpeak programs.
  * To control the use of action instructions, RoboSpeak provides a variety of "logic instructions" 
  * which allow a tribal bot to reason about its state and surroundings. 
  *
  * Here is a simple RoboSpeak program. It causes the members of a tribe to move forward as long 
  * as they can, turning clockwise whenever they run into a wall.
  *
  * {{{
  * ifwall 4
  * move
  * goto 1
  * spin
  * goto 1
  * }}}
  *
  * This program can be paraphrased as: "If you see a wall in front of you, go to line 4 (where you spin clockwise 
  * before returning to line 1). Otherwise, move one step forward and return to line 1." Either moving or spinning 
  * ends the robot's turn. Next turn, it resumes where it left off. In this example, it so happens that the 
  * robot will always start its turns by returning to line 1 (because both the ''move'' and ''spin''
  * commands are followed by ''goto 1'').
  *
  * Only the two action instructions listed above end a tribal bot's turn: a robot can execute any 
  * number of non-action instructions during a turn. 
  *
  *
  * ==Basic Logic==
  *
  * Some basic logic instructions are:
  *
  *  - ''goto N'': Continue executing the program from line N.
  *
  *  - ''switch'': Change the direction in which the robot spins when the ''spin'' instruction is executed.
  *                If the robot was previously spinning clockwise, it now spins counterclockwise.
  *                If it was spinning counterclockwise, it now spins clockwise. Note that switching 
  *                only affects future spins; it does not actually spin the robot immediately.
  *
  *  - ''ifempty N'': If there is nothing in the square in front of you, continue executing the program 
  *                from line N, otherwise execution from the next line. The command ''ifnempty N'' does 
  *                exactly the opposite, and jumps to line N if and only if the square is not empty.
  *
  *  - ''iffriend N'': If there is a friend (a robot of the same tribe) in the square in front of you, 
  *                continue executing the program from line N, otherwise continue execution from the next line. 
  *                The command ''ifnfriend N'' does exactly the opposite, and jumps to line N if and only 
  *                if there is no friend present.
  *
  *  - ''ifwall N'': If there is a wall in the square in front of you, continue executing the program from 
  *                line N, otherwise continue execution from the next line. The command ''ifnwall N'' does 
  *                exactly the opposite, and jumps to line N if and only if there is no wall present.
  *
  *  - ''ifrandom N'': "Flip a coin": 50% chance of continuing from line N, 50% chance of continuing from 
  *                the next line.
  *
  *
  * ==Labels==
  *
  * Since using line number literals is highly inconvenient when editing any program longer than a 
  * dozen lines or so, RoboSpeak allows the programmer to define '''labels'''. Labels are essentially 
  * names for program locations. They can be used instead of line numbers in RoboSpeak instructions. 
  *
  * A label is defined by line that contains a single word (the name of the label)
  * followed by a colon. Here is the previous RoboSpeak program rewritten using labels:
  *
  * {{{
  * start:
  *   ifwall wallfound
  *   move
  *   goto start
  * wallfound:
  *   spin
  *   goto start
  * }}}
  *
  * A label definition itself does not instruct a tribal bot to do anything. 
  * It is simply a name for a location in code.
  *
  * Anywhere a line number is required as a parameter to a RoboSpeak instruction, a label name 
  * may be used instead. 
  *
  *
  * ==Comments and Whitespace==
  *
  * In RoboSpeak, the hash character (#) means that the rest of the line is a comment and does not 
  * have any effect on robot behavior. Empty lines are likewise ignored. Using whitespace for 
  * indentation is encouraged. 
  *
  * Here is a slightly more complex tribe whose code has been explained using comments.
  *
  * {{{
  * #########################################################
  * # A member of the Tiger Tribe moves straight forward,   #      
  * # hacking anything it sees in front of it. When its     #
  * # route is blocked, it turns either left or right at    #
  * # random.                                               #
  * #########################################################
  *
  * start:                        # Main loop: turn or move
  *   ifnempty turn               # turn if facing an obstacle
  *   move                        # move otherwise
  *   goto start                  # repeat the above 
  *
  * turn:                         # Turns left or right at random 
  *   ifrandom noswitch          
  *   switch
  * noswitch:
  *   spin
  *   goto start 
  * }}}
  *
  *
  * ==Pacifist Tribes==
  *
  * If a RoboSpeak program contains a line that consists of the word ''pacifist'', then members of the 
  * tribe will not try to hack anyone at the beginning of their turns (the bunny tribe is an example).
  *
  *
  * ==Using Memory Slots==
  *
  * Each tribal bot has three '''memory slots''' called ''mem'', ''radar'' and ''hackline'', each of 
  * which can store an integer value. Some commands directly manipulate these slots: 
  *
  *  - ''set S N'': Set the value of slot S to N. The parameter S must be a slot name. For instance,
  *    ''set mem 5'' stores the value five in ''mem'', replacing any previously stored value. 
  *
  *  - A slot name can be used as a parameter value. For instance, ''goto mem'' causes execution to 
  *    jump to the line indicated by the current value of ''mem'', and ''set mem radar'' copies the 
  *    value of ''radar'' into ''mem''.
  *
  * In addition to the logic instructions listed above, RoboSpeak features a few commands that compare 
  * numerical values. These work best in combination with memory slots: 
  *
  *  - ''ifeq A B N'': If A equals B then continue executing the program from line N, otherwise
  *                    continue execution from the next line. For instance, ''ifeq mem 100 22'' jumps
  *                    to line 22 if the memory slot ''mem'' contains the value 100. 
  *                    The command ''ifneq A B N'' does exactly the opposite, and jumps to N if and 
  *                    only if A and B are not equal.
  *
  *  - ''ifgt A B N'': If A is greater than B then continue executing the program from line N, otherwise
  *                    continue execution from the next line.
  *
  *  - ''iflt A B N'': If A is less than B then continue executing the program from line N, otherwise
  *                    continue execution from the next line. 
  *
  * The slot ''mem'' may be used for anything. The slots ''radar'' and ''hackline'' have 
  * special purposes --- see below.
  *
  *
  * ==Radar Commands==
  *
  * The ''radar'' memory slot is a special one that is automatically assigned a new value whenever
  * the robot uses any of the available radar commands: 
  *
  *  - ''enemiesnear'': Use the radar to find out how many non-pacifist enemy bots are 
  *                     within two steps of the acting robot. (See diagram below.) Stores this 
  *                     number in ''radar''.
  *
  *  - ''friendsnear'': Use the radar to find out how many friendly robots are within 
  *                     two steps of the acting robot. (See diagram below.) Stores this number 
  *                     in ''radar''. The robot does not count itself as a friend. 
  *
  *  - ''foddernear'': Use the radar to find out how many pacifist bots are within 
  *                    two steps of the acting robot. (See diagram below.) Stores this number 
  *                    in ''radar''. This count does not include the acting robot, even if
  *                    it is a pacifist one. Non-tribal bots count as pacifists for this purpose.
  *
  *  - ''fodderleft'': Use the radar to produce a count of how many "fodder bots"
  *                    (see ''foddernear'' above) there still are in the entire robot world. 
  *                    Stores this number in ''radar''.
  *
  *  - ''score'': Use the radar to produce a number that indicates who is leading the 
  *                    ongoing tribal fight and by how much. Stores this number in ''radar''. 
  *                    A positive number means that the acting robot's tribe is more populous than 
  *                    any other tribe in the robot world, while a negative number means that another 
  *                    tribe is leading. The magnitude of the lead is also given: for instance, a 
  *                    score of 5 means that the acting robot's tribe has five more members than 
  *                    the second-placed one, and -10 means that the acting robot's tribe has ten 
  *                    fewer members than the leading tribe. Pacifist tribes are ignored when
  *                    determining the score.
  *
  * A "step" means a non-diagonal move to an adjacent square. For instance, in the diagram below, robots 
  * A, B, C, D, and E are within two steps of robot R, but robots F and G aren't. (# represents a wall, 
  * and . represents an empty floor square.)
  *
  * {{{
  * ###########
  * #..A......#
  * #.D#RBC...#
  * #.....G...#
  * #.F.E.....#
  * ###########
  * }}}
  *
  *
  * ==Hacking and Talking==
  *
  * As noted above, non-pacifist tribal bots always automatically spend their turn hacking a nearby 
  * enemy whenever possible. When this happens, the enemy robot starts executing its tribal program from
  * whichever line number is stored in the hacking robot's ''hackline'' memory slot. As the default value of 
  * all memory slots is 1, hacked robots start running their programs from the beginning, unless a ''set'' 
  * command has been used to modify the value of ''hackline''.
  *
  * Robots can also affect how their existing tribemates behave. The command ''talk N'' transmits a 
  * command to a nearby friendly robot. Assuming that there is a friendly robot in front of the acting 
  * robot, the friend jumps to line N in its RoboSpeak program. The next time the friend gets a turn, 
  * it will resume executing its program from line N onwards. If there is no friendly robot right in front 
  * of the acting robot, this instruction achieves nothing. Talking does not end a robot's turn. 
  *
  * A robot that is hacked or talked to is rebooted. Its state (spinning direction, memory slots, etc.) 
  * are reset.
  *
  *
  * ==Subprograms==
  *
  * RoboSpeak provides a mechanism for making subprogram calls. Subprogram calls are similar to function 
  * calls in Scala in that they cause a specified sequence of instructions to be executed, after 
  * which execution resumes at the location where the call was made. However, they are much simpler 
  * than function calls in that RoboSpeak subprograms can not receive any parameters, don't produce 
  * return values, and are never invoked on objects. (RoboSpeak is not an object-oriented language.)
  *
  * Two RoboSpeak instructions are directly related to subprogram calls:  
  *
  *  - ''callsub N'': Calls a subprogram that starts at line N, causing program execution to jump to 
  *             that line. Although this is similar to ''goto N'', there is a crucial difference. 
  *             While the ''goto'' command simply moves program execution from one place to another, 
  *             ''callsub'' causes the robot to remember that it is making a subprogram call.
  *             More specifically, the robot will remember that there is a location at which 
  *             the subprogram was called and where program execution is supposed to resume once the 
  *             end of the subprogram's code has been reached.
  *
  *  - ''return'': Marks the end of a subprogram's code. When this instruction is executed the robot 
  *             returns to where the subprogram was called, and resumes program execution at the 
  *             following RoboSpeak instruction. 
  *
  * Here is an example of a tribe that makes use of subprogram calls. Notice that just like Scala
  * functions can call other functions, subprograms can also call other subprograms. 
  *
  * {{{
  * # A member of the Patrolman Tribe moves clockwise in rectangles, 
  * # hacking every enemy it encounters. When it runs into obstacles, 
  * # it takes a look around counterclockwise before proceeding.  
  *
  * start:
  *   callsub advance     # repeat three times: try to advance 
  *   callsub advance
  *   callsub advance
  *   spin                # turn clockwise
  *   goto start
  *
  * advance:              # move unless obstacle in front
  *   ifnempty cantmove   
  *   move
  *   return
  * cantmove:             # spin around counterclockwise
  *   switch
  *   spin
  *   spin
  *   spin
  *   switch
  *   return
  * }}}
  *
  * @param name      the name of the tribe
  * @param fileName  the path to a file containing the RoboSpeak program that defines the tribe's behavior */
class Tribe(val name: String, fileName: String) {

  private var nextMemberID: Long = 0          // stepper: 0, 1, 2, ...

  var isPacifist = false  
  private val instructions = Buffer[Instruction]()  
  private val labels = Map[String, Instruction]()       
  this.readProgram(fileName) // initializes instructions, labels and isPacifist based on the contents of the RoboSpeak file 

  
  /** Reads a given RoboSpeak program file and parses the instructions and labels
    * contained therein by calling parseLine on each line of the program. */
  private def readProgram(fileName: String) = {
    def cleanLine(text: String) = text.takeWhile(_!='#').toLowerCase().trim()
    val source = scala.io.Source.fromFile(fileName).getLines()

    source.foreach(x => parseLine(cleanLine(x)))

  }

  
  /** Given a line of a RoboSpeak program, adds the information represented by the line to this tribe object. */
  private def parseLine(line: String) = {
    val parsedLine = RoboSpeakGrammar.parse(line)
    this.instructions += parsedLine.instruction
    for (label <- parsedLine.label) {
      this.labels += label -> parsedLine.instruction
    }
    if (parsedLine.directive.exists( _ == "pacifist")) {
      this.isPacifist = true
    }
  }
    
  
  /** Returns a new, unused name for a new member of this tribe. This name consists
    * of the tribe's name and a unique ID number for the new member. */
  def newName() = {
    this.nextMemberID += 1
    this.name(0).toUpper + this.name.substring(1) + "#" + this.nextMemberID    
  }

  
  /** Returns the instruction at the given line number. If there is no such line in the program, 
    * an instruction is returned that terminates the RoboSpeak program when executed. */
  def instructionAt(lineNumber: Int) =
    if (lineNumber <= 0 || lineNumber > this.instructions.size)
      new TerminateProgram(lineNumber) 
    else 
      this.instructions(lineNumber - 1)

      
  /** Returns the line number where the given label appears in this tribe's
    * RoboSpeak program; `None` if there is no such label. */
  private def label(name: String) = this.labels.get(name).map( _.lineNumber )
  
  
  /** The description is simply the tribe's name.*/
  override def toString = this.name

    
    
  import scala.util.parsing.combinator.JavaTokenParsers

  type Param = Either[String, Int]  // parameter of a RoboSpeak Instruction

  object RoboSpeakGrammar extends JavaTokenParsers {
  
    case class Line(val instruction: Instruction, val label: Option[String], val directive: Option[String])    
      
    def parse(input: String) = 
      this.parseAll(this.line, input) match {
        case Success  (parsedCode, _) => parsedCode
        case NoSuccess(problem,    _) => throw new TribeFileException(s"Invalid RoboSpeak. Message from parser: $problem")
      }

    def line = instruction | labelDef | directive | empty   
    
    // Instructions:
    def instruction = (
      move        | spin        | waitInstr   | switch     | 
      talk        | ifempty     | ifnempty    | iffriend   | 
      ifnfriend   | ifwall      | ifnwall     | ifrandom   | 
      goto        | callsub     | returnInstr | ifeq       | 
      ifneq       | ifgt        | iflt        | set        | 
      enemiesnear | friendsnear | foddernear  | fodderleft | 
      score )                                    ^^ { Line(_, None, None) }
    def        move = "move"                     ^^ ( _ => new Move ) 
    def        spin = "spin"                     ^^ ( _ => new Spin ) 
    def   waitInstr = "wait"                     ^^ ( _ => new Wait )  
    def      switch = "switch"                   ^^ ( _ => new Switch )
    def        talk = "talk"~>param              ^^ { case line => new Talk(line) } 
    def     ifempty = "ifempty"~>param           ^^ { case line => new IfEmpty(line) }  
    def    ifnempty = "ifnempty"~>param          ^^ { case line => new IfNEmpty(line) }  
    def    iffriend = "iffriend"~>param          ^^ { case line => new IfFriend(line) }  
    def   ifnfriend = "ifnfriend"~>param         ^^ { case line => new IfNFriend(line) }  
    def      ifwall = "ifwall"~>param            ^^ { case line => new IfWall(line) }  
    def     ifnwall = "ifnwall"~>param           ^^ { case line => new IfNWall(line) }  
    def    ifrandom = "ifrandom"~>param          ^^ { case line => new IfRandom(line) } 
    def        goto = "goto"~>param              ^^ { case line => new Goto(line) } 
    def        ifeq = "ifeq"~>param~param~param  ^^ { case n1~n2~line => new IfEq(n1, n2, line) } 
    def       ifneq = "ifneq"~>param~param~param ^^ { case n1~n2~line => new IfNEq(n1, n2, line) } 
    def        ifgt = "ifgt"~>param~param~param  ^^ { case n1~n2~line => new IfGT(n1, n2, line) } 
    def        iflt = "iflt"~>param~param~param  ^^ { case n1~n2~line => new IfLT(n1, n2, line) } 
    def     callsub = "callsub"~>param           ^^ { case line => new CallSub(line) } 
    def returnInstr = "return"                   ^^ ( _ => new Return )  
    def         set = "set"~>slotParam~param     ^^ { case slot~value => new Set(slot.left.get, value) } 
    def enemiesnear = "enemiesnear"              ^^ ( _ => new EnemiesNear ) 
    def friendsnear = "friendsnear"              ^^ ( _ => new FriendsNear ) 
    def  foddernear = "foddernear"               ^^ ( _ => new FodderNear ) 
    def  fodderleft = "fodderleft"               ^^ ( _ => new FodderLeft ) 
    def       score = "score"                    ^^ ( _ => new Score ) 

    // Parameters of instructions:
    def      param = intParam | slotParam | labelParam
    def  slotParam = Tribe.Slots.mkString("|").r ^^ ( Left(_) ) 
    def labelParam = raw"[\S]+".r                ^^ ( Left(_) )
    def   intParam = wholeNumber                 ^^ ( numString => Right(numString.toInt) )
    
    // Labels, empty lines, and directives
    def       empty = ""                         ^^ ( _ => Line(new NoCommand, None, None) )
    def    labelDef = raw"[\S]+:".r              ^^ ( labelName => Line(new LabelDefinition, Some(labelName.init), None) )
    def   directive = "pacifist"                 ^^ ( directiveName => Line(new Directive, None, Some(directiveName)) )

    
  }
    
  trait EndsTurn extends Instruction {
    def endsTurn = true
  }     
    
  trait ContinuesTurn extends Instruction {
    def endsTurn = false
  }     

  class TerminateProgram(val lineNumber: Int) extends Instruction with EndsTurn { 
    def execute(actor: TribalBot) = {
      this
    }  
  }
    
  abstract class GenericInstruction extends Instruction {
    
    def followingInstruction = Tribe.this.instructionAt(this.lineNumber + 1)
    
    def lineNumber = {
      val index = Tribe.this.instructions.indexOf(this) // tweak for huge programs?
      assert(index >= 0, "Instruction not found within its own program.")
      index + 1
    }
      
    def eval(actor: TribalBot, parameter: Param) = 
      parameter match {
        case Right(integer) => integer  
        case Left(word) => 
          if (Tribe.Slots.contains(word))
            actor.slotValue(word)  
          else // a label
            Tribe.this.label(word).getOrElse(throw new TribeFileException("\"" + word + "\" is not a valid line number, label, or slot name."))
      }

    def findInstruction(actor: TribalBot, parameter: Param) = Tribe.this.instructionAt(this.eval(actor, parameter))
      
    override def toString = this.getClass.getName

    def execute(actor: TribalBot) = {
      this.act(actor)
      this.next(actor)
    }
      
    def act(actor: TribalBot)
      
    def next(actor: TribalBot): Instruction
  }
   

  trait ToNextLine extends GenericInstruction {
    def next(actor: TribalBot) = this.followingInstruction
  }    
    
    
  abstract class SimpleInstruction(val doAction: TribalBot => Unit) extends GenericInstruction {
    def act(actor: TribalBot) = {
      this.doAction(actor)
    }
  }
   
  abstract class JumpInstruction extends SimpleInstruction( _ => { } ) with ContinuesTurn 
   
  abstract class ActionInstruction(act: TribalBot => Unit) extends SimpleInstruction(act) 
      
  abstract class BranchInstruction(val branchLine: Param) extends JumpInstruction {
    def shouldBranch(actor: TribalBot): Boolean
    def next(actor: TribalBot) = if (this.shouldBranch(actor)) this.findInstruction(actor, this.branchLine) else this.followingInstruction 
  }
  
  abstract class SimpleCheck(branchLine: Param, val checkCondition: TribalBot => Boolean) extends BranchInstruction(branchLine) {
    def shouldBranch(actor: TribalBot) = this.checkCondition(actor)
  }
      
  abstract class Comparison(val number: Param, val number2: Param, branchLine: Param, val compare: (Int, Int) => Boolean) extends BranchInstruction(branchLine) {
    def shouldBranch(actor: TribalBot) = this.compare(this.eval(actor, number), this.eval(actor, number2))
  }

  abstract class LoadInstruction(val targetSlot: String) extends GenericInstruction with ToNextLine with ContinuesTurn {
    def act(actor: TribalBot) = {
      actor.setSlotValue(this.targetSlot, this.newSlotValue(actor))
    }
    def newSlotValue(actor: TribalBot): Int
  }
      
  abstract class RadarCount(val shortRange: Boolean, val filter: (TribalBot, RobotBrain) => Boolean) extends LoadInstruction(Tribe.RadarSlot) {
    def newSlotValue(actor: TribalBot) = {
      val robotsInRange = if (this.shortRange) actor.shortRadar else actor.longRadar
      robotsInRange.count( this.filter(actor, _) )
    }
  }
  
  class Move extends SimpleInstruction( _.moveCarefully() ) with ToNextLine with EndsTurn
  class Spin extends SimpleInstruction( _.spin() )          with ToNextLine with EndsTurn
  class Wait extends SimpleInstruction( _ => { } )          with ToNextLine with EndsTurn
  
  abstract class Pass extends JumpInstruction with ToNextLine  
  class LabelDefinition extends Pass 
  class NoCommand       extends Pass 
  class Directive       extends Pass 
  
  class Switch extends SimpleInstruction( _.switchDirection() ) with ToNextLine with ContinuesTurn
  
  class Talk(val line: Param) extends GenericInstruction with ToNextLine with ContinuesTurn {
    def act(actor: TribalBot) = {
      actor.talk(this.eval(actor, this.line))
    }
  }
  
  class IfEmpty(branchLine: Param)   extends SimpleCheck(branchLine, _.seesFloor)
  class IfNEmpty(branchLine: Param)  extends SimpleCheck(branchLine, !_.seesFloor)
  class IfFriend(branchLine: Param)  extends SimpleCheck(branchLine, _.seesFriend)
  class IfNFriend(branchLine: Param) extends SimpleCheck(branchLine, !_.seesFriend)
  class IfWall(branchLine: Param)    extends SimpleCheck(branchLine, _.seesWall)
  class IfNWall(branchLine: Param)   extends SimpleCheck(branchLine, !_.seesWall)
  class IfRandom(branchLine: Param)  extends SimpleCheck(branchLine, _ => Random.nextBoolean())
  class Goto(branchLine: Param)      extends SimpleCheck(branchLine, _ => true)
  
  class IfEq (number: Param, number2: Param, branchLine: Param) extends Comparison(number, number2, branchLine, _ == _ )
  class IfNEq(number: Param, number2: Param, branchLine: Param) extends Comparison(number, number2, branchLine, _ != _ )
  class IfGT (number: Param, number2: Param, branchLine: Param) extends Comparison(number, number2, branchLine, _ > _ )
  class IfLT (number: Param, number2: Param, branchLine: Param) extends Comparison(number, number2, branchLine, _ < _ )
  
  class CallSub(val sub: Param) extends GenericInstruction with ContinuesTurn {
    def act(actor: TribalBot) = {
      actor.callSubprogram(this.lineNumber)
    }
    def next(actor: TribalBot) = this.findInstruction(actor, this.sub)
  }
  
  class Return extends JumpInstruction {
    def next(actor: TribalBot) = {
      Tribe.this.instructionAt(actor.returnFromSubprogram() + 1)
    }
  }
  
  class Set(targetSlot: String, val newValue: Param) extends LoadInstruction(targetSlot) {
    def newSlotValue(actor: TribalBot) = this.eval(actor, this.newValue)
  } 

  class EnemiesNear extends RadarCount(true,  _.isThreatenedBy(_) )  
  class FriendsNear extends RadarCount(true,  _.isFriend(_)       )
  class FodderNear  extends RadarCount(true,  _.seesAsFodder(_)   )
  class FodderLeft  extends RadarCount(false, _.seesAsFodder(_)   )
      
  class Score extends LoadInstruction(Tribe.RadarSlot) {
    def newSlotValue(actor: TribalBot) = {
      val tribesPerRobot = for {
        currentBot   <- actor.longRadar
        currentTribe <- actor.determineTribe(currentBot)
        if (!currentTribe.isPacifist)
      } yield currentTribe
      val tribeCounts = tribesPerRobot.groupBy(identity).mapValues( _.size )
      val myCount = tribeCounts.getOrElse(actor.tribe, 0)
      val competitors = (tribeCounts - actor.tribe)
      if (competitors.isEmpty) myCount else myCount - competitors.values.max 
    }
  }
  
}


/** This is the companion object of class `Tribe`. 
  * '''NOTE TO STUDENTS: In this course, you don't need to understand how this object works or can be used.'''
  * @see the class [[Tribe]] */
object Tribe {

  /** This exception type represents situations where a robot tribe file is either  
    * unreadable or does not contain a valid RoboSpeak program. */
  class TribeFileException(message: String) extends RuntimeException(message)  

  /** the name of the memory slot that's used by tribal bots' radar */
  val RadarSlot = "radar"  
  /** the name of the memory slot that's used by tribal bots' hacking action */
  val HackSlot = "hackline"  
  /** the names of all the tribal bots' memory slots */
  val Slots = Vector(HackSlot, "mem", RadarSlot)  
  
}


