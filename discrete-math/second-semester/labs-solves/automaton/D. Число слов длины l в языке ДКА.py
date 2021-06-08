f = open("problem4.in", "r")
f1 = open("problem4.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
#n, m, k, l = map(int, input().split())
n, m, k, l = map(int, f.readline().split())
 
#k_th = list(map(int, input().split()))
k_th = list(map(int, f.readline().split()))
 
 
ways = [[] for i in range(n + 1)]
x = [1]
dp = [0 for i in range(n + 1)]
dp[1] = 1
 
for i in range(m):
    #start, end, char = map(str, input().split())
    start, end, char = map(str, f.readline().split())
    start = int(start)
    end = int(end)
    ways[start].append(end)
 
for i in range(l):
    y = []
    b = [0 for i in range(n + 1)]
    for i in x:
        for j in range(len(ways[i])):
            t = ways[i][j]
            b[t] = (b[t] + dp[i]) % 1000000007
            y.append(t)
    y = list(set(y))
    x = y[:]
    dp = b[:]
 
ans = 0
 
for i in range(k):
    ans = (ans + dp[k_th[i]]) % 1000000007
 
f1.write(str(ans))
print(ans)
 
f.close()
f1.close()
