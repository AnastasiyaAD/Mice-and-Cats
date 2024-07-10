INCLUDE_PATH =  -I"$(ISE_LIBRARY)/library/base/elks/encoding/implementation/include" 
SHELL = /bin/sh
CC = cc
CPP = c++
CFLAGS = -O0 -pipe -fno-common -fPIC -DEIF_THREADS -DEIF_POSIX_THREADS  -DWORKBENCH -I"/Applications/MacPorts/Eiffel_24.05/EiffelStudio.app/Contents/MacOS/../Resources/../../../studio/spec/macosx-armv6/include" -I. $(INCLUDE_PATH)
CPPFLAGS = -O0 -pipe -fno-common -fPIC -DEIF_THREADS -DEIF_POSIX_THREADS  -DWORKBENCH -I"/Applications/MacPorts/Eiffel_24.05/EiffelStudio.app/Contents/MacOS/../Resources/../../../studio/spec/macosx-armv6/include" -I. $(INCLUDE_PATH)
LDFLAGS = 
CCLDFLAGS =   
LDSHAREDFLAGS =   -dynamiclib -flat_namespace -undefined suppress -o
EIFLIB = "/Applications/MacPorts/Eiffel_24.05/EiffelStudio.app/Contents/MacOS/../Resources/../../../studio/spec/macosx-armv6/lib/libmtwkbench.a"
EIFTEMPLATES = /Applications/MacPorts/Eiffel_24.05/EiffelStudio.app/Contents/MacOS/../Resources/../../../studio/config/macosx-armv6/templates
LIBS = -lm -lpthread
MAKE = make
AR = ar
LD = cc
MKDEP = \$(EIFFEL_SRC)/C/mkdep $(DPFLAGS) --
MV = /bin/mv
CP = /bin/cp
RANLIB = :
RM = /bin/rm -f
FILE_EXIST = 
RMDIR = 
X2C = "/Applications/MacPorts/Eiffel_24.05/EiffelStudio.app/Contents/MacOS/../Resources/../../../studio/spec/macosx-armv6/bin/x2c"
SHAREDLINK = cc
SHAREDLIBS = -lm
SHARED_SUFFIX = .dylib
COMMAND_MAKEFILE = 
START_TEST = if [ ! -f finished ] ; then 
END_TEST = ; fi 
CREATE_TEST = touch finished 
SYSTEM_IN_DYNAMIC_LIB = mice_and_cat.dylib 

.SUFFIXES:.cpp .o

.c.o:
	$(CC) $(CFLAGS) -c $<

.cpp.o:
	$(CPP) $(CPPFLAGS) -c $<

OBJECTS = big_file_C1_c.o 

OLDOBJECTS =  ap982.o ap982d.o ca984.o ca984d.o mo983.o mo983d.o fi985.o fi985d.o \
	te986.o te986d.o 

all: Cobj1.o

Cobj1.o: $(OBJECTS) Makefile
	$(LD) $(LDFLAGS) -r -o Cobj1.o $(OBJECTS)
	$(RM) $(OBJECTS)
	$(CREATE_TEST)

clean: local_clean
clobber: local_clobber

local_clean::
	$(RM) core finished *.o

local_clobber:: local_clean
	$(RM) Makefile

