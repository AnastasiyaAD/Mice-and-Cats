note
	description: "Cat class for the game"
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	CAT

inherit
    POSITION

create
	make

feature{NONE}

    single_math: SINGLE_MATH -- library for .sqrt
    once -- mix single and double precision reals (SINGLE_MATH for REAL_32 and DOUBLE_MATH for REAL_64)
        create Result
    end

feature

    speed : DOUBLE
	max_x,max_y:INTEGER

    make(cat_x,cat_y:INTEGER; s:DOUBLE)
    	local
    		rand: RANDGENERATOR
    		rand_x: INTEGER
    		rand_y: INTEGER
    	do
    		create rand.make

    		max_x := cat_x
    		max_y := cat_y

    		speed := s

			rand_x := rand.get(cat_x - 1) -- a pseudorandom number from 0 to width -1
    		rand_y := rand.get(cat_y - 1) -- a pseudorandom number from 0 to hight -1

			set_position_x(rand_x)
			set_position_y(rand_y)
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

invariant
	0 <= position_x and position_x < max_x
	0 <= position_y and position_y < max_y
	speed > 0
end
