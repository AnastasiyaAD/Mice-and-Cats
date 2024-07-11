﻿note
	description: "mice_and_cat application root class"
	date: "$Date$"
	revision: "$Revision$"

class
	APPLICATION

inherit
	EXECUTION_ENVIRONMENT

create
	make

feature {NONE}
	t: TIME
	time_out: TIME
	s:INTEGER
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
			time_out.minute_add (1)
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
						cat.move (player.get_position_x, player.get_position_y)
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
				field.caught
				field.print_field
				print("%NTIMER: ")
				s := time_out.relative_duration (t).seconds_count
				timer.make_by_seconds (s)
				print(timer.formatted_out ("mi:ss")) --print Timer
				print("  CAUGHT: ")
				print(field.get_caught)
				sleep (1000 * 1000 * 600)
			end
			print(status)
		end

	is_time_out: BOOLEAN
	do
		Result := t >= time_out
	end
end
