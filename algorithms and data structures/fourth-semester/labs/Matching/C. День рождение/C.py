def dfs1(i, edges, r, used):
    if used[i]:
        return False
    used[i] = True
    for j in edges[i]:
        if r[j] == -1 or dfs1(r[j], edges, r, used):
            r[j] = i
            return True
    return False
 
 
def dfs2(i, edges, r, used, right_used):
    if used[i]:
        return False
    used[i] = True
    for j in edges[i]:
        if not right_used[j]:
            right_used[j] = True
        dfs2(r[j], edges, r, used, right_used)
    return False
 
 
def solve():
    k = int(input())
    for i in range(k):
        m, n = map(int, input().split())
        edges = [set() for _ in range(m)]
        for j in range(m):
            lst = list(map(int, input().split()))
            for k in range(len(lst) - 1):
                edges[j].add(lst[k] - 1)
        reversed_edges = [[] for _ in range(m)]
        for j in range(m):
            for g in range(n):
                if edges[j].__contains__(g):
                    continue
                reversed_edges[j].append(g)
 
        matching = [-1 for _ in range(n)]
        used = [False for _ in range(m)]
        for j in range(m):
            if dfs1(j, reversed_edges, matching, used):
                used = [False for _ in range(m)]
        free_vertex = set()
        for j in range(m):
            free_vertex.add(j)
        for j in matching:
            if free_vertex.__contains__(j):
                free_vertex.remove(j)
        right_used = [False for _ in range(n)]
        used = [False for _ in range(m)]
        for j in free_vertex:
            dfs2(j, reversed_edges, matching, used, right_used)
        boys = []
        girls = []
        for j in range(m):
            if used[j]:
                boys.append(j + 1)
        for j in range(n):
            if not right_used[j]:
                girls.append(j + 1)
        print(len(boys) + len(girls))
        print(len(boys), len(girls))
        print(*boys)
        print(*girls)
        print()

solve()