select
	GroupName,
	SumMark
from Groups
left join (
	select
		GroupId,
		sum(Mark) as SumMark
    from Marks
    natural join Students
    group by GroupId
) as m
	on Groups.GroupId = m.GroupId;