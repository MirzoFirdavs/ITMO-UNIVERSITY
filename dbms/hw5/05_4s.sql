select 
  min(StudentName) as StudentName,
  min(CourseName) as CourseName
from Students
natural join Plan
natural join Courses
natural join Lecturers
where LecturerName = :LecturerName
group by 
  StudentId, 
  CourseId;