def _find_free_(graph):
    for i in range(len(graph)):
        if (len(graph[i]) > 0):
            return i
    return -1

n, m = map(int, input().split())

graph = [[] for i in range(n)]
ans = [0 for i in range(n + 1)]

for i in range(m):
    u, v = map(int, input().split())
    u -= 1
    v -= 1
    graph[u].append(v)
    graph[v].append(u)

def _recursive_(graph, flag):
    free = _find_free_(graph)
    if (free == -1):
        if (flag):
            ans[len(graph)] += 1
        else:
            ans[len(graph)] -= 1
    else:
        u = free
        v = graph[u][0]
        g1 = [[] for i in range(len(graph))]
        g2 = [[] for i in range(len(graph) - 1)]
        for i in range(len(graph)):
            for j in range(len(graph[i])):
                if ((i == u and graph[i][j] == v) or (i == v and graph[i][j] == u)):
                    continue
                g1[i].append(graph[i][j])
        bb = set()
        bb.add(u)
        for i in range(len(graph)):
            if (i == v):
                continue
            for j in range(len(graph[i])):
                if (i == u):
                    bb.add(graph[i][j])
                    if (graph[i][j] == v):
                        continue
                l = i
                r = graph[i][j]
                if (l > v):
                    l -= 1
                if (r > v):
                    r -= 1
                if (graph[i][j] == v):
                    g2[l].append(u)
                else:
                    g2[l].append(r)
        for i in range(len(graph[v])):
            j = graph[v][i]
            if (list(bb).count(j) == 0):
                if (j > v):
                    j -= 1
                g2[u].append(j)
        _recursive_(g1, flag)
        _recursive_(g2,  not flag)

_recursive_(graph, True)

print(n)

for i in range(n, -1, -1):
    print(ans[i], end = ' ')
                    
    


