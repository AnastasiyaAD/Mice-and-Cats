note
	description: "tunnel entrance class for the game"
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	TUNNEL

inherit
    POSITION

create
	make

feature
	max_x,max_y:INTEGER

    make(tunnel_x,tunnel_y:INTEGER)
    	local
    		rand_x: INTEGER
    		rand_y: INTEGER
    		rand: RANDGENERATOR
    	do
    		create rand.make

    		max_x := tunnel_x
    		max_y := tunnel_y

			rand_x := rand.get(tunnel_x-1) -- a pseudorandom number from 0 to width -1
    		rand_y := rand.get(tunnel_y-1) -- a pseudorandom number from 0 to hight -1

			set_position_x(rand_x)
			set_position_y(rand_y)
    	end

invariant
	0 <= position_x and position_x < max_x
	0 <= position_y and position_y < max_y
end
