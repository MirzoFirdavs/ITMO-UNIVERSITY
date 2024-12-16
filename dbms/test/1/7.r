pi{UnivName}(
    (pi{TeamId, ContestId}(Teams nj Contests) 
    diff 
    pi{TeamId, ContestId}(sigma{Accepted = 1}(Runs) nj Sessions)
    ) nj Teams nj Universities
)