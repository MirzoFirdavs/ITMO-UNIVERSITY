select 
    s.StudentId,
    s.StudentName,
    g.GroupName
from Students as s, Groups as g 
where s.GroupId = g.GroupId and s.StudentId NOT IN(
    select 
        s.StudentId 
    from Students as s, Groups as g, Marks as m, Courses as c
    where s.GroupId = g.GroupId AND s.StudentId = m.StudentId AND m.CourseId = c.CourseId AND c.CourseName = :CourseName
);