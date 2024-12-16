select distinct s.TeamId
from  Sessions s, Runs r
where s.ContestId = :ContestId and r.Accepted = 1 and s.SessionId = r.SessionId;