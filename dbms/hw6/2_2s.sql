select 
	s.StudentId, 
	s.StudentName, 
	g.GroupName 
from Students as s, Groups as g, Faculties as f 
where s.GroupId = g.GroupId and g.GroupFacultyId = f.FacultyId and f.FacultyName = :FacultyName