-- СУБД: PostgreSQL
-- Триггер PreserveMarks для предотвращения уменьшения оценки
CREATE OR REPLACE FUNCTION preserve_marks() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.Mark < OLD.Mark THEN
        RAISE EXCEPTION 'Cannot reduce a students mark';
    END IF;
    RETURN NEW;
END;
$$
 LANGUAGE plpgsql;

CREATE TRIGGER PreserveMarks
BEFORE UPDATE ON Marks
FOR EACH ROW
EXECUTE FUNCTION preserve_marks();