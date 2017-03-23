import scala.math.round
package object longestIncreasingSubsequence {

  /** Returns a longest increasing subsequence in the sequence s.
   *  If there are multiple such subsequences, any subsequence will do. */

  def round(i: Double) = {
         var total = i

         total = Math.round(total * 100);

         total / 100
     }
  
   def hackerMultipliers(x: Int, y: Int, z: Int): Array[Double] = {
     require(x >= 20 && x <= 60 && y >= 1 && y <= 5 && z >= 1 && z <= 6)
     
     //Määritä kerroin habithackeille
     var h = round (50.00 / x)
     
     //Määritä kerroin discoverylle
     var d = round (20.00 / y)
     
     //Määritä kerroin targetoinnille
     var t = round (30.00 / z)
     
     var r = 100 - x * h - y * d - t * z
         t = round(t + r)
         
     Array(h,d,t)
   }
   
   def test() = {
     for(x <- 20 to 60){
       for(y <- 1 to 5){
         for(z <- 1 to 6){
           var a = hackerMultipliers(x,y,z)(0)
           var b = hackerMultipliers(x,y,z)(1)
           var c = hackerMultipliers(x,y,z)(1)
           var total = round(x * a + y * b + z * c) 
           if (total <= 100 && total >= 90 && c <= 10 && b <= 10){
             print("\n")
             print("\n" + x +", "+y +", " + z)
             print("\n" + hackerMultipliers(x,y,z).mkString(", "))
             print("\n" + "TOTAL: " +  total )
           }
           
       }
     }  
    }
   }
}

