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

feature {NONE}  -- Implementation
	t: TIME -- current time
	time_out: TIME -- the time when the timer will end
	s:INTEGER -- the number of seconds until the timer ends
	timer:TIME
	make
		local
			read_char: CHARACTER
			terminal: TERMINAL
			exit: BOOLEAN
			player: MOUSE
			field: FIELD
			status: STRING
			cat: CAT
		do
			create terminal.make
			terminal.set_non_blocking
			terminal.make_term_raw
			create t.make_now
			create time_out.make_now
			time_out.minute_add (1) -- number of minutes per game
			create timer.make_now
			create field.make
			player := field.get_player
			cat := field.get_cat
			from until exit loop
				print ("%/27/[1J")  -- erase screen
				print ("%/27/[H")   -- move cursor to top left
				read_char := terminal.get_char
				inspect read_char   -- mouse movement is not by arrows, but by S W D A  to exit, you need to press Q
					when 'a' then
						player.move_left
						cat.move (player.get_position_x, player.get_position_y) -- calculation of the cat's next move based on the player's coordinates
					when 'd' then
						player.move_right
						cat.move (player.get_position_x, player.get_position_y)
					when 'w' then
						player.move_up
						cat.move (player.get_position_x, player.get_position_y)
					when 's' then
						player.move_down
						cat.move (player.get_position_x, player.get_position_y)
					when 'q' then
						exit := True
					else
				end
				t.make_now
				if field.is_tunnel then
					status := "%N%N   !!! VICTORY !!! "
					exit := True
				end
				if is_time_out then
					status := "%N%N !!! GAME OVER !!! "
					exit := True
				end
				field.caught -- checking for the caught of the player
				field.print_field -- displaying the updated playing field on the console
				print("%NTIMER: ")
				s := time_out.relative_duration (t).seconds_count -- calculating the seconds between the current time and the end of the timer
				timer.make_by_seconds (s)
				print(timer.formatted_out ("mi:ss")) -- converting seconds to the format minutes : seconds
				print("  CAUGHT: ")
				print(field.get_caught)
				print("%N%N O - mouse %N X - cat %N T - tunnel")
				sleep (1000 * 1000 * 600) ---  an appropriate frequency (without flickering)
			end
			print(status)
		end

	is_time_out: BOOLEAN
	do
		Result := t >= time_out
	end
end
