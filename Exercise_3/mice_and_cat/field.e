note
	description: "Summary description for {FIELD}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	FIELD
inherit
	ARGUMENTS_32
create
	make
feature {NONE} -- Initialization
    player: MOUSE
feature -- Access
	make
		do
			create player.make
			player.set_position_x (0)
			player.set_position_y (0)
		end
	get_player: MOUSE
        do
            Result := player
        end
    print_field --create new gamefield
		local
	    i: INTEGER
	    j: INTEGER
		do
			from
				i := 0
			until
				i >= 10
			loop
				from
					j := 0
				until
					j >= 20
				loop
					if i = player.get_position_y and j = player.get_position_x then -- print mouse
						print ("O")
					else
						print (".") -- print field
					end

					j := j + 1
				end
				print("%N")
				i := i + 1
			end
		end
end
