/*
 * Code for class TERMINAL
 */

#include "eif_eiffel.h"
#include "../E1/estructure.h"


#ifdef __cplusplus
extern "C" {
#endif

extern void F986_7457(EIF_REFERENCE);
extern void F986_7458(EIF_REFERENCE);
extern void F986_7461(EIF_REFERENCE);
extern EIF_TYPED_VALUE F986_7460(EIF_REFERENCE);
extern void EIF_Minit986(void);

#ifdef __cplusplus
}
#endif

#include <termios.h>
#include <unistd.h>
#include <fcntl.h>

#ifdef __cplusplus
extern "C" {
#endif

#ifndef INLINE_F986_7458
static void inline_F986_7458 (void)
{
	fcntl(0, F_SETFL, O_NONBLOCK);
	;
}
#define INLINE_F986_7458
#endif
#ifndef INLINE_F986_7461
static void inline_F986_7461 (void)
{
	struct termios term; tcgetattr(0, &term); term.c_lflag &= ~(ICANON | ECHO); term.c_cc[VTIME] = 0; term.c_cc[VMIN] = 0; tcsetattr(0, 0, &term);
	;
}
#define INLINE_F986_7461
#endif
#ifndef INLINE_F986_7460
static EIF_CHARACTER_8 inline_F986_7460 (void)
{
	char c, last;
				while (read(0, &c, 1) > 0 && c != '\n') {last = c;}
				return c;
	;
}
#define INLINE_F986_7460
#endif

#ifdef __cplusplus
}
#endif


#ifdef __cplusplus
extern "C" {
#endif

/* {TERMINAL}.make */
void F986_7457 (EIF_REFERENCE Current)
{
	GTCX
	char *l_feature_name = "make";
	RTEX;
	RTCDT;
	RTSN;
	RTDA;
	RTLD;
	
	RTLI(1);
	RTLR(0,Current);
	RTLIU(1);
	RTLU (SK_VOID, NULL);
	RTLU (SK_REF, &Current);
	
	RTEAA(l_feature_name, 985, Current, 0, 0, 13452);
	RTSA(dtype);
	RTSC;
	RTME(dtype, 0);
	RTGC;
	RTDBGEAA(985, Current, 13452);
	RTIV(Current, RTAL);
	RTVI(Current, RTAL);
	RTRS;
	RTHOOK(1);
	RTDBGLE;
	RTMD(0);
	RTLE;
	RTLO(2);
	RTEE;
}

/* {TERMINAL}.set_non_blocking */
void F986_7458 (EIF_REFERENCE Current)
{
	GTCX
	char *l_feature_name = "set_non_blocking";
	RTEX;
	RTCDT;
	RTSN;
	RTDA;
	RTLD;
	
	RTLI(1);
	RTLR(0,Current);
	RTLIU(1);
	RTLU (SK_VOID, NULL);
	RTLU (SK_REF, &Current);
	
	RTEAA(l_feature_name, 985, Current, 0, 0, 13453);
	RTSA(dtype);
	RTSC;
	RTME(dtype, 1);
	RTDBGEAA(985, Current, 13453);
	RTIV(Current, RTAL);
	inline_F986_7458 ();
	RTVI(Current, RTAL);
	RTRS;
	RTHOOK(1);
	RTDBGLE;
	RTMD(1);
	RTLE;
	RTLO(2);
	RTEE;
}

/* {TERMINAL}.make_term_raw */
void F986_7461 (EIF_REFERENCE Current)
{
	GTCX
	char *l_feature_name = "make_term_raw";
	RTEX;
	RTCDT;
	RTSN;
	RTDA;
	RTLD;
	
	RTLI(1);
	RTLR(0,Current);
	RTLIU(1);
	RTLU (SK_VOID, NULL);
	RTLU (SK_REF, &Current);
	
	RTEAA(l_feature_name, 985, Current, 0, 0, 13456);
	RTSA(dtype);
	RTSC;
	RTME(dtype, 1);
	RTDBGEAA(985, Current, 13456);
	RTIV(Current, RTAL);
	inline_F986_7461 ();
	RTVI(Current, RTAL);
	RTRS;
	RTHOOK(1);
	RTDBGLE;
	RTMD(1);
	RTLE;
	RTLO(2);
	RTEE;
}

/* {TERMINAL}.get_char */
EIF_TYPED_VALUE F986_7460 (EIF_REFERENCE Current)
{
	GTCX
	char *l_feature_name = "get_char";
	RTEX;
	EIF_CHARACTER_8 Result = ((EIF_CHARACTER_8) 0);
	
	RTCDT;
	RTSN;
	RTDA;
	RTLD;
	
	RTLI(1);
	RTLR(0,Current);
	RTLIU(1);
	RTLU (SK_CHAR8, &Result);
	RTLU (SK_REF, &Current);
	
	RTEAA(l_feature_name, 985, Current, 0, 0, 13455);
	RTSA(dtype);
	RTSC;
	RTME(dtype, 1);
	RTDBGEAA(985, Current, 13455);
	RTIV(Current, RTAL);
	Result = inline_F986_7460 ();
	RTVI(Current, RTAL);
	RTRS;
	RTHOOK(1);
	RTDBGLE;
	RTMD(1);
	RTLE;
	RTLO(2);
	RTEE;
	{ EIF_TYPED_VALUE r; r.type = SK_CHAR8; r.it_c1 = Result; return r; }
}

void EIF_Minit986 (void)
{
	GTCX
}


#ifdef __cplusplus
}
#endif
