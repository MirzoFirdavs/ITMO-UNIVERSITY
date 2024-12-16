CREATE VIEW Debts AS
SELECT 
    s.StudentId,
    COUNT(DISTINCT p.CourseId) AS Debts
FROM Students s 
NATURAL JOIN Plan p
LEFT JOIN Marks m
    ON s.StudentId = m.StudentId and p.CourseId = m.CourseId
WHERE m.Mark IS NULL
GROUP BY s.StudentId