select
	StudentName,
	AvgMark
from Students
left join (
	select
		StudentId,
		avg(cast(Mark as double)) as AvgMark
    from Marks
    group by StudentId
) as MarkSum
	on Students.StudentId = MarkSum.StudentId;