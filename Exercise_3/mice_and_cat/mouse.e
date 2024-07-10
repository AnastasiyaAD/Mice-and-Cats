note
	description: "Summary description for {MOUSE}."
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
    		if position_y > 0 then
    			set_position_y(position_y - 1)
    		end
    	end
    move_down
    	do
    		if position_y < 8 then
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
    		if position_x < 18 then
    			set_position_x(position_x + 1)
    		end
    	end
    make
    	do

    	end

end

