select 
  StudentId
from Students
natural join Plan
natural join Marks
natural join Lecturers
where LecturerName = :LecturerName;