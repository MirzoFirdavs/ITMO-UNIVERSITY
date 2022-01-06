n, m, k, s = map(int, input().split())
s -= 1
 
graph = []
 
for i in range(m):
    u, v, w = map(int, input().split())
    u -= 1
    v -= 1
    graph.append([u, v, w])
 
d = [[10000000000000000000001 for i in range(n)] for j in range(k + 1)]
 
d[0][s] = 0
 
for i in range(k):
    for j in range(m):
        if d[i][graph[j][0]] < 10000000000000000000001:
            d[i + 1][graph[j][1]] = min(d[i + 1][graph[j][1]], d[i][graph[j][0]] + graph[j][2])
 
for i in range(n):
    if d[k][i] == 10000000000000000000001:
        print(-1)
    else:
        print(d[k][i])