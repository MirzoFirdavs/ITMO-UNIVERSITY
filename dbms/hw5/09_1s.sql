select
	avg(cast(Mark as double)) as AvgMark
from Marks
where StudentId = :StudentId;