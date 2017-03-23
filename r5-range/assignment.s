
# This assignment asks you to find the range of a data array.

# Namely, you must find the range of the data array that starts
# at memory address $0 and contains $1 words.
# The range is the maximum value minus the minimum value in the data.
# All values should be viewed as unsigned.
# The range must be stored in register $2.
# Complete and submit the part delimited by "----" and indicated by
# a "nop" below.

# Here is some wrapper code to test your solution:

        mov     $0, >test_data  # set up the address where to get the data
        mov     $1, >test_len   # set up address where to get the length
        loa     $1, $1          # load the length from memory to $1

# Your solution starts here ...
# ------------------------------------------

        nop                     # ready for your code over here

      cmp $1, 1                    # compare length with 1
      bab >have_data               # ... if length > 1, branch to process data
      hlt                          # ... otherwise halt immediately
      
      
      
@have_data:
      mov $4, $0                   # set up first address
      add $5, $4, $1               # set up last address
      loa $2, $4                   # set up a candidate maximum
      loa $3, $4				   # set up a candidate minimum
      sub $7, $4, $0               # set up position of candidate maximum
      add $4, $4, 1                # advance current address
      cmp $4, $5                   # are we at the last address?
      bbw >min_scan_loop           # ... if not, continue scanning
      jmp >done                    # ... otherwise we are done

# ... and then scan for the maximum and minimum. 
# First check if value is the min, if not then check if it is maximum.

@min_scan_loop:
      loa $6, $4                   # load current data item
      cmp $6, $3                   # compare current with candidate maximum
      bae >max_scan_loop           # ... if current >= candidate, no update
      mov $3, $6                   # ... update candidate minimum
      sub $1, $4, $0               #     ... and its position

@max_scan_loop:
      loa $6, $4                   # load current data item
      cmp $6, $2                   # compare current with candidate maximum
      bbe >no_update           # ... if current <= candidate, no update
      mov $2, $6                   # ... update candidate maximum
      sub $1, $4, $0               #     ... and its position

 
@no_update:
      add $4, $4, 1                # advance current address
      cmp $4, $5                   # compare current address with last address
      bbw >min_scan_loop           # ... if curr addr < last addr, continue scan
     


# ... until we are done

	 sub $2, $2, $3

@done:
      hlt                          # the processor stops here
        
# ------------------------------------------
# ... and ends here 

        hlt                     # the processor stops here

# Here is some test data:
# (the minimum is 151 and the maximum is 9978, so the range is 9978-151 = 9827)

@test_len:
        %data   28
@test_data:
        %data   9554, 4689, 447, 9554, 4085, 415, 447, 4244, 4596, 447, 4596, 3767, 307, 415, 4085, 4689, 307, 9554, 447, 447, 9554, 4596, 3829, 307, 4689, 415, 307, 4085
