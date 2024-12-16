CREATE VIEW StudentDebts AS
SELECT
    s.StudentId,
    COALESCE(q.Debts, 0) AS Debts
FROM Students s
LEFT JOIN (
    SELECT
        s1.StudentId,
        COUNT(DISTINCT p.CourseId) AS Debts
    FROM Students as s1
    NATURAL JOIN Plan p
    LEFT JOIN Marks m 
        ON p.CourseId = m.CourseId and s1.StudentId = m.StudentId
    WHERE m.Mark IS NULL
    GROUP BY s1.StudentId
) q
ON s.StudentId = q.StudentId;