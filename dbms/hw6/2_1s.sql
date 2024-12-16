select 
	s.StudentId, 
	s.StudentName, 
	g.GroupName 
from Students as s, Groups as g 
where s.GroupId = g.GroupId