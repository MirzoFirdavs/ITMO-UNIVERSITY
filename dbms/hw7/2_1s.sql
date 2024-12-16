delete from Students where StudentId in (
    select
        s.StudentId
    from Students as s
    natural join Plan p
    left join Marks m
        on s.StudentId = m.StudentId and m.CourseId = p.CourseId
    where Mark is null
)
