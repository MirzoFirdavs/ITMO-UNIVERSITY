n = int(input())
 
graph = []
 
for i in range(n):
    graph.append(list(map(int, input().split())))
 
for i in range(n):
    for j in range(n):
        for g in range(n):
            graph[j][g] = min(graph[j][g], graph[j][i] + graph[i][g])
 
for i in range(n):
    print(*graph[i])