CREATE VIEW StudentMarks AS
SELECT 
    s.StudentId,
    COUNT(m.Mark) AS Marks
FROM 
    Students s
LEFT JOIN 
    Marks m ON s.StudentId = m.StudentId
GROUP BY 
    s.StudentId;