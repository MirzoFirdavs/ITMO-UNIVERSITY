select 
    g.GroupName,
    c.CourseName
from Groups as g, Courses as c
where not exists(
    select 
        s.StudentId,
        s.GroupId
    from Students as s
    where s.GroupId = g.GroupId and s.StudentId not in (
        select 
            m.StudentId
        from Marks as m
        where m.CourseId = c.CourseId
    )
);