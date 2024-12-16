select r.RunId, s.SessionId, r.Letter, r.SubmitTime
from Sessions s natural join Runs r
where ContestId = :ContestId and Accepted = 1