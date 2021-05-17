n, k = map(int,input().split())
 
x = [0, 0]
 
lst = list(map(int,input().split()))
 
for i in range(len(lst)):
    x.append(lst[i])
 
x.append(0)
 
f = [0 for i in range(n + 1)]
dp = [0 for i in range(n + 1)]
 
mx = 1
 
for i in range(2, n + 1):
    if(mx < i - k or dp[i - 1] > dp[mx]):
        mx = i - 1
        for j in range(i - 2, i - k - 1, -1):
            if(j >= 1 and dp[j] >= dp[mx]):
                mx = j
    dp[i] = dp[mx] + x[i]
    f[i] = mx
 
ans = []
end = n
 
while(f[end] != 1):
    ans.append(end)
    end = f[end]
 
print(dp[-1])
 
ans.append(end)
 
print(len(ans))
 
ans.append(1)
 
ans.reverse()
 
print(*ans)
