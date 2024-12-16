select
	s.StudentId,
	s.StudentName,
	s.GroupId 
from Students s, Marks m, Courses c
where s.StudentId = m.StudentId and m.CourseId = c.CourseId and m.Mark = :Mark and c.CourseName = :CourseName