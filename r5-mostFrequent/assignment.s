
# This assignment asks you to find the most frequent value in a data array.
        
# Namely, you must find the most frequently occurring value in a data
# array that starts at memory address $0 and contains $1 words.
# The most frequent value must be stored in register $2.
# Complete and submit the part delimited by "----" and indicated
# by a "nop" below.

# Here is some wrapper code to test your solution:

        mov     $0, >test_data  # set up the address where to get the data
        mov     $1, >test_len   # set up address where to get the length
        loa     $1, $1          # load the length from memory to $1

# Your solution starts here ...
# ------------------------------------------
        
# Selection sort for the armlet

      add $1, $1, $0
      sub $1, $1, 1           # address of the last data item
      
@select_loop:
      cmp $0, $1              # compare start address and last address
      bae >free_memory        # ... if start addr >= last addr, we are done
      loa $2, $0              # set up a candidate maximum
      mov $3, $0              # set up address of candidate maximum
      add $4, $0, 1           # set up current address
      
@max_scan_loop:
      cmp $4, $1              # compare current address with last address
      bab >scan_done          # ... if curr addr > last addr, we have the max
      loa $5, $4              # load current data item
      cmp $5, $2              # compare current item with candidate max
      bbe >no_update          # ... if current <= candidate, no need to update
      mov $2, $5              # update candidate maximum
      mov $3, $4              # update address of candidate maximum
      
@no_update:
      add $4, $4, 1           # advance to next element
      jmp >max_scan_loop      # continue scanning
      
@scan_done:
      # at this point $2 is the max item and $3 is its addr in array
      # transpose max item and last item in current array ...
      sub $4, $4, 1           # address of last item
      loa $5, $4              # load last item
      sto $4, $2              # store max to last position
      sto $3, $5              # store last item to max position
      sub $1, $1, 1           # remove last item (now =max) from consideration
      jmp >select_loop        # continue sorting the remaining array
      
      
@free_memory:
	  mov     $1, >test_len   # set up address where to get the length
      loa     $1, $1          # load the length from memory to $1
	  mov $2, 0				  # holder for the most frequent number 
	  mov $3, 0				  # counter for the most frequent number
	  mov $4, 0				  # counter for the current number frequency
	  jmp >scan_data
	  
@begin_new_scan:
	  mov $4, 0				  # Reset counter 
	  jmp >scan_data		  # Begin new scan otherwise
      
@scan_data:
	  sub $1, $1, 1			  # Subtract one to end scan eventually
	  cmp $1, 0				  # Compare length to zero
	  beq >final_check				  # If length equals zero -> done
	  
	  loa $6, $0			  # Load memory slot to $6
	  add $0, $0, 1		      # Move to next memory slot
	  loa $7, $0			  # Load next memory slot to $7
	  cmp $6, $7			  # Compare current and next values
	  beq >add_one			  # If the same number, add one
	  add $4, $4, 1			  # Add one to counter
	  jmp >compare			  # Numbers are not same -> Compare frequency to previous set.
	  
@add_one:
	  add $4, $4, 1			  # Add one to counter
	  jmp >scan_data		  # Continue scan
	  
	  
@compare:
	  cmp $3, $4			  # Compare most frequent frequency and current's frequency
	  bgt >begin_new_scan	  # If smaller than most frequent begin new scan, otherwise update most frequent
	  mov $2, $6			  # Update most frequent number
      mov $3, $4 			  # Update frequency of most frequent number
      jmp >begin_new_scan

@final_check:
	  cmp $6, $7			  # Compare current and next values
	  bne >done			      # If different, we are done
	  add $4, $4, 1			  # Add one to counter
	  cmp $3, $4			  # Compare most frequent frequency and current's frequency
	  bgt >done 	     	  # If smaller than most frequent -> done
	  mov $2, $7			  # Update most frequent number
      mov $3, $4 			  # Update frequency of most frequent number
      jmp >done
	  
      
@done:
      hlt
      
# ------------------------------------------
# ... and ends here 

        hlt                     # the processor stops here

# Here is the test data:
# (the most frequent value is 56369 with frequency 5 in the array)

@test_len:
        %data   26
@test_data:
        %data   40177, 11736, 25168, 47202, 23755, 11736, 46935, 25168, 7679, 25168, 40177, 7679, 11736, 40177, 47202, 19586, 33582, 7679, 25837, 47202, 6040, 6040, 25168, 53381, 19586, 6040



