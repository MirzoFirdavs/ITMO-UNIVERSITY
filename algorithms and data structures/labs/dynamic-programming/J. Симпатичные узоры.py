n, m = 0, 0
 
dp = [[0 for i in range(1 << 10)] for j in range(1 << 10)]
M = [[0 for i in range(1 << 10)] for j in range(31)]
 
def boolean(x, y):
   for i in range(1, n):
      if((x & (1 << i)) == (y & (1 << i)) and (x & (1 << (i - 1))) == (y &(1 << (i - 1))) and (x &(1 << i)) == ((x & (1 << (i - 1))) << 1)):
         return 0
   return 1
 
n, m = map(int,input().split())
 
if(n > m):
   n, m = m, n
 
for i in range(1 << n):
   for j in range(i, 1 << n):
      if(boolean(i, j)):
         dp[i][j] = 1
         dp[j][i] = 1
 
for i in range(1 << n):
   M[0][i] = 1
 
for k in range(1, m):
   for i in range(1 << n):
      for j in range(1 << n):
         M[k][i] += M[k - 1][j] * dp[j][i]
 
ans = 0
 
for i in range(1 << n):
   ans += M[m - 1][i]
 
print(ans)
