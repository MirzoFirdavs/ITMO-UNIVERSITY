select 
  min(StudentName) as StudentName,
  min(CourseName) as CourseName
from Students
natural join Plan
natural join Courses
natural join Lecturers as l
inner join Faculties as f
  on l.LecturerFacultyId = f.FacultyId
where FacultyName = :FacultyName
group by 
  StudentId, 
  CourseId;