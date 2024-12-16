update Students as s
set Marks = Marks + (select COUNT(Mark) from NewMarks nm where s.StudentId = nm.StudentId);