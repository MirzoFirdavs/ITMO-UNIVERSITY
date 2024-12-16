select
	StudentName,
	SumMark
from Students
left join (
	select
		StudentId,
		sum(Mark) as SumMark
    from Marks
    group by StudentId
) as MarkSum
	on Students.StudentId = MarkSum.StudentId;