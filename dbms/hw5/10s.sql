select 
    s.StudentId as StudentId,
    count(distinct p.CourseId) as Total,
    count(distinct m.CourseId) as Passed,
    count(distinct p.CourseId) - count(distinct m.CourseId) as Failed
from Students as s
left join Plan as p 
    on p.GroupId = s.GroupId
left join Marks as m 
    on m.StudentId = s.StudentId and m.CourseId = p.CourseId
group by 
    s.StudentId