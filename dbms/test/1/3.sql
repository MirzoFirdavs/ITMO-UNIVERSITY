select distinct r.RunId, r.SessionId, r.Letter, r.SubmitTime, r.Accepted
from Sessions s natural join Runs r
where TeamId = :TeamId and ContestId = :ContestId;