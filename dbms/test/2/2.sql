select distinct TeamName 
from Teams t, (
    select distinct s.TeamId
    from Sessions s, Runs r
    where r.SessionId = s.SessionId and s.ContestId = :ContestId and r.Letter = :Letter and r.Accepted = 1  
) q
where t.TeamId = q.TeamId