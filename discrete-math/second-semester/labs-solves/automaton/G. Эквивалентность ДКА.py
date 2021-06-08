f = open("equivalence.in", "r")
f1 = open("equivalence.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
   
t1 = []; t2 = []; visited1 = []; visited2 = []; g1 = []; g2 = []
   
def bfs(v1, v2):
    q = []
    q.append([v1, v2])
    while (len(q) != 0):
        z1 = q[0][0]; z2 = q[0][1]
        if (z1 != 0):
            visited1[z1] = 1
        if (z2 != 0):
            visited2[z2] = 1
        q = q[1:]
        if (t1[z1] != t2[z2]):
            return 0
        for i in range(26):
            s1 = g1[z1][i]; s2 = g2[z2][i]
            if ((not visited1[s1]) or (not visited2[s2])):
                if (s1 != 0 or s2 != 0):
                    q.append([s1, s2])
    return 1
          
#n, m, k = map(int, input().split())
n, m, k = map(int, f.readline().split())
   
#k_th = list(map(int, input().split()))
k_th = list(map(int, f.readline().split()))
   
t1 = [0 for i in range(n + 1)]
visited1 = [0 for i in range(n + 1)]
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
visited2 = [0 for i in range(n1 + 1)]
g2 = [[0 for i in range(26)] for j in range(n1 + 1)]
   
for i in range(k1):
    t2[k_th1[i]] = 1
   
for i in range(m1):
    #start1, end1, char1 = map(str, input().split())
    start1, end1, char1 = map(str, f.readline().split())
    start1 = int(start1) 
    end1 = int(end1)
    g2[start1][ord(char1) - 97] = end1
   
if (bfs(1, 1) == 1):
    f1.write("YES")
    f1.close()
    f1.close()
    #print("YES")
    exit()
   
#print("NO")
f1.write("NO") 
 
f.close()
f1.close()
