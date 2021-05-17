import random
 
def gen(n):
   #print("======================")
   #n = int(input())
 
   dp = [[0 for i in range(102)] for j in range(102)]
   c = [0 for i in range(102)]
 
   for i in range(1, n + 1):
       k = int(input())
       c[i] = k
       #c[i] = random.randint(1, 301)
 
 
   for i in range(n + 1):
       for j in range(i + 1, n + 1):
          dp[i][j] = 10 ** 20
 
   dp[1][0] = c[1]
 
   if(c[1] > 100):
       dp[1][1] = c[1]
   else:
       dp[1][1] = 10 ** 20
 
   for i in range(2, n + 1):
       dp[i][0] = 10 ** 20
 
   for i in range(2, n + 1):
       for j in range(n + 1):
           if(c[i] <= 100):
               dp[i][j] = min(dp[i - 1][j] + c[i], dp[i - 1][j + 1])
           elif(j > 0):
               dp[i][j] = min(dp[i - 1][j - 1] + c[i], dp[i - 1][j + 1])
           else:
               dp[i][j] = dp[i - 1][j + 1]
 
           if(dp[i][j] > 10 ** 20):
               dp[i][j] = 10 ** 20
    
   col = 0
 
   while (col < n and dp[n][col] >= dp[n][col + 1] and dp[n][col + 1] > 0):
           col += 1
 
   print(dp[n][col])
 
   ans = []
 
   cur = n
   ans1 = str(col) + " "
 
   while(cur > 0):
       if(dp[cur][col] == dp[cur -1][col + 1]):
           ans.append(cur)
           cur -= 1
           col += 1
           continue
       cur -= 1
       if(c[cur + 1] > 100):
           col -= 1
 
   ans1 += str(len(ans))
 
   print(ans1)
 
   for i in range(len(ans) - 1, -1, -1):
       print(ans[i])
 
n = int(input())
gen(n)
   
