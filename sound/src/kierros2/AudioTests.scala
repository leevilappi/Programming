package kierros2

object AudioTests extends App {
  
//Voit käyttää tätä oliota ääniefektien testaamiseen
//
//ESIM:
//
  
  val s: Int = 44100
  
  val sound = new StereoSound("sounds/love.wav")
  val sound2 = new StereoSound("sounds/water.wav")
  val sound2_1 = new StereoSound("sounds/water_shortfade.wav")
  val sound2_2 = new StereoSound("sounds/water_longfade.wav")
  val sound3 = new StereoSound("sounds/grace.wav")
  val sound4 = new StereoSound("sounds/airplane.wav")
  val sound4_1 = new StereoSound("sounds/airplane_pan.wav")
  val sound5 = new StereoSound("sounds/crow.wav")
  val sound5_1 = new StereoSound("sounds/crow_echo.wav")
  val sound6 = new StereoSound("sounds/welcome.wav")
  val sound6_1 = new StereoSound("sounds/welcome_echo.wav")
//  
//  println("removeVocals")
//  AudioEffects.removeVocals(sound).play()
//  
//  println("fadeIn, 2s")
//  AudioEffects.fadeIn(sound, 2 * s).play()
//  
//  println("fadeOut, short")
//  sound2_1.play()
//  println("fadeOut")
//  AudioEffects.fadeOut(sound2, 2 * s).play()
//  
//  println("fadeOut, long")
//  sound2_2.play()
//  println("fadeOut")
//  AudioEffects.fadeOut(sound2, 6 * s).play()
//  
//  println("fade")
//  sound3.play()
//  AudioEffects.fade(sound3, 2 * s).play()
//  
//  println("panLeftToRight")
//  sound4_1.play()
//  AudioEffects.panLeftToRight(sound4, 4 * s).play()
  
  println("echo")
  sound5_1.play()
  AudioEffects.echo(sound5, 20000).play()
  sound6_1.play()
  AudioEffects.echo(sound6, 10000).play()
  
  
  
  
}