select distinct SessionId
from Sessions
except
select q.SessionId
from (
  select SessionId, ContestId, Letter
  from Sessions
  natural join Problems
  except
  select SessionId, ContestId, Letter
  from Sessions
  natural join Runs
) q