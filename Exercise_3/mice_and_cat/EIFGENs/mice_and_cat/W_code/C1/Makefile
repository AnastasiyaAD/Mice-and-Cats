INCLUDE_PATH =  -I"$(ISE_LIBRARY)/library/base/elks/encoding/implementation/include" -I"$(ISE_LIBRARY)/library/time/spec/include" 
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
	te986.o te986d.o ra987.o ra987d.o tu988.o tu988d.o ti1005.o ti1005d.o \
	c_989.o c_989d.o da1010.o da1010d.o ab1006.o ab1006d.o ti996.o \
	ti996d.o ti1013.o ti1013d.o du1002.o du1002d.o ti995.o ti995d.o \
	da1012.o da1012d.o da1007.o da1007d.o da997.o da997d.o fi1009.o \
	fi1009d.o ti994.o ti994d.o gr992.o gr992d.o da1004.o da1004d.o \
	da1008.o da1008d.o da1011.o da1011d.o da1003.o da1003d.o da999.o \
	da999d.o co1001.o co1001d.o ti993.o ti993d.o da1000.o da1000d.o \
	da998.o da998d.o da991.o da991d.o da990.o da990d.o 

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

