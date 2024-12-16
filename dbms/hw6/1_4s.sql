select
	s.StudentId,
	s.StudentName,
	s.GroupId
from Students as s, Marks as m 
where s.StudentId = m.StudentId and m.Mark = :Mark and m.CourseId = :CourseId