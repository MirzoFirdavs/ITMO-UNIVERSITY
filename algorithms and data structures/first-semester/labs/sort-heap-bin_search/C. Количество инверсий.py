def count_inversion(lst):
    return merge_count_inversion(lst)[1]
 
def merge_count_inversion(lst):
    if (len(lst) <= 1):
        return lst, 0
    
    m = len(lst) // 2 
    l, a = merge_count_inversion(lst[:m])
    r, b = merge_count_inversion(lst[m:])
    res, c = merge_count_split_inversion(l, r)
    return res, (a + b + c)
 
def merge_count_split_inversion(l, r):
    res = []
    count = 0
    i, j = 0, 0
    left_len = len(l)
    right_len = len(r)
    while(i < left_len and j < right_len):
        if(l[i] <= r[j]):
            res.append(l[i])
            i += 1
        else:
            res.append(r[j])
            count += left_len - i
            j += 1
    res += l[i:]
    res += r[j:]
    return res, count
 
n = int(input())
lst = list(map(int,input().split()))
 
print(count_inversion(lst))
