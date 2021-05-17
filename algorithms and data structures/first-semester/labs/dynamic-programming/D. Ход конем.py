dp = [[0 for i in range(10)] for j in range(101)]
 
n = int(input())
 
k = 10 ** 9
 
for i in range(10):
    dp[1][i] = 1
 
for i in range(2, n + 1):
    dp[i][0] = (dp[i - 1][4] + dp[i - 1][6]) % k
    dp[i][1] = (dp[i - 1][6] + dp[i - 1][8]) % k
    dp[i][2] = (dp[i - 1][9] + dp[i - 1][7]) % k
    dp[i][3] = (dp[i - 1][4] + dp[i - 1][8]) % k
    dp[i][4] = (dp[i - 1][3] + dp[i - 1][9] + dp[i - 1][0]) % k
    dp[i][6] = (dp[i - 1][1] + dp[i - 1][7] + dp[i - 1][0]) % k
    dp[i][7] = (dp[i - 1][2] + dp[i - 1][6]) % k
    dp[i][8] = (dp[i - 1][1] + dp[i - 1][3]) % k
    dp[i][9] = (dp[i - 1][2]+ dp[i - 1][4]) % k
 
ans = 0
 
for i in range(1, 10):
    if(i != 8):
        ans =(ans + dp[n][i]) % k
 
print(ans)
    
