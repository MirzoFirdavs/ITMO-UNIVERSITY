insert into Sessions (TeamId, ContestId, Start)
select TeamId, :ContestId, current_timestamp
from Teams
where TeamId not in (
select TeamId
from Sessions
where ContestId = :ContestId)