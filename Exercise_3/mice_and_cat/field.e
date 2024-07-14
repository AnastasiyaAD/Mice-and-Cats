note
	description: "This class is the game field. It contains the field, the tunnels, the player and the cat."
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
			width := 22 -- the size of the playing field
			hight := 11 -- the size of the playing field
			speed := 3.5 -- refers to cats

			create player.make(width, hight) -- creating an instance of the mouse class with a pseudo-random location on the playing field
			create tunnel_1.make(width, hight//2) -- creating an instance of the tunnel class with a pseudo-random location on the playing field
			create cat.make(width\\2, hight, speed) -- creating an instance of the cat class with a pseudo-random location on the playing field
			create tunnel_2.make(width//2, hight) -- creating an instance of the tunnel class with a pseudo-random location on the playing field
			tunnels:= <<tunnel_1,tunnel_2>> -- creating an array of tunnel entrances
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


    is_tunnel: BOOLEAN -- checking the intersection of the coordinates of the tunnel entrance and the player
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

    print_field --create new gamefield with cat, player, tunnels
		local
	    i: INTEGER
	    j: INTEGER
	    print_dot: BOOLEAN
		do
			from
				i := 0
			until
				i >= hight
			loop -- a cycle for passing along the Y axis
					from
						j := 0
					until
						j >= width
					loop -- a cycle for passing along the X axis
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
