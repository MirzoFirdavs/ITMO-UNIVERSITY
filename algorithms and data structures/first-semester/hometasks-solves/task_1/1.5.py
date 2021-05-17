def solve(a , b):
    ans = []

    n = len(a)
    m = len(b)

    i = n - 1
    j = m - 1
    cnt = 0

    while(i >= 0 and j >= 0):
        if(a[i] <= b[j]):
            j -= 1
        else:
            cnt += j + 1
            i -= 1
            
    print(cnt)
    

a = list(map(int,input().split()))
b = list(map(int,input().split()))

solve(a , b)
