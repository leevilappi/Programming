
# This assignment asks you to implement some basic word operations
# with assembly language.

# Namely, your solution should implement all of the following:
# 1) Reverse the order of bits in $0 and store the result in $5
#    (that is, the bit in position 0 goes to position 15, position 1 goes
#    to position 14, position 2 goes to position 13, ..., and position 15
#    goes to position 0; the contents of $0 must remain unchanged);
# 2) Count the number of 1-bits in $0 and store the result in $6; and
# 3) Rotate the bits in $0 by one position to the left and store 
#    the result in $7. (That is, bit in position 0 goes to position 1, 
#    position 1 goes to 2, position 2 goes to 3, ..., position 14 goes 
#    to position 15, and position 15 goes to position 0.)

# Here is some wrapper code to test your solution:
        
         mov     $0, 62361       # load test input to $0
         
      	 
		  
# Your solution starts here ...
# ------------------------------------------
        
        nop                     # ready for your code over here
        
         mov $5, 0
 		 mov $1, 0
		 mov $2, 0			#var b 
		 mov $3, $0			#var x
		 mov $4, 0
		 
		 
		
		 @loop:
			 cmp $1, 16
			 beq >done
			 add $1, $1, 1	#loop counter +1
			 
			 lsl $2, $2,1	# b = b <<  1
			 and $4, $3,1	# x & 1
			 ior $2, $2,$4  # b = b | x
			 add $6, $6,$4
			 lsr $3, $3, 1	# x = x >> 1
			 

		
		 jmp >loop
		 

		 
		 @done:
		 mov $5, $2
		 
		 	 mov $2, 0
		 	 mov $4, 0
		 	 lsr $2, $0, 15 
		 	 ior $7, $7, $2
		 	 lsl $1, $0, 1
		 	 ior $7, $7, $1
		 	 
		 hlt
        

# ------------------------------------------
# ... and ends here 

        hlt                     # the processor stops here

# (at halt we should have 39375 in $5, 10 in $6, and 59187 in $7)

