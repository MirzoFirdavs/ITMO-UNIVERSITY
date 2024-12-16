select 
    s.StudentId,
    s.StudentName,
    g.GroupName
from Students as s, Groups as g
where s.GroupId = g.GroupId and g.GroupId in (
    select 
        GroupId
    from Plan
    where CourseId in (
        select 
            CourseId
        from Courses
        where CourseName = :CourseName
    )
) and s.StudentId not in (
    select 
        StudentId
    from Marks
    where CourseId in (
        select 
            CourseId
        from Courses
        where CourseName = :CourseName
    )
);