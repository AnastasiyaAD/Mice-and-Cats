note
	description: "Summary description for {FIELD}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	FIELD

inherit
	EXECUTION_ENVIRONMENT
create
	make

feature
	player: MOUSE
    tunnel_1: TUNNEL
    tunnel_2: TUNNEL
    width: INTEGER
    hight: INTEGER
    tunnels: ARRAY[TUNNEL]
	make
		do
			width := 20
			hight := 10

			create player.make(width, hight)
			create tunnel_1.make(width, hight\\2)
			create tunnel_2.make(width\\2, hight)
			tunnels:= <<tunnel_1,tunnel_2>>


		end

	get_player: MOUSE
        do
            Result := player
        end

    is_tunnel: BOOLEAN
    	local
        	value: BOOLEAN
        do
        	value := false
            across tunnels as t loop
    			if t.item.get_position_x = player.get_position_x and t.item.get_position_y = player.get_position_y then
    				value := true
    			end
			end
			Result := value
        end

    print_field --create new gamefield
		local
	    i: INTEGER
	    j: INTEGER
	    print_tunnel: BOOLEAN
		do
			from
				i := 0
			until
				i >= hight
			loop
					from
						j := 0
					until
						j >= width
					loop
						print_tunnel := false
						across tunnels as t loop -- print tunnels
			    			if i = t.item.get_position_y and j = t.item.get_position_x then
			    				print ("T")
			    				print_tunnel:= true
			    			end
						end

						if print_tunnel then
						else
							if i = player.get_position_y and j = player.get_position_x then -- print mouse
							print ("O")

							else
								print(".") -- print field
							end
						end
						j := j + 1
					end
				print("%N")
				i := i + 1
			end
		end
end
