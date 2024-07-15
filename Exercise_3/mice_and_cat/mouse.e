note
	description: "Player class for the game"
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	MOUSE

inherit
    POSITION

create
	make

feature

    max_x,max_y:INTEGER

    make(mouse_x,mouse_y:INTEGER)
    	local
    		rand_x: INTEGER
    		rand_y: INTEGER
    		rand: RANDGENERATOR
    	do
    		create rand.make

    		max_x := mouse_x
    		max_y := mouse_y

			rand_x := rand.get(mouse_x-1) -- a pseudorandom number from 0 to width -1
    		rand_y := rand.get(mouse_y-1) -- a pseudorandom number from 0 to hight -1

			set_position_x(rand_x)
			set_position_y(rand_y)
    	end

    move_up -- moving the player with checking the boundaries of the playing field
    	do
    		if position_y > 0 then
    			set_position_y(position_y - 1)
    		end
    	end
    move_down -- moving the player with checking the boundaries of the playing field
    	do
    		if position_y < max_y - 1 then
    			set_position_y(position_y + 1)
    		end
    	end
    move_left -- moving the player with checking the boundaries of the playing field
    	do
    		if position_x > 0 then
    			set_position_x(position_x - 1)
    		end
    	end
    move_right -- moving the player with checking the boundaries of the playing field
    	do
    		if position_x < max_x - 1 then
    			set_position_x(position_x + 1)
    		end
    	end

invariant
	0 <= position_x and position_x < max_x
	0 <= position_y and position_y < max_y
end


