-- СУБД: PostgreSQL

-- Триггер NoExtraMarks для проверки курсов
CREATE OR REPLACE FUNCTION no_extra_marks() RETURNS TRIGGER AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM Plan 
        WHERE GroupId = (SELECT GroupId FROM Students WHERE StudentId = NEW.StudentId)
        AND CourseId = NEW.CourseId
    ) THEN
        RAISE EXCEPTION 'Student cannot have marks for courses not in their plan';
    END IF;
    RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER NoExtraMarks
BEFORE INSERT OR UPDATE ON Marks
FOR EACH ROW
EXECUTE FUNCTION no_extra_marks();