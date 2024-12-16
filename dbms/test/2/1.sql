select distinct u.UnivName
from Universities u, Teams t, Sessions s
where s.ContestId = :ContestId
    and u.UnivId = t.UnivId and t.TeamId = s.TeamId;