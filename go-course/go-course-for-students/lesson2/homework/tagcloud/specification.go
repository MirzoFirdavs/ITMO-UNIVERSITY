package tagcloud

import "sort"

// TagCloud aggregates statistics about used tags
type TagCloud struct {
	Capacity map[string]int
}

// TagStat represents statistics regarding single tag
type TagStat struct {
	Tag             string
	OccurrenceCount int
}

// New should create a valid TagCloud instance
// TODO: You decide whether this function should return a pointer or a value
func New() TagCloud {
	return TagCloud{
		make(map[string]int),
	}
}

// AddTag should add a tag to the cloud if it wasn't present and increase tag occurrence count
// thread-safety is not needed
// TODO: You decide whether receiver should be a pointer or a value
func (tc *TagCloud) AddTag(tag string) {
	tc.Capacity[tag]++
}

// TopN should return top N most frequent tags ordered in descending order by occurrence count
// if there are multiple tags with the same occurrence count then the order is defined by implementation
// if n is greater that TagCloud size then all elements should be returned
// thread-safety is not needed
// there are no restrictions on time complexity
// TODO: You decide whether receiver should be a pointer or a value
func (tc *TagCloud) TopN(n int) []TagStat {
	var res []TagStat
	var tagsArray []TagStat

	for key, value := range tc.Capacity {
		var cur = TagStat{
			key, value,
		}
		tagsArray = append(tagsArray, cur)
	}

	sort.SliceStable(tagsArray, func(i, j int) bool {
		return tagsArray[i].OccurrenceCount > tagsArray[j].OccurrenceCount
	})

	if n >= len(tagsArray) {
		for i := 0; i < len(tagsArray); i++ {
			res = append(res, tagsArray[i])
		}
		return res
	} else {
		for i := 0; i < n; i++ {
			res = append(res, tagsArray[i])
		}
		return res
	}
}
