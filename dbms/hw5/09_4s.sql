select
	GroupName,
	AvgAvgMark
from Groups
left join (
	select
		GroupId, 
		avg(cast(AvgMark as double)) as AvgAvgMark
    from (
    	select
            StudentId,
            avg(cast(Mark as double)) as AvgMark
        from Marks
        group by StudentId
    ) as StudentAvgMark
    natural join Students
    group by GroupId
) as GroupAvgMark
	on Groups.GroupId = GroupAvgMark.GroupId;