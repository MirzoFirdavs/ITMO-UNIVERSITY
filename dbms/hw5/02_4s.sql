select 
	StudentId,
	StudentName,
	GroupName
from Students
natural join Groups as g
left join Faculties as f
	ON g.GroupFacultyId = f.FacultyId
WHERE f.FacultyName = :FacultyName;
