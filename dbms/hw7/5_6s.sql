UPDATE Students as s
SET Debts = (
    SELECT COUNT(DISTINCT p.CourseId)
    FROM Plan p
    LEFT JOIN Marks m 
        ON p.CourseId = m.CourseId AND m.StudentId = s.StudentId
    WHERE p.GroupId = s.GroupId AND m.CourseId IS NULL
), Marks = (
    SELECT COUNT(Mark)
    FROM Marks m
    WHERE m.StudentId = s.StudentId
);