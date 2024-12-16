select 
	StudentId,
	StudentName,
	Students.GroupId
from Students
natural join Marks
left join Plan
	on Marks.CourseId = Plan.CourseId
left join Lecturers
	on Plan.LecturerId = Lecturers.LecturerId
where Mark = :Mark and LecturerName = :LecturerName;