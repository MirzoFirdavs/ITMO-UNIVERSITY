create view AllMarks as
select
    s.StudentId,
    count(a.Mark) as Marks
from Students s
left join (
    select
        StudentId,
        CourseId,
        Mark
    from Marks
    union all
    select
        StudentId,
        CourseId,
        Mark
    from NewMarks
) as a
on s.StudentId = a.StudentId 
group by s.StudentId