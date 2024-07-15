note
	description: "Summary description for {TIMER}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	TIMER

create
	make

feature {APPLICATION}

	t : TIME -- current time
	time_out : TIME -- the time when the timer will end
	s : INTEGER -- the number of seconds until the timer ends
	countdown : TIME

	make
		do
			create t.make_now
			create time_out.make_now
			create countdown.make_now
		end

	set_max_time (minutes :INTEGER)
		require
			minutes > 0
		do
			time_out.minute_add (minutes) -- number of minutes per game
		ensure
			duration: time_out > t
		end

	is_time_out: BOOLEAN
		do
			t.make_now
			Result := t >= time_out
		end

	print_timer
		do
			print("%NTIMER: ")
			t.make_now
			s := time_out.relative_duration (t).seconds_count -- calculating the seconds between the current time and the end of the timer
			countdown.make_by_seconds (s)
			print(countdown.formatted_out ("mi:ss")) -- converting seconds to the format minutes : seconds
		ensure
			new_duration: s >= 0
		end
end
