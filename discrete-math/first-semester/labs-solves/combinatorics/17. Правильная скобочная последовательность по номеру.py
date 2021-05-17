f = open("num2brackets.in", "r")
f1 = open("num2brackets.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
#n, k = map(int, input().split())
n, k = map(int, f.readline().split())
 
 
d = [[0 for i in range(n + 2)] for j in range(2 * n + 1)]
 
d[0][0] = 1
 
for i in range(2 * n):
    for j in range(n + 2):
        if (j + 1 <= n + 1):
            d[i + 1][j + 1] += d[i][j]
        if(j > 0):
            d[i + 1][j - 1] += d[i][j]
 
ans = ""
di = 0
 
for i in range(2 * n):
    if(d[2 * n - i - 1][di + 1] > k):
        ans += "("
        di += 1
    else:
        k -= d[2 * n - i - 1][di + 1]
        ans += ")"
        di -= 1
 
#print(ans)
f1.write(ans)
 
f.close()
f1.close()
