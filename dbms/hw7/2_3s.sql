delete from Students where StudentId in (
    select 
        s.StudentId
    from Students as s
    natural join Plan p
    natural join Groups g
    left join Faculties f
        on g.GroupFacultyId = f.FacultyId
    left join Marks m
        on s.StudentId = m.StudentId and m.CourseId = p.CourseId
    where Mark is null and f.FacultyName = :FacultyName
    group by s.StudentId
    having count(distinct p.CourseId) >= 2
);