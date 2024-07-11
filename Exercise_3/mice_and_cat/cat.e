note
	description: "Summary description for {CAT}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	CAT

create
	make
feature
	single_math: SINGLE_MATH
    once
        create Result
    end
    speed : DOUBLE
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
    move(mouse_x,mouse_y: INTEGER)
    	local
    		dx: INTEGER
    		dy: INTEGER
    		arg: INTEGER
    		lenght: DOUBLE
    		dir_x: DOUBLE
    		dir_y: DOUBLE
    		move_x: DOUBLE
    		move_y: DOUBLE
    		new_x: INTEGER
    		new_y: INTEGER

	    do

	    	dx := mouse_x - position_x
	    	dy := mouse_y - position_y

	    	arg := dx * dx + dy * dy
	    	lenght := single_math.sqrt(arg)

	    	dir_x := dx/lenght -- Normalize the direction vector
	    	dir_y := dy/lenght

	    	move_x := dir_x * speed -- Scale the direction vector by the given distance
	    	move_y := dir_y * speed

	    	new_x := (position_x+(move_x/3)).rounded -- Compute the new position
			new_y := (position_y+(move_y/3)).rounded

			set_position_x(new_x)
			set_position_y(new_y)

	    end

    make(x,y:INTEGER; s: DOUBLE)
    	local
    		rand_x: INTEGER
    		rand_y: INTEGER
    		rand: RANDGENERATOR
    	do
    		create rand.make
    		rand_x := rand.get(x-1)
    		rand_y := rand.get(y-1)
			set_position_x(rand_x)
			set_position_y(rand_y)
			speed := s
    	end
end
