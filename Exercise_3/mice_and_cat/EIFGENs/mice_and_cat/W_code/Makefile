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

SUBDIRS = C1 C2 E1

EXTERNALS =  \
	`$(ISE_LIBRARY)/library/base/elks/encoding/implementation/unix/Clib/iconv-config --library`

all: mice_and_cat

C1/Cobj1.o: Makefile E1/estructure.h
	cd C1 ; $(START_TEST) $(SHELL) Makefile.SH ; $(MAKE) $(END_TEST)

C2/Cobj2.o: Makefile E1/estructure.h
	cd C2 ; $(START_TEST) $(SHELL) Makefile.SH ; $(MAKE) $(END_TEST)

E1/Makefile: E1/Makefile.SH
	cd E1 ; $(SHELL) Makefile.SH

E1/emain.o: Makefile E1/Makefile
	$(CP) "$(EIFTEMPLATES)/emain.template" "E1/emain.c"
	cd E1 ; $(MAKE) emain.o

E1/eoption.o: Makefile E1/Makefile E1/estructure.h
	cd E1 ; $(MAKE) eoption.o

E1/epattern.o: Makefile E1/Makefile E1/estructure.h
	cd E1 ; $(MAKE) epattern.o

E1/efrozen.o: Makefile E1/Makefile E1/estructure.h
	cd E1 ; $(MAKE) efrozen.o

E1/ecall.o: Makefile E1/Makefile E1/estructure.h
	cd E1 ; $(MAKE) ecall.o

E1/eplug.o: Makefile E1/Makefile E1/estructure.h
	cd E1 ; $(MAKE) eplug.o

E1/eskelet.o: Makefile E1/Makefile E1/estructure.h
	cd E1 ; $(MAKE) eskelet.o

E1/enames.o: Makefile E1/Makefile E1/estructure.h
	cd E1 ; $(MAKE) enames.o

E1/evisib.o: Makefile E1/Makefile E1/estructure.h
	cd E1 ; $(MAKE) evisib.o

E1/ececil.o: Makefile E1/Makefile E1/estructure.h
	cd E1 ; $(MAKE) ececil.o

E1/einit.o: Makefile E1/Makefile E1/estructure.h
	cd E1 ; $(MAKE) einit.o

E1/eparents.o: Makefile E1/Makefile E1/estructure.h
	cd E1 ; $(MAKE) eparents.o

OBJECTS= C2/Cobj2.o C1/Cobj1.o  E1/eoption.o E1/epattern.o E1/efrozen.o E1/ecall.o E1/eplug.o E1/eskelet.o E1/enames.o E1/evisib.o E1/ececil.o E1/einit.o E1/eparents.o

PRECOMP_OBJECTS= "/Users/anastasiadudina/Eiffel User Files/24.05/precomp/spec/macosx-armv6/EIFGENs/base-scoop-safe/W_code/preobj.o"


mice_and_cat: $(OBJECTS)  E1/emain.o Makefile
	$(RM) mice_and_cat
	$(CC) -o mice_and_cat $(CFLAGS) $(CCLDFLAGS)  $(OBJECTS) E1/emain.o \
		$(PRECOMP_OBJECTS) $(EXTERNALS) $(EIFLIB) $(LIBS)

RCECIL = MTdebug.o MTidrs.o MTinterp.o mtipc.o MTnetwork.o MTupdate.o \
	MTwargv.o MTwbench.o MTwboolstr.o MTwcecil.o MTwcompress.o \
	MTwconsole.o MTwcopy.o MTwdir.o MTweif_project.o MTweif_threads.o \
	MTweif_type_id.o MTwequal.o MTwerror.o MTweveqs.o MTwexcept.o  \
	MTwfile.o MTwgarcol.o MTwgen_conf.o MTwhash.o MTwhashin.o MTwhector.o \
	MTwinternal.o MTwlmalloc.o MTwlocal.o MTwmain.o MTwmalloc.o \
	MTwmemory.o MTwmemory_analyzer.o MTwmisc.o MTwnotify_token.o \
	MTwobject_id.o MTwoffset.o MTwoption.o MTwout.o MTwpath_name.o \
	MTwplug.o MTwposix_threads.o MTwprivate_queue.o MTwprocessor.o \
	MTwprocessor_registry.o MTwqueue_cache.o MTwreq_grp.o MTwretrieve.o \
	MTwrout_obj.o MTwrun_idr.o MTwscoop.o MTwscoop_gc.o MTwsearch.o \
	MTwsig.o MTwstore.o MTwtimer.o MTwtools.o MTwtraverse.o MTwumain.o \
	MTwurgent.o 

STATIC_CECIL = libmice_and_cat.a
cecil: $(STATIC_CECIL)
$(STATIC_CECIL):  $(OBJECTS) 
	$(AR) x $(EIFLIB)
	$(AR) cr $(STATIC_CECIL) $(OBJECTS) $(PRECOMP_OBJECTS) $(RCECIL)
	$(RANLIB) $(STATIC_CECIL)
	$(RM) $(RCECIL) 

SHARED_CECIL = libmice_and_cat$(SHARED_SUFFIX)
dynamic_cecil: $(SHARED_CECIL) 
SHARED_CECIL_OBJECT = $(OBJECTS) \
		E1/emain.o
SHAREDFLAGS = $(LDSHAREDFLAGS) $(SHARED_CECIL) 
$(SHARED_CECIL): $(SHARED_CECIL_OBJECT) 
	$(RM) $(SHARED_CECIL) 
	$(SHAREDLINK) $(SHAREDFLAGS) $(SHARED_CECIL_OBJECT) $(PRECOMP_OBJECTS) $(EXTERNALS) $(EIFLIB) $(SHAREDLIBS) 



dynlib: $(SYSTEM_IN_DYNAMIC_LIB) 
E1/egc_dynlib.o: Makefile E1/Makefile
	$(CP) "$(EIFTEMPLATES)/egc_dynlib.template" E1/egc_dynlib.c
	cd E1 ; $(MAKE) egc_dynlib.o ; cd ..
E1/edynlib.o: Makefile E1/Makefile E1/edynlib.c 
	cd E1 ; $(MAKE) edynlib.o ; cd ..

SYSTEM_IN_DYNAMIC_LIB_OBJ = $(OBJECTS) \
		E1/edynlib.o E1/egc_dynlib.o 
DYNLIBSHAREDFLAGS = $(LDSHAREDFLAGS) $(SYSTEM_IN_DYNAMIC_LIB) 
$(SYSTEM_IN_DYNAMIC_LIB): $(SYSTEM_IN_DYNAMIC_LIB_OBJ) 
	$(RM) $(SYSTEM_IN_DYNAMIC_LIB) 
	$(SHAREDLINK) $(DYNLIBSHAREDFLAGS) $(SYSTEM_IN_DYNAMIC_LIB_OBJ) $(PRECOMP_OBJECTS) $(EXTERNALS) $(EIFLIB) $(SHAREDLIBS) 


clean: sub_clean local_clean
clobber: sub_clobber local_clobber

local_clean::
	$(RM) core finished *.o *.so *.a

local_clobber:: local_clean
	$(RM) Makefile config.sh finish_freezing

sub_clean::
	for i in $(SUBDIRS); \
	do \
		if [ -r $$i/Makefile ]; then \
			(cd $$i ; $(MAKE) clean); \
		fi; \
	done

sub_clobber::
	for i in $(SUBDIRS); \
	do \
		if [ -r $$i/Makefile ]; then \
			(cd $$i ; $(MAKE) clobber); \
		fi; \
	done

