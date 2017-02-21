/*
 * 						 Nimi: Leevi Lappi
 * Opiskelijanumero: 526270
 * Sähköpostiosoite: leevi.lappi@aalto.fi
 */


package kierros2
import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn.readLine


object AudioEffects extends App {
  
  /*
   * Method removeVocals removes vocals from the soundtrack.
   * Vocals are removed by taking the sound data from the right channel 
   * and subtracting it from the left channel and dividing the result by two.
   */
  
  def removeVocals(sound: StereoSound) : StereoSound = {
    val soundData = ArrayBuffer[Int]()
            
    for(i <- 0 until sound.left.size){
      soundData += (sound.left(i) - sound.right(i)) / 2
    }
        
    new StereoSound("vocalsRemoved", soundData, soundData)
  }
  
  /*
   *  Method fadeIn creates an effect of increasing volume from the 
   *  beginning of the soundtrack. Effect is created by increasing sound sample
   *  value gradually from 0 to 1 by a multiplier determined by the length of wanted 
   *  fading in.
   */
  
  
  def fadeIn(sound: StereoSound, fadeLen: Int): StereoSound = {
    
    if(sound.length > fadeLen && fadeLen > 0){   
      
      val increment = 1.00 / fadeLen                        //calculate required increment
      var multiply: Double = 0                              //stepper
  
      val leftChannel = ArrayBuffer[Int]()
      val rightChannel = ArrayBuffer[Int]()
      
      for(i <- 0 until sound.left.size){
        leftChannel += (sound.left(i) * multiply).toInt       //multiply each sound sample by multiplier
        rightChannel += (sound.right(i) * multiply).toInt
        
        //Increase multiplier to raise sound volume gradually
        if (multiply + increment < 1){
          multiply += increment
        }else {
          multiply = 1
        }
      }
      
      
      new StereoSound("fadedInTrack", leftChannel, rightChannel)
      
    } else {
      println("Method fadeIn did not succeed...")
      new StereoSound("noFade", sound.left, sound.right) 
    }
    
  }
  
  /*
   *  Method fadeOut creates an effect of decreasing volume at the end of the
   *  soundtrack. Effect is created by decreasing sound sample value gradually 
   *  from 1 to 0 by a multiplier determined by the length of wanted 
   *  fading in.
   */
  
  
  def fadeOut(sound: StereoSound, fadeLen: Int)={
    
    if(sound.length > fadeLen && fadeLen > 0){   
      
      val increment = 1.00 / fadeLen
      var multiply: Double = 0
  
      var leftChannel = ArrayBuffer[Int]()
      var rightChannel = ArrayBuffer[Int]()
      
      for(i <- sound.left.size-1 to 0 by -1 ){                    //Begin from the last sound sample and end to first
        leftChannel += (sound.left(i) * multiply).toInt
        rightChannel += (sound.right(i) * multiply).toInt
        
        //Increase multiplier to raise sound volume gradually
        if (multiply + increment < 1){
          multiply += increment
        }else {
          multiply = 1
        }
      }
      
      leftChannel = leftChannel.reverse                          //Reverse sound sample order so it is correct
      rightChannel = rightChannel.reverse
      
      new StereoSound("fadedOutTrack", leftChannel, rightChannel)
      
    } else {
      println("Method fadeOut did not succeed...")
      new StereoSound("noFade", sound.left, sound.right) 
    }
    
  }
  
  /*
   *  Method fade combines methods fadeIn and fadeOut, so that both the 
   *  beginning and the end of the soundtrack are faded. Detailed descriptions of 
   *  methods are found in fadeIn and fadeOut.
   */
  
  
  def fade(sound: StereoSound, fadeLen: Int)={
    
    if(sound.length > fadeLen && fadeLen > 0){
      //First apply method fadeIn
      var fadedInSample = fadeIn(sound, fadeLen)
      //Then apply method fadeOut
      fadeOut(fadedInSample, fadeLen)
       
    } else {
      println("Method fadeOut did not succeed...")
      new StereoSound("noFade", sound.left, sound.right) 
    
    }
      
  }
  
  /*
   * Method panLeftToRight creates an effect of panning so that the direction of the 
   * sound is changed. In this method soundtrack is panned from left to right. Effect
   * is created by fading in the right channel and fading out the left channel.
   */
  
  def panLeftToRight(sound: StereoSound, panLen: Int) = {
    
    if(sound.length > panLen && panLen > 0){
        val increment = 1.00 / panLen
        var multiply: Double = 0
    
        var leftChannel = ArrayBuffer[Int]()
        val rightChannel = ArrayBuffer[Int]()
        
        //fadeIn to right ear
        for(i <- 0 until sound.left.size){
          rightChannel += (sound.right(i) * multiply).toInt
          
          if (multiply < 1){
            multiply += increment
          }else if(multiply > 1){
            multiply = 1
          }
        }
        
        //fadeOut to left ear
        for(i <- sound.left.size-1 to 0 by -1 ){
          leftChannel += (sound.left(i) * multiply).toInt
          
          if (multiply < 1){
            multiply += increment
          }else if(multiply > 1){
            multiply = 1
          }
        }
        
        leftChannel = leftChannel.reverse
        
        
        new StereoSound("Faded", leftChannel, rightChannel)
        
      } else {
      println("Method fadeOut did not succeed...")
      new StereoSound("noFade", sound.left, sound.right) 
    
    }
    
  }
  
  /*
   * Method echo provides an echo at the end of a sound sample. 
   * In the beginning creates ArrayBuffers for both left and right sound channels and 
   * then copies the original sound track. After that, delay is added. Lastly, the echo
   * is applied by multiplying the original sound track's samples by 0.25 and adding the samples 
   * at the end.
   */
  
  def echo(sound: StereoSound, delay: Int): StereoSound = {
       
    var leftChannel = ArrayBuffer[Int]()
    var rightChannel = ArrayBuffer[Int]()
    
    //Copy original sound track
    for(i <- 0 until sound.left.size ){
      leftChannel += sound.left(i)
      rightChannel += sound.right(i)
    }
    
    //Add delay for echo
    for(i <- 0 to delay){
      leftChannel += 0
      rightChannel += 0
    }
    
    //Add echo at the end, after delay
    for(i <- 0 until sound.left.size){
      leftChannel(delay + i) += (sound.left(i) * 0.25).toInt
      rightChannel(delay + i) += (sound.right(i) * 0.25).toInt
    }

    new StereoSound("echoSound", leftChannel, rightChannel)
    
  }
  
  /*
   * Method speedUp speeds the soundtracks by skipping 
   * few sound samples. The speed of the song is determined by
   * factor speed: Int. Factor speed is not recommended to exceed 10.
   */
  
  def speedUp(sound: StereoSound, speed: Int) = {
    
    var leftChannel = ArrayBuffer[Int]()
    var rightChannel = ArrayBuffer[Int]()
    
    for(i <- 0 until sound.left.size by speed){ //Factor speed determines how many samples are 'skipped'
      leftChannel += sound.left(i)
      rightChannel += sound.right(i)
    }
    
    new StereoSound("speededUpSound", leftChannel, rightChannel)
    
  }
  
  /*
   * Method slowDown slows soundtracks by increasing the amount
   * of sound samples in a track. Factor slowness: Int determines how 
   * slow the track will be played. Factor slowness is not recommended to 
   * exceed 5.
   */
  
  def slowDown(sound: StereoSound, slowness: Int) = {
    
    var leftChannel = ArrayBuffer[Int]()
    var rightChannel = ArrayBuffer[Int]()
    
    for(i <- 0 until sound.left.size){
      for(time <- 0 until slowness){
        leftChannel += sound.left(i)
        rightChannel += sound.right(i)
      }
    }
    
    new StereoSound("speededUpSound", leftChannel, rightChannel)
    
  }
  
  
  
}
  

