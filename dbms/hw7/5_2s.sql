UPDATE Students as s
SET Debts = (
    SELECT COUNT(DISTINCT p.CourseId)
    FROM Plan p
    LEFT JOIN Marks m 
        ON p.CourseId = m.CourseId AND m.StudentId = :StudentId
    WHERE p.GroupId = s.GroupId AND m.CourseId IS NULL
) WHERE s.StudentId = :StudentId;