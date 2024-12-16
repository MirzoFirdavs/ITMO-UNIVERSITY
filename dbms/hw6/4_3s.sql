select
    s.StudentName,
    c.CourseName
from (
    select
        s.StudentId,
        p.CourseId
    from Students as s, Plan as p
    where s.GroupId = p.GroupId
    except
    select
        m.StudentId,
        m.CourseId
    from Marks as m
    where m.Mark > 2
) as d, Students as s, Courses as c
where d.StudentId = s.StudentId and d.CourseId = c.CourseId;