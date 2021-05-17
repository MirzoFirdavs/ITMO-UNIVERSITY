n, m = map(int,input().split())
 
c = []
 
for i in range(n):
    c.append(list(map(int, input().split())))
 
road = [["" for i in range(m)] for j in range(n)]
dp = [[0 for i in range(m)] for j in range(n)]
 
dp[0][0] = c[0][0]
 
for i in range(1, n):
    dp[i][0] = dp[i - 1][0] + c[i][0]
    road[i][0] = "D"
 
for i in range(1, m):
    dp[0][i] = dp[0][i - 1] + c[0][i]
    road[0][i] = "R"
 
for i in range(1, n):
    for j in range(1, m):
        if(dp[i - 1][j] > dp[i][j - 1]):
            road[i][j] = "D"
            dp[i][j] = c[i][j] + dp[i - 1][j]
            continue
        road[i][j] = "R"
        dp[i][j] = c[i][j] + dp[i][j - 1]
        
print(dp[-1][-1])
i = n - 1
j = m - 1
ans = []
while (i > 0 or j > 0):
    ans.append(road[i][j])
    if(road[i][j] == 'D'):
        i -= 1
        continue
        
    j -= 1
    
ans.reverse()
 
print("".join(ans))
