divisible(X, Y) :-
    0 is mod(X, Y).

smaller(A, B) :-
    A < B + 1.

prime(N) :-
	N > 0,
	not composite(N).

init(MAX_N) :-
    jump(2, MAX_N).

delete(INDEX, MAX_N, INCREMENT) :-
	assert(composite(INDEX)),
	smaller(INDEX, MAX_N),
	INDEX1 is INDEX + INCREMENT,
	delete(INDEX1, MAX_N, INCREMENT).

jump(INDEX, RANGE) :-
    not(composite(INDEX)),
    smaller(INDEX, RANGE),
    INDEX1 is INDEX * INDEX,
    smaller(INDEX1, RANGE),
    delete(INDEX1, RANGE, INDEX).

jump(INDEX, MAX_N) :-
    smaller(INDEX, MAX_N),
    INDEX1 is INDEX + 1,
    jump(INDEX1, MAX_N).

divisors_per_number(N, [], _, N):- !.

divisors_per_number(NUMBER, [HEAD | TAIL], PREV, RESULT):-
    not composite(HEAD),
    smaller(PREV, HEAD),
    NEXT is NUMBER * HEAD,
	divisors_per_number(NEXT, TAIL, HEAD, RESULT).

number_per_divisors(NUMBER, D, [NUMBER]):-
	D > sqrt(NUMBER).

number_per_divisors(NUMBER, D, [HEAD | TAIL]):-
	divisible(NUMBER, D),
	HEAD is D,
	NEXT is div(NUMBER, D),
	number_per_divisors(NEXT, D, TAIL).

number_per_divisors(NUMBER, 2, RANGE):-
	not divisible(NUMBER, 2),
	number_per_divisors(NUMBER, 3, RANGE).

number_per_divisors(NUMBER, D, RANGE):-
	not divisible(NUMBER, D),
	D1 is D + 2,
	number_per_divisors(NUMBER, D1, RANGE).

prime_divisors(NUMBER, DIVISORS) :-
	integer(NUMBER),
	NUMBER - 1 > 0,
	number_per_divisors(NUMBER, 2, DIVISORS), !.

prime_divisors(NUMBER, DIVISORS) :-
	divisors_per_number(1, DIVISORS, 1, NUMBER).

merge([], _, []) :- !.
merge(_, [], []).
merge([H | T1], [H | T2], [H | T]) :- merge(T1, T2, T).
merge([H1 | T1], [H2 | T2], T) :- H1 < H2, !, merge(T1, [H2 | T2], T).
merge([H1 | T1], [H2 | T2], T) :- H2 < H1, !, merge([H1 | T1], T2, T).

gcd(A, B, GCD) :- prime_divisors(A, LA),
                  prime_divisors(B, LB),
                  merge(LA, LB, R),
                  divisors_per_number(1, R, 1, GCD).