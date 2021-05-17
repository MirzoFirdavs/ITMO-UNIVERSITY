c, dp, ans = [], [], []
 
n , w = map(int,input().split())
 
for i in range(n):
   c.append(int(input()))
 
for i in range(1 << n):
   sm = 0
   for j in range(n):
      if(i & (1 << j) != 0):
         sm += c[j]
   dp.append([sm, i])
 
dp.sort()
 
k, cur = 0, 0
 
for i in range((1 << n) - 1, -1, -1):
   b = []
   if(dp[i][0] <= w and cur & dp[i][1] == 0):
      cur |= dp[i][1]
      for j in range(n):
        
         if(dp[i][1] & (1 << j) != 0):
            b.append(j + 1)
      if(len(b) != 0):
         ans.append(b)
  
#print(ans)
print(len(ans))
for i in range(len(ans)):
   print(len(ans[i]), *ans[i])
