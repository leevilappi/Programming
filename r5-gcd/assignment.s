
# This assignment asks you to write an assembly-language program that
# computes the greatest common divisor of two positive integers.
        
# Namely, your task is to compute the greatest common divisor of $0 and $1.
# The result must be stored into $2. Both $0 and $1 are guaranteed to
# be nonzero. Complete and submit the part delimited
# by "----" and indicated by a "nop" below.
#
# What makes this task challenging is that our "armlet" architecture
# has no hardware support for division, so you will have to make do
# without.
#
# Hint: Greatest common divisors may be computed using, for example,
# the Euclidean algorithm.

# Here is some wrapper code to test your solution:

        mov     $0, 14212      # load values to registers $0,$1
        mov     $1, 29484

# Your solution starts here ...
# ------------------------------------------
        
        
        mov 	$3, $0
        mov 	$4, $1
        
        # This solution utilizes a Chinese Algorithm, https://fi.wikipedia.org/wiki/Eukleideen_algoritmi
        
		@compare:
			cmp $3, $4			# Compare numbers
			beq >done			# If same we are done
			bgt >substract		# If left is bigger than right go to substract			
			sub $4, $4, $3
			jmp >compare
			
		@substract:
			sub $3, $3, $4		# Substract bigger by smaller
			jmp >compare		# Go back to compare
		
		@done:
			mov $2, $3			# Copy solution to slot $2
			hlt        


# ------------------------------------------
# ... and ends here 

        hlt                     # the processor stops here

# (at halt we should have 362 in $2)
