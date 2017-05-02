
package object bwt {
  
  def transform(s: String): String = {
      
    
    //Function to rotate input to different rotation variants
    def rotate(s: String): List[String] = {
      def loop(s: String, acc: List[String]): List[String] = {
        val rotated = s.tail :+ s.head
        if (s.length != acc.length) loop(rotated, acc :+ rotated)
        else acc
      }
      loop(s, Nil)
   }

    var inputRotated = rotate(s).sorted.toArray
    var result = ""
    
    //Collect last char (= "each column") of each list
    for( i <- inputRotated){
      result += i.last
    }
    
    result
     
  }

  def inverse(s: String): String = {
     import scala.collection.mutable.Buffer
     
     var counter = 0
     var input = Buffer[String]()
     
     //Form array of chars
     for(i <- 0 until s.length()){
       input += s.charAt(i).toString
     }
     
     input = input.sorted
     
     //Add and rotate table
     while(counter < s.length()-1){
       for(x <- 0 until s.length() ){
         input(x) = s(x) + input(x)
       }
       input = input.sorted
       counter += 1
     }
     
     input.filter { x => x.last == '|' }.head
     
  }
}

