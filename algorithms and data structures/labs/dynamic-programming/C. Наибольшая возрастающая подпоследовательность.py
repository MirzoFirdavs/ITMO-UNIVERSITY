n = int(input())
lst = list(map(int,input().split()))
 
dp = [1 for i in range(n)]
f = [0 for i in range(n)]
 
mx = 0
 
for i in range(n):
    idx = i
    for j in range(i):
        if(lst[j] < lst[i] and dp[j] + 1 > dp[i]):
            dp[i] = dp[j] + 1
            idx = j
    f[i] = idx
    if(dp[i] > dp[mx]):
        mx = i
 
ans = []
 
while(f[mx] != mx):
    ans.append(lst[mx])
    mx = f[mx]
 
ans.append(lst[mx])
ans.reverse()
 
print(len(ans))
print(*ans)
