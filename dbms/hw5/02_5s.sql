select 
	s.StudentId,
	s.StudentName,
	g.GroupName
from Students as s
inner join Groups as g
	on s.GroupId = g.GroupId
inner join Faculties as f
	on g.GroupFacultyId = f.FacultyId
inner join Lecturers as l 
	on f.DeanId = l.LecturerId
where l.LecturerName = :LecturerName;