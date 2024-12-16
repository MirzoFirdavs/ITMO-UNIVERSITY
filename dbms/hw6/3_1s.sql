select 
    s.StudentId, 
    p.CourseId 
from Students as s, Plan as p 
where s.GroupId = p.GroupId
union
select 
    m.StudentId,
    m.CourseId 
from Marks as m;