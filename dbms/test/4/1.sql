select SessionId, count(Letter) as Solved
from (
  select distinct SessionId, Letter
  from Runs
  where Accepted = 1
) DS
group by SessionId;