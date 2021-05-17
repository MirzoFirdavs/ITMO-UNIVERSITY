st = str(input())
x = ["a", "b", "c", "d", "e",  "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"]
 
n = len(st)
 
k = 25
t = ""
tmp = ""
ans = []
 
for i in range(n):
    fl = 0
    cur = st[i]
    tmp += cur
    for j in range(k + 1):
        if(x[j] == tmp):
            fl = 1
            break
    if(not fl):
        k += 1
        x.append(tmp)
        tmp = tmp[:-1]
        for j in range(k + 1):
            if(x[j] == tmp):
               ans.append(j)
               break
        tmp = cur
         
if(len(tmp) != 0):
    for j in range(k + 1):
        if(x[j] == tmp):
            ans.append(j)
            break
print(*ans)
