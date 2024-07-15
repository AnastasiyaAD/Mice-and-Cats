note
	description: "Summary description for {POSITION}."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	POSITION

create
    init

feature

    position_x: INTEGER
    position_y: INTEGER

    init
        do
            position_x := 1
            position_y := 1
        end
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

end
