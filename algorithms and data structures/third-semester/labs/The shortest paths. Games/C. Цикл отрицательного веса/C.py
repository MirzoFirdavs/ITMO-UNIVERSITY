n = int(input())
 
graph = []
 
for i in range(n):
    x = list(map(int, input().split()))
    for j in range(n):
        if x[j] == 100000:
            continue
        graph.append([i + 1, j + 1, x[j]])
 
d = [10000001 for i in range(n + 1)]
p = [-1 for i in range(n + 1)]
 
d[1] = 0
 
for i in range(1, n + 1):
    for j in range(len(graph)):
        if d[graph[j][1]] > d[graph[j][0]] + graph[j][2]:
            d[graph[j][1]] = d[graph[j][0]] + graph[j][2]
            p[graph[j][1]] = graph[j][0]
 
cycle = []
 
for i in range(len(graph)):
    if d[graph[i][1]] > d[graph[i][0]] + graph[i][2]:
        v = graph[i][1]
        for j in range(1, n + 1, 1):
            v = p[v]
        u = v
        while u != p[v]:
            v = p[v]
            cycle.append(v)
        cycle.append(u)
        cycle.reverse()
        print("YES")
        print(len(cycle))
        print(*cycle)
        exit()
print("NO")
