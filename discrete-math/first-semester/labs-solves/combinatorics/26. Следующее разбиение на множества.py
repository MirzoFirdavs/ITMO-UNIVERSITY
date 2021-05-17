f = open("nextsetpartition.in", "r")
f1 = open("nextsetpartition.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
def func(v):
    n = len(v)
    if(len(v) == 1 or len(v) == 0):
        return v
    left = [0 for i in range(n // 2)]
    right = [0 for i in range(n // 2 + n % 2)]
    for i in range(n // 2):
        left[i] = v[i]
    for i in range(n // 2 + n % 2):
        right[i] = v[n // 2 + i]
    func(left); func(right)
    idl = 0; idr = 0
    for i in range(n):
        if(idl == len(left)):
            v[i] = right[idr]
            idr += 1
        elif(idr == len(right)):
            v[i] = left[idl]
            idl += 1
        else:
            if(left[idl] <= right[idr]):
                v[i] = left[idl]
                idl += 1
            else:
                v[i] = right[idr]
                idr += 1
    return v
 
n, k = 1, 1
 
while(True):
    n, k = map(int, f.readline().split())
    #n, k = map(int, input().split())
    if(n == 0 and k == 0):
        f.close()
        f1.close()
        exit()
    d, s = [], []
    for i in range(k):
        #d.append(list(map(int, input().split())))
        d.append(list(map(int, f.readline().split())))
    f.readline()
    #print()
    fl = 0
    for i in range(k - 1, -1, -1):
        mn = 10 ** 9 + 239; ex = -1
        for j in range(len(s)):
            if(s[j] < mn and s[j] > d[i][-1]):
                mn = s[j]
                ex = j
        if(ex != -1):
            d[i].append(mn)
            for j in range(ex, len(s) - 1):
                s[j] = s[j + 1]
            s.pop()
            break
        last = len(d[i]) - 1
        for j in range(last, -1, -1):
            mn = 10 ** 9 + 239; ex = -1
            for h in range(len(s)):
                if(s[h] < mn and s[h] > d[i][j]):
                    mn = s[h]
                    ex = h
            if(ex != -1 and j > 0):
                fl = 1
                d[i][j], s[ex] = s[ex], d[i][j]
                break
            else:
                s.append(d[i][-1])
                d[i].pop()
        if(fl):
            break
    s = func(s)
    for i in range(len(s)):
        cur = []
        cur.append(s[i])
        d.append(cur)
    i = 0
    while(i < len(d)):
        if(len(d[i]) != 0):
            i += 1
        else:
            for j in range(i, len(d) - 1):
                d[j] = d[j + 1]
            d.pop()
        i += 1
 
    #print(n, len(d) - d.count([]))
    f1.write(str(n) + " " + str(len(d) - d.count([])) + "\n")
    for i in d:
        if(i != []):
            #print(*i)
            for j in i:
                f1.write(str(j))
                f1.write(" ")
            f1.write("\n")
    #print()
    f1.write("\n")
 
f.close()
f1.close()
