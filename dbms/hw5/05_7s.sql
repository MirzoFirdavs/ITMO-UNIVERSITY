select 
  StudentName,
  CourseName
from Students
natural join Plan as p
inner join Faculties as f 
	on p.LecturerId = f.DeanId
natural join Courses;
