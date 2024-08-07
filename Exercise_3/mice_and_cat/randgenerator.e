note
	description: "This class is used to generate random numbers between 0 and set value"
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	RANDGENERATOR


create
	make
feature
	r: RANDOM
	make
    	local
	    	t: TIME
	    	seed: INTEGER
		do
			create t.make_now -- current time
           	seed := t.hour
            seed := seed * 60 + t.minute
            seed := seed * 60 + t.second
            seed := seed * 1000 + t.milli_second
	        create r.set_seed (seed)
    	end


	get(max_value:INTEGER): INTEGER
		do
			r.forth
	    	Result := r.item \\ max_value
		end
end
