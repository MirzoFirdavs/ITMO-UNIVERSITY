select distinct
	s.StudentId 
from Students as s
where s.StudentId not in(
	select distinct 
		s.StudentId 
	from Students as s, Marks as m, Plan as p, Lecturers as l
	where s.StudentId = m.StudentId and s.GroupId = p.GroupId and m.CourseId = p.CourseId and p.LecturerId = l.LecturerId and l.LecturerName = :LecturerName
);