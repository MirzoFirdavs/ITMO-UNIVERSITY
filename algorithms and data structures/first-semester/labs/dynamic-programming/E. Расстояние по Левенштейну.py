a = str(input())
b = str(input())
 
dp = [[10 ** 20 for i in range(len(b) + 1)] for j in range(len(a) + 1)]
 
for i in range(len(a) + 1):
    for j in range(len(b) + 1):
        if(i == 0 or j == 0):
            dp[i][j] = i + j
            continue
        if(i > 0 and j > 0 and a[i - 1] == b[j - 1]):
            dp[i][j] = dp[i - 1][j -1]
            continue
        if(i > 0 and j > 0):
            dp[i][j] = min(dp[i][j], 1 + dp[i - 1][j - 1]);
            dp[i][j] = min(dp[i][j], 1 + dp[i][j - 1]);
            dp[i][j] = min(dp[i][j], 1 + dp[i - 1][j]);
    
print(dp[-1][-1])
