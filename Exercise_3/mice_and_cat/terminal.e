note
	description: "This class provides a simple interface to the terminal. It is used to set the terminal to non-blocking mode and to read a single character from the terminal."
	author: ""
	date: "$Date$"
	revision: "$Revision$"

class
	TERMINAL

create
    make

feature

    make
        do

        end -- Implementation
	set_non_blocking
		external "C inline use <fcntl.h>"
			alias "fcntl(0, F_SETFL, O_NONBLOCK);"
		end

	make_term_raw
		external "C inline use <termios.h>"
			alias "struct termios term; tcgetattr(0, &term); term.c_lflag &= ~(ICANON | ECHO); term.c_cc[VTIME] = 0; term.c_cc[VMIN] = 0; tcsetattr(0, 0, &term);"
		end

	get_char : CHARACTER
		external "C inline use <unistd.h>"
			alias "{
				char c, last;
				while (read(0, &c, 1) > 0 && c != '\n') {last = c;}
				return c;
			}"
		end
end
