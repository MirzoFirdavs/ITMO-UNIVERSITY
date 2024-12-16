select
    s.StudentName,
    c.CourseName 
from Students as s, Plan as p, Marks as m, Courses as c
where s.GroupId = p.GroupId and s.StudentId = m.StudentId and p.CourseId = m.CourseId and m.CourseId = c.CourseId and m.Mark <= 2
group by
    s.StudentId,
    s.StudentName,
    c.CourseId,
    c.CourseName;