select 
	s.StudentId, 
	s.StudentName,
	s.GroupId 
from Students as s, Groups as g 
where s.GroupId = g.GroupId and GroupName = :GroupName