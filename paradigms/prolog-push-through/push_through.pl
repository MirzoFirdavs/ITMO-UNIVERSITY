push_through(not(and(A, B)), or(A1, B1)) :- push_through(not(A), A1), push_through(not(B), B1), !.
push_through(not(or(A, B)), and(A1, B1)) :- push_through(not(A), A1), push_through(not(B), B1), !.
push_through(and(0, _), 0) :- !.
push_through(and(_, 0), 0) :- !.
push_through(or(1, _), 1) :- !.
push_through(or(_, 1), 1) :- !.
push_through(and(A, B), and(A1, B1)) :- push_through(A, A1), push_through(B, B1), !.
push_through(or(A, B), or(A1, B1)) :- push_through(A, A1), push_through(B, B1), !.
push_through(not(not(A)), A1) :- push_through(A, A1), !.
push_through(not(1), 0) :- !.
push_through(not(0), 1) :- !.
push_through(not(A), not(A1)) :- push_through(A, A1), !.
push_through(A, A).