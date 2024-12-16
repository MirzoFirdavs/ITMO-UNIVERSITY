select 
    s.StudentId,
    s.StudentName,
    g.GroupName
from Students as s, Groups as g 
where s.GroupId = g.GroupId and s.StudentId not in(
    select 
        StudentId 
    from Marks
    where CourseId = :CourseId
);