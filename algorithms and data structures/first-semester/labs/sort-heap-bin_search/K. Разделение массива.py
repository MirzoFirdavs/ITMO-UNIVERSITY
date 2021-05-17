def check(lst , m , k , n):
    c = 0
    k1 = 0
    for i in range(n):
        if(lst[i] > m):
            return 0
        if(c + lst[i] > m):
            k1 += 1
            c = 0
        c += lst[i]
    k1 += 1
    return (k1 <= k)
def solve():
    n , k = map(int,input().split())
    lst = list(map(int,input().split()))
    l = 0
    r = sum(lst)
    while(r - l > 0):
        m = (l + r) // 2
        if(check(lst , m , k , n)):
            r = m
        else:
            l = m + 1
    print(r)
    
solve()
