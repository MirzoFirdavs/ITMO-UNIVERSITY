s = str(input())
 
n = len(s)
 
d = [[0 for i in range(101)] for j in range(101)]
b = [["" for i in range(101)] for j in range(101)]
 
for i in range(1, n):
   for j in range(n - i):
      k = i + j
      for x in range(j, k + 1):
         if(d[j][x] + d[x + 1][k] > d[j][k]):
            d[j][k] = d[j][x] + d[x + 1][k]
            b[j][k] = b[j][x] + b[x + 1][k]
      if((s[j] == "(" and s[k] == ")") or (s[j] == "[" and s[k] == "]") or (s[j] == "{" and s[k] == "}")):
         if(d[j + 1][k - 1] + 2 > d[j][k]):
            d[j][k] = d[j + 1][k - 1] + 2
            b[j][k] = s[j] + b[j + 1][k - 1] + s[k]
 
print(b[0][n - 1])
