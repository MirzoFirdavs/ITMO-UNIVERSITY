select t.TeamName
from Teams t
where t.TeamId not in (
    select s.TeamId
    from Sessions s
    natural join Runs r
    where s.ContestId = :ContestId
);