dp = [[0 for i in range(1 << 13)] for j in range(13)]
f = [[0 for i in range(1 << 13)] for j in range(13)]
road = []
 
n = int(input())
 
for i in range(n):
   road.append(list(map(int,input().split())))
 
for i in range(n):
    for j in range(1 << n):
        dp[i][j] = 10 ** 15
 
for i in range(n):
    dp[i][0] = 0
    
for x in range(1 << n):
    for i in range(n):
        if((x >> i & 1) == 1):
            for j in range(n):
                if(dp[j][x - (1 << i)] + road[i][j] < dp[i][x]):
                    dp[i][x] = dp[j][x - (1 << i)] + road[i][j]
                    f[i][x] = j
 
m_n = 10 ** 15
last = 0
tmp = 0
 
for i in range(n):
    if(dp[i][(1 << n) - 1] < m_n):
        m_n = dp[i][(1 << n) - 1]
        last = i
 
x = (1 << n) - 1
ans = []
 
for i in range(n):
    ans.append(last + 1)
    tmp = last
    last = f[last][x]
    x -= (1 << tmp)
 
print(m_n)
 
ans.reverse()
 
print(*ans)
