n, m, s = map(int, input().split())
s -= 1
 
graph = [[] for i in range(n)]
edges = []
 
for i in range(m):
    u, v, w = map(int, input().split())
    u -= 1
    v -= 1
    graph[u].append(v)
    edges.append([u, v, w])
 
d = [10000000000000000000001 for i in range(n)]
d[s] = 0
 
for i in range(n):
    for j in range(len(edges)):
        if d[edges[j][0]] < 10000000000000000000001 and d[edges[j][1]] > d[edges[j][0]] + edges[j][2]:
            d[edges[j][1]] = max(-10000000000000000000001, d[edges[j][0]] + edges[j][2])
 
used = [0 for i in range(n)]
 
def dfs(u):
    used[u] = 1
    for i in range(len(graph[u])):
        if used[graph[u][i]] == 0:
            dfs(graph[u][i])
 
for i in range(n):
    for j in range(len(edges)):
        if d[edges[j][0]] < 10000000000000000000001 and d[edges[j][1]] > d[edges[j][0]] + edges[j][2] and used[edges[j][1]] == 0:
            dfs(edges[j][1])
 
for i in range(n):
    if d[i] == 10000000000000000000001:
        print('*')
    elif used[i]:
        print('-')
    else:
        print(d[i])