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
    number_caught: INTEGER


    cat: CAT
	make
		local
			speed: DOUBLE
		do
			width := 22
			hight := 11
			speed := 3.5

			create player.make(width, hight)
			create tunnel_1.make(width, hight//2)
			create cat.make(width\\2, hight, speed)
			create tunnel_2.make(width//2, hight)
			tunnels:= <<tunnel_1,tunnel_2>>
			number_caught := 0


		end

	get_player: MOUSE
        do
            Result := player
        end

    get_cat: CAT
        do
            Result := cat
        end

    caught -- moving the mouse to a random location
    	do
			if player.get_position_y = cat.get_position_y and player.get_position_x = cat.get_position_x then
				number_caught := number_caught + 1
				player.make(width, hight)
			end
        end

   get_caught: INTEGER
   		do
   			Result := number_caught
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
	    print_dot: BOOLEAN
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
						print_dot := true
						across tunnels as t loop -- print tunnels
			    			if i = t.item.get_position_y and j = t.item.get_position_x then
			    				print ("T")
			    				print_dot:= false
			    			end
						end

						if i = cat.get_position_y and j = cat.get_position_x  then -- print cat
							print ("X")
							print_dot:= false
						end

						if print_dot then
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
