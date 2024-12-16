-- СУБД: PostgreSQL
-- Триггер SameMarks для проверки одинаковых оценок у студентов группы
CREATE OR REPLACE FUNCTION same_marks() RETURNS TRIGGER AS $$
DECLARE
    mark_count INT;
    student_count INT;
BEGIN
    SELECT COUNT(DISTINCT Mark) INTO mark_count 
    FROM Marks 
    WHERE CourseId = NEW.CourseId 
    AND StudentId IN (SELECT StudentId FROM Students WHERE GroupId = (SELECT GroupId FROM Students WHERE StudentId = NEW.StudentId));

    SELECT COUNT(DISTINCT StudentId) INTO student_count 
    FROM Students 
    WHERE GroupId = (SELECT GroupId FROM Students WHERE StudentId = NEW.StudentId);

    IF mark_count > 1 THEN
        RAISE EXCEPTION 'All students in the same group must have the same marks for the same course';
    END IF;

    RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE TRIGGER SameMarks
AFTER INSERT OR UPDATE ON Marks
FOR EACH ROW
EXECUTE FUNCTION same_marks();