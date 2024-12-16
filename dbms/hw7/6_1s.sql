INSERT INTO Marks (StudentId, CourseId, Mark)
SELECT nm.StudentId, nm.CourseId, nm.Mark
FROM NewMarks nm
LEFT JOIN Marks m ON nm.StudentId = m.StudentId AND nm.CourseId = m.CourseId
WHERE m.Mark IS NULL;