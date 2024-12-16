select 
	StudentId,
	StudentName,
	GroupId 
from Students
natural join Groups as g
inner join Faculties as f 
	on f.FacultyId = g.GroupFacultyId
where FacultyName = :FacultyName
except
select 
	StudentId,
	StudentName,
	GroupId 
from Students
natural join Marks
natural join Courses
where CourseName = :CourseName;