note
	description: "Player class for the game"
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	MOUSE
create
	make
feature  
    position_x: INTEGER
    	-- position x on the a symbol on the playing field
    position_y: INTEGER
    	-- position y on the a symbol on the playing field
    width_field: INTEGER
    hight_field: INTEGER

    set_position_x (new_position_x: INTEGER )
    	do
    		position_x := new_position_x
    	end
    set_position_y (new_position_y: INTEGER )
    	do
    		position_y := new_position_y
    	end

    get_position_x: INTEGER
    	do
    		Result := position_x
    	end
    get_position_y: INTEGER
    	do
    		Result := position_y
    	end
    move_up
    	do
    		if position_y > 0 then -- moving the player with checking the boundaries of the playing field
    			set_position_y(position_y - 1)
    		end
    	end
    move_down
    	do
    		if position_y < hight_field - 1 then
    			set_position_y(position_y + 1)
    		end
    	end
    move_left
    	do
    		if position_x > 0 then
    			set_position_x(position_x - 1)
    		end
    	end
    move_right
    	do
    		if position_x < width_field - 1 then
    			set_position_x(position_x + 1)
    		end
    	end
    make(x,y:INTEGER)
    	local
    		rand_x: INTEGER
    		rand_y: INTEGER
    		rand: RANDGENERATOR
    	do
    		create rand.make
    		width_field := x
    		hight_field := y
			rand_x := rand.get(x-1) -- a pseudorandom number from 0 to width -1
    		rand_y := rand.get(y-1) -- a pseudorandom number from 0 to hight -1
			set_position_x(rand_x)
			set_position_y(rand_y)
    	end

end

