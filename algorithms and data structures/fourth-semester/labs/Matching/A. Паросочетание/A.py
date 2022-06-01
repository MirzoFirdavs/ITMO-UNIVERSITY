used = []
result = []
edges = []
 
 
def dfs(v):
    if used[v]:
        return False
    used[v] = True
    for u in edges[v]:
        if result[u] == -1 or dfs(result[u]):
            result[u] = v
            return True
    return False
 
 
n, m = map(int, input().split())
 
edges = [[] for i in range(n)]
used = [False for j in range(n)]
result = [-1 for k in range(m)]
 
for i in range(n):
    lst = list(map(int, input().split()))
    for j in range(len(lst) - 1):
        edges[i].append(lst[j] - 1)
 
ans = 0
 
for i in range(n):
    if dfs(i):
        ans += 1
        used = [False for j in range(n)]
 
print(ans)
 
for i in range(m):
    if result[i] == -1:
        continue
    print(result[i] + 1, i + 1)