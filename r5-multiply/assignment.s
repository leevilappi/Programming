
# This assignment asks you to write an assembly-language program that
# multiplies two integers.
        
# Namely, your task is to compute $0 * $1 and store the result in $2.
# The result is guaranteed to be in the range 0,1,...,65535.
# Both $0 and $1 should be viewed as unsigned integers.
# Complete and submit the part delimited by "----" and indicated by
# a "nop" below.

# Here is some wrapper code to test your solution:

        mov     $0, 82          # load some values to registers $0,$1
        mov     $1, 430

# Your solution starts here ...
# ------------------------------------------
        
        nop                     # ready for your code over here
        
        mov 	$3, 0 			# To be multiplied
        mov 	$4, 0			# Multiplier
        mov 	$5, 0			# Loop counter
        
	@multiply:
		cmp 	$5, 16			# Compare if done
		beq 	>done			# Go to done if equals zero

		lsr 	$4, $1, $5		# Right shift multiplier by index
		lsl		$3, $0, $5		# Left shift to be multiplied by index
		and 	$4, $4, 1		# Choose the rightmost value as multiplier
		
		add 	$5, $5, 1		# Add one
		cmp 	$4, 0			# Compare result
		beq		>multiply		# If zero -> go back
		add 	$2, $2, $3		# Sum results 
		jmp 	>multiply		# Go back for next index
				
 				
    @done:
    	hlt

# ------------------------------------------
# ... and ends here 

        hlt                     # the processor stops here

# (at halt we should have 35260 in $2)
