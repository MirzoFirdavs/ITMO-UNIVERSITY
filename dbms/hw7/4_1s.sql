update Students 
set Marks = (select COUNT(Mark) from Marks where StudentId = :StudentId) 
WHERE StudentId = :StudentId;
