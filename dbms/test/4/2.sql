select 
    q.TeamId,
    count(1) as Solved
from (
    select distinct
        TeamId, Letter, ContestId
    from Runs
    natural join Sessions
    where Accepted = 1
) q
group by q.TeamId