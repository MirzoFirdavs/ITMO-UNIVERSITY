def solve(a , b):
    i = 0
    j = 0
    cnt = 0

    ans = []

    n = len(a)
    m = len(b)

    while(i < n and j < m):
        if(a[i] > b[j]):
            j += 1
        elif(a[i] < b[j]):
            i += 1
        else:
            cnt += 1
            ans.append([i + 1, j + 1])
            j += 1

    print(cnt)
    print(ans)

a = list(map(int,input().split()))
b = list(map(int,input().split()))

solve(a , b)
