select 
  min(StudentName) as StudentName,
  min(CourseName) as CourseName
from Students
natural join Plan
natural join Courses
group by 
  StudentId, 
  CourseId;