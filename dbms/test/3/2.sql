DELETE FROM Runs WHERE SessionId IN (
    SELECT SessionId FROM Sessions S JOIN Teams T ON S.TeamId = T.TeamId WHERE T.UnivId = :UnivId)