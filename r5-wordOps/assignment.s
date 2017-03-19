
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

# ------------------------------------------
# ... and ends here 

        hlt                     # the processor stops here

# (at halt we should have 39375 in $5, 10 in $6, and 59187 in $7)

