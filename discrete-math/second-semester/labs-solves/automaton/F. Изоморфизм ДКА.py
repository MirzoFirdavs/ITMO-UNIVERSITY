f = open("isomorphism.in", "r")
f1 = open("isomorphism.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
t1 = []; t2 = []; visited = []; g1 = []; g2 = []
 
def dfs(v1, v2):
    visited[v1] = 1
    if (t1[v1] != t2[v2]):
        return 0
    res = 1
    for i in range(26):
        z = g1[v1][i]
        x = g2[v2][i]
        if ((z == 0 and x != 0) or (z != 0 and x == 0)):
            return 0
        if (visited[z] == 0):
            res &= dfs(z, x)
    return res
 
#n, m, k = map(int, input().split())
n, m, k = map(int, f.readline().split())
 
#k_th = list(map(int, input().split()))
k_th = list(map(int, f.readline().split()))
 
t1 = [0 for i in range(n + 1)]
visited = [0 for i in range(n + 1)]
g1 = [[0 for i in range(26)] for j in range(n + 1)]
 
for i in range(k):
    t1[k_th[i]] = 1
 
for i in range(m):
    #start, end, char = map(str, input().split())
    start, end, char = map(str, f.readline().split())
    start = int(start)
    end = int(end)
    g1[start][ord(char) - 97] = end;
           
#n1, m1, k1 = map(int, input().split())
n1, m1, k1 = map(int, f.readline().split())
 
#k_th1 = list(map(int, input().split()))
k_th1 = list(map(int, f.readline().split()))
 
t2 = [0 for i in range(n1 + 1)]
g2 = [[0 for i in range(26)] for j in range(n1 + 1)]
 
for i in range(k1):
    t2[k_th1[i]] = 1
 
for i in range(m1):
    #start1, end1, char1 = map(str, input().split())
    start1, end1, char1 = map(str, f.readline().split())
    start1 = int(start1)
    end1 = int(end1)
    g2[start1][ord(char1) - 97] = end1
 
if (dfs(1, 1) == 1):
    f1.write("YES")
    f1.close()
    f1.close()
    #print("YES")
    exit()
 
#print("NO")
f1.write("NO")
 
f.close()
f1.close()
