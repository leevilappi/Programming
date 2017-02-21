package o1.robots.tribal

import o1.robots._
import o1.grid._

import scala.collection.mutable.Map
import scala.collection.mutable.Stack


/** The class `TribalBot` represents the "brains" (or AI) of robots that belong to 
  * a "robot tribe" and behave according to the rules of that tribe. Tribal robots 
  * consider other tribal robots of the same tribe their friends and all other robots 
  * their enemies. A tribal robot is capable of attacking enemies and thereby converting 
  * them to their own tribe. As a consequence, tribes can be quite aggressive as they 
  * compete for survival in a robot world. 
  *
  *
  * ==Robot Actions==
  *
  * Each tribal robot is capable of doing one (but only one) of the following 
  * things during its turn:
  *
  *  - Moving one square forward into an empty floor space. 
  *  - Spinning 90 degrees (without moving). 
  *  - Attacking -- "hacking" -- an enemy robot immediately in front of it (see below). 
  *
  * Hacking is automatic whenever a tribal robot sees an enemy, but how a tribal robot 
  * moves about and spins depends on its tribe. Each tribe has its own program, written 
  * in the RoboSpeak language. All members of the tribe follow this program, which 
  * defines the sequences of actions that those robots take. See the documentation of 
  * class [[Tribe]] for details about RoboSpeak.
  *
  *
  * ==Hacking==
  *
  * A tribal robot starts its turn by looking at whether there is an enemy robot in front 
  *  of it. An enemy robot is any robot that is not a member of the same tribe. If there 
  * is an enemy robot in the square right in front of the acting tribal bot, then the 
  * tribal bot attacks it by "hacking". Hacking is a kind of brainwashing: it converts 
  * the enemy robot into a member of the acting robot's tribe. The victim will begin 
  * executing its new tribe's RoboSpeak program from a line determined by the hacking 
  * robot.
  *
  *
  * ==Memory==
  *
  * Each tribal bot has some limited memory resources available, which it draws on when 
  * it executes the tribe's RoboSpeak program. A tribal bot remembers the following 
  * information:
  *
  *  - Where it is: A tribal bot knows which robot body it is controlling, just like any  
  *    other `RobotBrain` does, and can therefore determine its location and facing.
  *
  *  - Where its allegiances lie: A tribal bot knows which tribe it belongs to 
  *
  *  - What it's supposed to do next: A tribal bot knows which instruction in its tribe's 
  *    RoboSpeak program it is supposed to execute next once it gets its next turn. 
  *    (This allows the tribal bot to save the program position it is in when it ends a turn. 
  *    It can later resume program execution where it left off the previous turn.)
  *
  *  - Which way it's turning: A tribal bot remembers if it is supposed to be turning 
  *    clockwise or counterclockwise when it next makes a turn. 
  *
  *  - Which subprogram calls have been made: A tribal bot has a call stack where it stores 
  *    frames representing calls to RoboSpeak subprograms. Each such frame is represented by 
  *    a [[Frame]] object. This allows the tribal bot to execute subprograms and return to 
  *    the correct location in the tribe's RoboSpeak code when the end of a subprogram is reached.
  *
  *  - The contents of three memory slots (or "registers") called ''mem'', ''radar'' and 
  *    ''hackline''. The slot ''mem'' can be used by the RoboSpeak program for any purpose. 
  *    The intended use of ''radar'' is to store the readings produced by the robot's radar. 
  *    The slot ''hackline'' is meant for configuring hacked robots. See the text below and 
  *    class [[Tribe]] for more details.
  *
  *
  * ==Sensors==
  *
  * A tribal bot is (only) capable of seeing the single square directly in front of it. 
  * It can determine what there is in that square but no further. It also has a ''radar'' that 
  * can be used either ''short-range'' to determine how many enemies or friends are located 
  * within exactly two steps of the acting robot, or ''long-range'' to count the memberships
  * of entire tribes in the robot world. Again, more details can be found in the documentation 
  * for class [[Tribe]].
  *
  *
  * ==Initial state==
  *
  * When a tribal bot is created, it starts executing its tribal program from the 
  * beginning (unless otherwise specified by a hacking robot). Its call stack is initially empty,
  * its memory slots all contain the value 1, and it is considered to be turning clockwise.
  *
  * As a new tribal bot brain "plugs into" a robot body, it changes the robot's name. 
  * Each new member of a tribe receives a new rather impersonal name of the form "Tribe#number", 
  * e.g., "Tiger#123". The ID number that comes after the hash (#) is unique for every member
  * of a tribe. 
  *
  *
  * @param body   the robot body that the tribal bot brain will control
  * @param tribe  the tribe that this brain belongs to
  *
  * @see [[Tribe]]
  * @see [[Frame]] */
class TribalBot(body: RobotBody, val tribe: Tribe) extends RobotBrain(tribe.newName(), body) {

  // NOTE TO STUDENTS: The underscores (_) below mean that these variables will initially be
  // assigned (unimportant) default values -- for the moment, anyway: they will be assigned 
  // values by the boot method that is called immediately below.
  private var nextInstruction: Instruction = _                    // serves as a "program counter register" for the robot
  private var turningClockwise: Boolean = _                       // flag
  private var memorySlots = Map[String,Int]().withDefaultValue(1) // container of most-recent holders (Int-valued memory slots or "registers")
  private var callStack = Stack[Frame]()
  this.boot(1)

  
  /** Resets the robot's state. All the robot's memory slots are set to the initial 
    * value of 1. When it next gets a turn, the robot starts executing its tribal program 
    * from the given line onwards with an empty call stack.    
    * @param startLine  a line number in the robot's tribe's RoboSpeak program; the robot will
    *                   execute the instruction on this line next */
  def boot(startLine: Int) = {
    this.nextInstruction = this.tribe.instructionAt(startLine)
    this.turningClockwise = true
    this.memorySlots.clear() 
  }
  
  
  /** A tribal robot prioritizes hacking: if there is an enemy present directly in front,
    * the acting bot spends its turn hacking it. (Exception: there are "pacifist" tribes 
    * that don't hack.) 
    *
    * If no enemy is present, or if the tribal bot is a pacifist, the bot executes its 
    * tribe's RoboSpeak program until it performs one of the actions that end its turn
    * (or at least tries to perform such an action).  
    *
    * Essentially, this method is responsible for the tribal bot's equivalent of a 
    * [[http://en.wikipedia.org/wiki/Instruction_cycle fetch-execute cycle]]
    * during the robot's turns. In the case of a tribal robot, this involves these two steps:
    *
    *   1. Execute an instruction.
    *
    *   2. Fetch the instruction that is to be executed next. 
    *
    * The cycle is repeated until the robot's turn is over. The turn is over when the robot 
    * executes one of the instructions that ends its turn or when the end of the tribe's
    * program is reached.  
    *
    * Instructions are represented by `Instruction` objects. The tribal bot object's job is 
    * made easy by the fact that the method `execute` in class [[Instruction]] not only executes 
    * the instruction (Step 1 above), but also returns the instruction that is to be 
    * executed next (Step 2 above).  
    *
    * Unless otherwise specified, a robot will resume execution where it left off the 
    * previous turn.
    *
    * This method assumes that it is called only if the robot is not broken and not stuck.
    *
    * @see [[Instruction]] */
  def moveBody() = {
    if(this.seesEnemy && !this.tribe.isPacifist){
      this.hack()
    }else {
      while(!this.nextInstruction.endsTurn){
        nextInstruction = this.nextInstruction.execute(this)
      }
      nextInstruction = this.nextInstruction.execute(this)
    }
  }
   
  /** Returns the contents of a named memory slot. */
  def slotValue(name: String) = this.memorySlots(name)
  
  
  /** Sets the contents of a named memory slot. The new value replaces the old value stored in the slot */
  def setSlotValue(name: String, value: Int) = {
    this.memorySlots += name -> value
  }
  
  
  /** Switches the spinning direction of the tribal bot. If it was previously set 
    * to turn clockwise, it now turns counterclockwise, and vice versa.
    * This method does not actually turn the robot, it only affects future spins.
    * @see [[spin]] */
  def switchDirection() = {
    this.turningClockwise = !this.turningClockwise
  }
  
  
  /** Spins the robot 90 degrees in whichever direction it is currently turning.
    * @see [[switchDirection]] */
  def spin() = {
    val currentFacing = this.body.facing
    val newFacing = if (this.turningClockwise) currentFacing.clockwise else currentFacing.counterClockwise
    this.body.spinTowards(newFacing)
  }

  
  /** Determines whether this robot is a member of the given tribe. */
  def belongsTo(tribe: Tribe) = this.tribe == tribe
  
  
  /** Determines the tribe of a robot. Returns the tribe of the given robot  
    * wrapped in an `Option`, or `None` if the robot is not a tribal bot. */
  def determineTribe(another: RobotBrain) = { 
    // NOTE TO STUDENTS: The following construct has not been covered in the course materials.
    // It is not necessary for the purposes of the present course that you understand it.
    // Briefly put, the "match" keyword is used here to choose an alternative based on 
    // dynamic type of the object referenced by "another". 
    // (There are other things that you can do with "match" in Scala as well.)
    another match {
      case otherTribal: TribalBot => Some(otherTribal.tribe)
      case _ => None
    }
  }

  
  /** Determines whether this robot considers the given robot to be "fodder", that is, an 
    * unthreatening resource. A tribal bot never sees itself as fodder, but does see all 
    * other pacifist robots (including ones from its own tribe if it's a pacifist) as fodder. 
    * All non-tribal bots are considered fodder by all tribal bots. */
  def seesAsFodder(robot: RobotBrain) = determineTribe(robot).forall( _.isPacifist ) && this != robot
  
  
  /** Determines whether this robot considers the given robot tribe a threat. 
    * A tribal bot considers all non-pacifist tribes threats except its own. */
  def isThreatenedBy(tribe: Tribe)= !this.belongsTo(tribe) && !tribe.isPacifist

  
  /** Determines whether this robot considers the given robot to be a threat.
    * A tribal bot considers all members of all non-pacifist tribes except its 
    * own to be threats. Non-tribal bots are never considered to be threats. */
  def isThreatenedBy(robot: RobotBrain): Boolean = determineTribe(robot).exists( this.isThreatenedBy(_) )

  
  /** Determines if the given robot is a friend of this robot. That is, determines if the 
    * given robot is a tribal robot of the same tribe (but is not this robot itself). */
  def isFriend(robot: RobotBrain) = this.determineTribe(robot).exists( this.belongsTo(_) ) && this != robot 
  
    
  /** Returns the brains of the friendly robot immediately in front of this robot,
    * if there is one. The brains are returned in an `Option`; `None` is returned 
    * if there is no robot in the square or if the robot that is there is not a friend.
    * @see [[isFriend]] */
  // NOTE TO STUDENTS: It's not necessary for the purposes of this course that you understand this method implementation.  
  def friendInFront: Option[TribalBot] = this.robotInFront.collect{ case tribal: TribalBot => tribal }.filter( this.isFriend(_) )  
  
  
  /** Returns the brains of the enemy robot immediately in front of this robot, if there is one. 
    * Any robot that is not a friend is an enemy. The brains are returned in an `Option`; `None` 
    * is returned if there is no robot in the square or if the robot that is there is a friend.
    * @see [[isFriend]] */
  def enemyInFront = this.robotInFront.filter( !this.isFriend(_) )

  
  /** Determines whether the square immediately in front of the robot is an unpassable obstacle. */
  def seesWall = this.squareInFront.isUnpassable


  /** Determines whether the square immediately in front of the robot is an empty floor space. */
  def seesFloor = this.squareInFront.isEmpty
  

  /** Determines whether the square immediately in front of the robot contains an enemy robot. 
    * @see [[enemyInFront]] */
  def seesEnemy = this.enemyInFront.isDefined

  
  /** Determines whether the square immediately in front of the robot contains an friendly robot.
    * @see [[friendInFront]] */
  def seesFriend = this.friendInFront.isDefined


  /** "Talks" to a friendly robot that is currently right in front of the acting robot, assuming 
    * there is a friendly robot there. In practice, what this means is that the talking robot 
    * gives an order to the "listener", causing the listener to move to the given line number 
    * in the two robots' shared tribal program. The listener is "rebooted" and starts from
    * the given line number with a clean slate, as if it had just been created. 
    *
    * If there is no friendly robot in front of the acting robot, this method does nothing.
    *
    * @param newLine  a line number referencing the RoboSpeak program of the two friends' shared tribe
    * @see [[isFriend]] */
  def talk(lineNumber: Int) = {
    this.friendInFront.foreach(_.boot(lineNumber))
    this.friendInFront.foreach(_.callStack.clear()) 
  }


  
  /** "Brainwashes" an enemy robot that is currently right in front of the acting robot, 
    * assuming there is one there. The victim of the hack will receive a new tribal bot brain
    * of the enemy tribe, which replaces its old brain. Consequently, it will receive a new 
    * name and will start behaving differently than before.
    *
    * When its next turn comes, the victim will start executing the tribal RoboSpeak program 
    * of the attacker's tribe, using its fresh brain. Anything that the victim's old brain had 
    * in memory (stack frames, memory slot values, etc.) is lost; the victim is now considered 
    * to be turning clockwise.
    *
    * The victim will start executing its new RoboSpeak program from the line indicated by the 
    * hacking robot's ''hackline'' memory slot. The default value for this memory slot is 1, 
    * so unless the hacking robot's tribal program specifies otherwise, the victim starts 
    * from the first line of RoboSpeak. (See the description of RoboSpeak in class [[Tribe]] 
    * for more details about how to affect ''hackline''.) 
    *
    * If there is no enemy in front of the acting robot, this method does nothing.
    *
    * @see [[enemyInFront]] */
  def hack() = {
    if(this.enemyInFront.isDefined){
      var hackline = this.slotValue("hackline")
      var enemy = this.robotInFront.get.body
      enemy.brain = Some(new TribalBot(enemy, tribe))
      this.friendInFront.foreach(_.boot(hackline))
      
     }
  }
  

  
  /** Starts the execution of a subprogram. That is to say, creates a new stack frame and places 
    * ("pushes") it on top of the robot's call stack.
    * @param callingLine  A line number indicating where the subprogram call was made. This number is 
    *                     to be stored in the new stack frame so that program execution can resume from 
    *                     the correct location once the robot returns from the subprogram. */
  def callSubprogram(callingLine: Int):Stack[Frame]  = {
    callStack.push(new Frame(callingLine))
  }
  

  /** Ends the execution of a subprogram. This means that the topmost stack frame is removed 
    * ("popped") from the robot's call stack.
    * @return a line number indicating where the subprogram call (whose execution ended) had been 
    *         called from (and where execution should therefore resume)  */
  def returnFromSubprogram(): Int = {
   callStack.pop().returnLine
  }


  /** Returns the brains of all the robots in the same robot world with this one. */
  def longRadar =  this.world.robotList.flatMap(_.brain)



  
  /** Returns the brains of all the robots within two steps of this one.
    * (See the documentation of class [[Tribe]] for a definition of "steps".) */
  def shortRadar = {
    def distance(loc1: Coords, loc2: Coords) = (loc1.x - loc2.x).abs + (loc1.y - loc2.y).abs
    def isNear(robot: RobotBrain) = distance(this.location, robot.location) <= 2
    this.longRadar.filter( isNear(_) )
  }

  
}

