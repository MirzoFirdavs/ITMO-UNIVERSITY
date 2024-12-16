select distinct
	s.StudentId
from Students as s, Lecturers as l, Plan as p, Marks as m
where s.StudentId = m.StudentId and s.GroupId = p.GroupId and l.LecturerId = p.LecturerId and p.CourseId = m.CourseId and l.LecturerName = :LecturerName;