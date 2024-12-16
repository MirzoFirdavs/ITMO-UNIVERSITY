UPDATE Students as s
SET Marks = (
    SELECT COUNT(distinct m.CourseId)
    FROM Marks m
    WHERE m.StudentId = s.StudentId AND m.Mark IS NOT NULL
);
