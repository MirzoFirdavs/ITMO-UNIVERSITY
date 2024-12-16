select 
    StudentName,
    CourseName 
from (
    select
        s.StudentId,
        p.CourseId
    from Students as s, Plan as p
    where s.GroupId = p.GroupId
    except
    select
        s.StudentId,
        p.CourseId
    from Students as s, Plan as p, Marks as m
    where s.GroupId = p.GroupId and s.StudentId = m.StudentId and p.CourseId = m.CourseId
) as i, Students as s, Courses as c
where i.StudentId = s.StudentId and i.CourseId = c.CourseId;