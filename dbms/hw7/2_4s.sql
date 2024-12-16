delete from Students where StudentId not in (
    SELECT 
        s.StudentId
    from Students as s
    natural join Plan p
    left join Marks m
        on s.StudentId = m.StudentId and m.CourseId = p.CourseId
    where m.Mark is null
    group by s.StudentId
    having count(distinct p.CourseId) > 3
);