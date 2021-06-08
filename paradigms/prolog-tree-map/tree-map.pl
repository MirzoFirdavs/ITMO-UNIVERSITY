node(K, V, H, L, R).

height(null, 0) :- !.
height(node(K, V, H, L, R), H).

bfactor(node(K, V, H, L, R), DH) :-
		height(L, LH),
		height(R, RH),
		DH is RH - LH.

fixheight(node(K, V, H, L, R), node(K, V, H1, L, R)) :-
		height(L, LH),
		height(R, RH),
		fixheight(LH, RH, H1).
fixheight(LH, RH, H1) :-
		LH > RH, !,
		H1 is LH + 1.
fixheight(LH, RH, H1) :-
		H1 is RH + 1.

rotateright(node(K, V, H, node(LK, LV, LH, LL, LR), R), Q) :-
		fixheight(node(K, V, H, LR, R), P),
		fixheight(node(LK, LV, LH, LL, P), Q).
		
rotateleft(node(K, V, H, L, node(RK, RV, RH, RL, RR)), P) :-
		fixheight(node(K, V, H, L, RL), Q),
		fixheight(node(RK, RV, RH, Q, RR), P).

balance(node(K, V, H, L, R), P) :-
		fixheight(node(K, V, H, L, R), node(K, V, H1, L, R)),
		bfactor(node(K, V, H1, L, R), DH),
		balance(DH, node(K, V, H1, L, R), P).
balance(DH, node(K, V, H, L, R), node(K, V, H, L, R1)) :-
		DH =:= 2, !,
		bfactor(R, DRH),
		rbalance(DRH, R, R1).
rbalance(DRH, R, R1) :-
		DRH < 0, !,
		rotateright(R, R1).
rbalance(DRH, R, R).
balance(DH, node(K, V, H, L, R), node(K, V, H, L1, R)) :-
		DH =:= -2, !,
		bfactor(L, DLH),
		lbalance(DLH, L, L1).
lbalance(DLH, L, L1) :-
		DLH > 0, !,
		rotateleft(L, L1).
lbalance(DLH, L, L).
balance(_, P, P).

map_get(node(K, V, _, L, R), K, V) :- !.

map_get(node(KN, VN, _, L, R), K, V) :-
		K < KN, !,
		map_get(L, K, V).

map_get(node(KN, VN, _, L, R), K, V) :-
		K > KN, !,
		map_get(R, K, V).

map_put(null, K, V, node(K, V, 1, null, null)) :- !.
map_put(node(K, VN, H, L, R), K, V, node(K, V, H, L, R)) :- !.
map_put(node(KN, VN, H, L, R), K, V, P) :-
		K < KN, !,
		map_put(L, K, V, Q),
		balance(node(KN, VN, H, Q, R), P).
map_put(node(KN, VN, H, L, R), K, V, P) :-
		K > KN, !,
		map_put(R, K, V, Q),
		balance(node(KN, VN, H, L, Q), P).

map_getLast(node(K, V, _, _, null), (K, V)) :- !.
map_getLast(node(K, V, H, L, R), PR) :-
		map_getLast(R, PR).

map_removeLast(null, null) :- !.
map_removeLast(node(_, _, _, L, null), L) :- !.
map_removeLast(node(K, V, H, L, R), Q) :-
		map_removeLast(R, P),
		balance(node(K, V, H, L, P), Q).
		
findmin(node(K, V, H, null, R), node(K, V, H, null, R)) :- !.
findmin(node(K, V, H, L, R), MIN) :- findmin(L, MIN).

removemin(node(_, _, _, null, R), R) :- !.
removemin(node(K, V, H, L, R), Q) :-
		removemin(L, P),
		balance(node(K, V, H, P, R), Q).

map_remove(node(KN, VN, H, L, R), K, P) :-
		K < KN, !,
		map_remove(L, K, Q),
		balance(node(KN, VN, H, Q, R), P).
map_remove(node(KN, VN, H, L, R), K, P) :-
		K > KN, !,
		map_remove(R, K, Q),
		balance(node(KN, VN, H, L, Q), P).

map_remove(null, _, null) :- !.
map_remove(node(K, _, _, L, null), K, L) :- !.
map_remove(node(K, _, _, L, R), K, P) :-
		findmin(R, node(MK, V, H, ML, MR)),
		removemin(R, R1),
		balance(node(MK, V, H, L, R1), P).

map_build([], null) :- !.
map_build([(K, V) | T], Result) :-
    map_build(T, Result1),
    map_put(Result1, K, V, Result).