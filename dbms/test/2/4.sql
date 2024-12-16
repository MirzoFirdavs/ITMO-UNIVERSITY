select 
  p.ContestId, 
  p.Letter
from Problems p
where p.Letter not in (
  select 
    r.Letter
  from Teams t
  natural join Sessions s
  natural join Runs r
where s.ContestId = p.ContestId and t.UnivId = :UnivId and r.Accepted = 1);