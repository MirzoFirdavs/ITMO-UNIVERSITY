select
	s.StudentName,
	c.CourseName
from Students as s, Plan as p, Groups as g, Lecturers as l, Courses as c
where s.GroupId = p.GroupId and s.GroupId = g.GroupId and p.LecturerId = l.LecturerId and p.CourseId = c.CourseId and l.LecturerFacultyId != g.GroupFacultyId;