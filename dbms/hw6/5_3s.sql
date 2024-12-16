select 
    s.StudentId
from Students as s
where not exists (
    select 
        p.CourseId
    from Plan as p
    where p.LecturerId in (
        select 
            l.LecturerId
        from Lecturers as l
        where l.LecturerName = :LecturerName
    ) and not exists (
        select 
            m.StudentId,
            m.CourseId
        from Marks as m
        where m.StudentId = s.StudentId and m.CourseId = p.CourseId
    )
);