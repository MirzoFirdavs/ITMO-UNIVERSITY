UPDATE Students as s
SET Marks = (
    SELECT COUNT(Mark)
    FROM Marks m
    WHERE m.StudentId = s.StudentId
) WHERE GroupId IN (
    SELECT GroupId
    FROM Groups g
    LEFT JOIN Faculties f 
        ON g.GroupFacultyId = f.FacultyId
    WHERE f.FacultyName = :FacultyName
);
