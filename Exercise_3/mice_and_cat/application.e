note
	description: "mice_and_cat application root class"
	date: "$Date$"
	revision: "$Revision$"

class
	APPLICATION

inherit
	EXECUTION_ENVIRONMENT

create
	make

feature {NONE} -- Initialization
	make
			-- Launch `Current'.
		local
			read_char: CHARACTER
			terminal: TERMINAL
			exit: BOOLEAN
			player: MOUSE
			field: FIELD
		do
			create terminal.make
			terminal.set_non_blocking
			terminal.make_term_raw
			create field.make
			player := field.get_player
			print ("%/27/[25l") -- make cursor invisible
			from until exit loop
				print ("%/27/[1J")  -- erase screen
				print ("%/27/[H")   -- move cursor to top left
				read_char := terminal.get_char
				inspect read_char   -- mouse movement is not by arrows, but by S W D A  to exit, you need to press Q
					when 'a' then
						player.move_left
					when 'd' then
						player.move_right
					when 'w' then
						player.move_up
					when 's' then
						player.move_down
					when 'q' then
						exit := True
					else
				end
				field.print_field
				sleep (1000 * 1000 * 500)
			end
		end
end
