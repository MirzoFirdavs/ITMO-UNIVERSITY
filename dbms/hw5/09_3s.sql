select
	GroupName,
	AvgMark
from Groups as g
left join (
	select
		GroupId,
		avg(cast(Mark as double)) as AvgMark
    from Marks
    natural join Students
    group by GroupId
) as m
	on g.GroupId = m.GroupId;