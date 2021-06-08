f = open("useless.in", "r")
f1 = open("useless.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
def dfs(g, visited, v, useful):
    if (visited[v] == 1 or useful[v] == 0):
        return
    visited[v] = 1
    for p in g[v]:
        if (p[1] == 1):
            for ch in p[0]:
                if (ord(ch) >= 65 and ord(ch) <= 90):
                    dfs(g, visited, ord(ch) - 65, useful)
   
#n, s = map(str, input().split())
n, s = map(str, f.readline().split())
   
n = int(n)
 
start = ord(s[0]) - 65
 
v = [[] for i in range(26)]
reachable = [0 for i in range(26)]
useful = [0 for i in range(26)]
to_check = [0 for i in range(26)]
 
to_check[start] = 1
 
for i in range(n):
    #lst = list(map(str, input().split()))
    lst = list(map(str, f.readline().split())) 
    nt = lst[0]
    if (len(lst) == 2):
        lst.append("")
    v[ord(nt) - 65].append([lst[2], 1])
    to_check[ord(nt) - 65] = 1
    for i in lst[2]:
        if (ord(i) >= 65 and ord(i) <= 90):
            to_check[ord(i) - 65] = 1
 
changed = 1
 
while(changed):
    changed = 0
    for i in range(26):
        if (to_check[i] == 0 or useful[i] == 1):
            continue
        for p in range(len(v[i])):
            s = v[i][p][0]
            mean = 1
            for ch in range(len(v[i][p][0])):
                if (ord(v[i][p][0][ch]) >= 65 and ord(v[i][p][0][ch]) <= 90 and useful[ord(v[i][p][0][ch]) - 65] == 0):
                    mean = 0
                    break
            if (mean):
                useful[i] = 1
                changed = 1
 
for i in range(26):
    if(to_check[i] == 0 or useful[i] == 0):
        continue
    for p in v[i]:
        for ch in p[0]:
            if (65 <= ord(ch) and ord(ch) <= 90 and useful[ord(ch) - 65] == 0):
                p[1] = 0
                break
 
dfs(v, reachable, start, useful)
 
ans = []
 
for i in range(26):
    if(to_check[i] == 1 and reachable[i] == 0):
        ans.append(chr(i + 65))
 
for i in range(len(ans)):
    f1.write(str(ans[i]))
    f1.write(" ")
 
f.close()
f1.close()
