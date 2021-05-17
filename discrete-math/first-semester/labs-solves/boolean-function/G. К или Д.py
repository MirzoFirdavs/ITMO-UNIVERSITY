n = int(input())
m = [0 for i in range(32)]
a = [[0 for i in range(32)] for j in range(2 * n)]
 
z = list(map(int,input().split()))
 
for i in range(n):
    for j in range(32):
        a[i][j] = ((z[i] & (1 << j)) > 0);
    z[i] = ((2 ** 32) - z[i] - 1)
    for j in range(32):
        a[i + n][j] = ((z[i] & (1 << j)) > 0)
 
x = int(input())
 
for j in range(32):
    m[j] = ((x & (1 << j)) > 0)
 
ans = []
 
for i in range(32):
    cur = []
    if(m[i] == 1):
        for j in range(2 * n):
            if(a[j][i] == 1):
                cur.append(j)
        if(len(cur) == 0):
            print("Impossible")
            exit()
        ans.append(cur)
 
u = 0
 
for now in ans:
    cur = [0 for i in range(32)]
    cur[0] = 1
    ss = 0
    for j in range(32):
        cur[j] = 1
    for now1 in now:
        ss += (now1 >= n)
        for i in range(32):
            cur[i] &= a[now1][i]
    for i in range(32):
        if(cur[i] > m[i] or ss == len(now) or x >= (2 ** 32) or x < 0):
            print("Impossible")
            exit()
 
if(x == 0):
    print("1&~1")
    exit()
 
k ="("
 
for now in ans:
    if(u >= 1):
        k += "|("
    uu = 0
    for now1 in now:
        if(uu >= 1):
            k += "&"
        if(now1 >= n):
            k += "~" + str(now1 - n + 1)
        else:
            k += str(now1 + 1)
        uu += 1
    u += 1
    k += ")"
 
print(k)
