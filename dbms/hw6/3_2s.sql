select 
    StudentName, 
    CourseName 
from (
    select 
        s.StudentId, 
        p.CourseId 
    from Students as s, Plan as p 
    where s.GroupId = p.GroupId
    union
    select 
        m.StudentId,
        m.CourseId
    from Marks m
) as info, Students as s, Courses as c
where info.StudentId = s.StudentId and info.CourseId = c.CourseId;

