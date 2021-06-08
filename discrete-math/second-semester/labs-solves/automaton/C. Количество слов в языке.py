f = open("problem3.in", "r")
f1 = open("problem3.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
visited = [0 for i in range(100001)]
used = [0 for i in range(100001)]
x = [0 for i in range(100001)]
y = [[] for i in range(100001)]
z = [[] for i in range(100001)]
 
def dfs(v):
    if (used[v] or visited[v]):
        return
    visited[v] = 1
    used[v] = 1
    for i in range(len(z[v])):
        dfs(z[v][i])
    visited[v] = 0
 
def findCycle(v):
    if (not used[v]):
        return 0
    if (visited[v]):
        return 1
    visited[v] = 1
    for i in range(len(y[v])):
        if (findCycle(y[v][i])):
            visited[v] = 0
            return 1
    visited[v] = 0
    return 0
 
def ans(v):
    if (not used[v]):
        return 0;
    res = x[v]
    for i in range(len(y[v])):
        res = (res + ans(y[v][i])) % 1000000007
    return res
 
#n, m, k = map(int, input().split())
n, m, k = map(int, f.readline().split())
 
#k_th = list(map(int, input().split()))
k_th = list(map(int, f.readline().split()))
 
for i in range(k):
    x[k_th[i]] = 1
 
for i in range(m):
    #start, end, char = map(str, input().split())
    start, end, char = map(str, f.readline().split())
    start = int(start)
    end = int(end)
    y[start].append(end)
    z[end].append(start)
 
for i in range(k):
    dfs(k_th[i])
 
if (findCycle(1)):
    f1.write("-1")
    f.close()
    f1.close()
    #print(-1)
    exit()
 
f1.write(str(ans(1)))
#print(ans(1))
 
f.close()
f1.close()
