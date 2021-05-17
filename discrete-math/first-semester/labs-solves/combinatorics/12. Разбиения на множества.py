import sys
sys.setrecursionlimit(10000)
 
f = open("part2sets.in", "r")
f1 = open("part2sets.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
def gen(n, k):
    if(n == k):
        return 1
    if(n < k or n * k == 0):
        return 0
    return k * gen(n - 1, k) + gen(n - 1, k - 1)
 
#n ,k = map(int,input().split())
n, k = map(int, f.readline().split())
cur = gen(n, k)
d, s = [], []
 
for i in range(k - 1):
    d.append([i + 1])
 
d.append([i for i in range(k, n + 1)])
 
cnt = 1
 
for i in d:
    for j in i:
        f1.write(str(j))
        f1.write(" ")
        #print(j, end = " ")
    f1.write("\n")
    #print()
#print()
f1.write("\n")
         
if(cnt == cur):
    f.close()
    f1.close()
    exit()
kkk = k;
while(True):
    s = []
    fl = 0
    for i in range(kkk - 1, -1, -1):
        mn = 10 ** 9 + 239; ex = -1
        for j in range(len(s)):
            if(s[j] < mn and s[j] > d[i][-1]):
                mn = s[j]; ex = j
        if(ex != -1):
            d[i].append(mn)
            for j in range(ex, len(s) - 1):
                s[j] = s[j + 1]
            s.pop()
            break
        for j in range(len(d[i]) - 1, -1, -1):
            mn = 10 ** 9 + 239; ex = -1
            for h in range(len(s)):
                if(s[h] < mn and s[h] > d[i][j]):
                    mn = s[h]; ex = h
            if(ex != -1 and j > 0):
                fl = 1
                d[i][j], s[ex] = s[ex], d[i][j]
                break
            else:
                s.append(d[i][-1])
                d[i].pop()
        if(fl):
            break
    s.sort()
    for i in range(len(s)):
        b = []
        b.append(s[i])
        d.append(b)
    i = 0
    while(i < len(d)):
        if(len(d[i]) != 0):
            i += 1
        else:
            for j in range(i, len(d) - 1):
                d[j] = d[j + 1]
            d.pop()
        #i += 1
    kkk = len(d)
    if(kkk != k):
        continue
    cnt += 1
    for i in d:
        for j in i:
            f1.write(str(j))
            f1.write(" ")
            #print(j, end = " ")
        #print()
        f1.write("\n")
    #print()
    f1.write("\n")
    if(cnt == cur):
        f.close()
        f1.close()
        exit()
         
f.close()
f1.close()
