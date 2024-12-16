UPDATE Students as s
SET Debts = (
    SELECT COUNT(DISTINCT p.CourseId)
    FROM Plan p
    LEFT JOIN Marks m 
        ON p.CourseId = m.CourseId AND m.StudentId = s.StudentId
    WHERE p.GroupId = s.GroupId and m.CourseId IS NULL
) WHERE GroupId IN (
    SELECT GroupId FROM Groups WHERE GroupName = :GroupName
);