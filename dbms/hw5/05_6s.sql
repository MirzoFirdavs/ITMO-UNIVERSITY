select 
  StudentName,
  CourseName
from Students
natural join Groups
natural join Plan
natural join Courses
natural join Lecturers
where LecturerFacultyId != GroupFacultyId;
