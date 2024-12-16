select
    s.StudentId,
    s.StudentName,
    g.GroupName
from Students as s, Groups as g, Faculties as f, Lecturers as l
where s.GroupId = g.GroupId and g.GroupFacultyId = f.FacultyId and f.DeanId = l.LecturerId and l.LecturerName = :LecturerName;
