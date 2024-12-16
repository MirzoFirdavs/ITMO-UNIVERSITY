select distinct 
	GroupId, 
	CourseId 
from Marks 
cross join Students
except
select 
	GroupId,
	CourseId
from (
    select 
    	GroupId, 
    	s.StudentId,
    	CourseId 
    from Marks 
    cross join Students as s
    except
    select 
    	GroupId,
    	StudentId,
    	CourseId
    from Marks 
    natural join Students
) as a