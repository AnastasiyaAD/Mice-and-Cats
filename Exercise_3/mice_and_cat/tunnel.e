note
	description: "tunnel entrance class for the game"
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	TUNNEL

create
	make
feature
    position_x: INTEGER
    	-- position x on the a symbol on the playing field
    position_y: INTEGER
    	-- position y on the a symbol on the playing field

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

    make(x,y:INTEGER)
    	local
    		rand_x: INTEGER
    		rand_y: INTEGER
    		rand: RANDGENERATOR
    	do
    		create rand.make
			rand_x := rand.get(x-1) -- a pseudorandom number from 0 to width -1
    		rand_y := rand.get(y-1) -- a pseudorandom number from 0 to hight -1
			set_position_x(rand_x)
			set_position_y(rand_y)
    	end
end
