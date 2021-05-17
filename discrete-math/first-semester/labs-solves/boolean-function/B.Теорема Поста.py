def mod_table(x):
    x[0] = 1
    for i in range(1 , len(x)):
        if(x[i].count("1") == 1):
            x[i] = 1
        else:
            x[i] = 0
     
    return x
 
def polinom(x):
    check = []
    y = x[:]
    check.append(y[0])
    while(len(y) != 1):
        for i in range(len(y) - 1):
            y[i] = y[i] ^ y[i + 1]
        check.append(y[0])
        y.pop()
     
 
    return check
 
def table(x):
    t = []
    for i in range(0 , x):
        s = bin(i)
        s = s[2:]
        k = "0" * (x - len(s)) + s
        t.append(k)
    return t
 
def line(x):
    X = polinom(x)
    #print(X)
    Z = table(len(x))
    Z = mod_table(Z)
    #print(Z)
    for i in range(len(x)):
        if(X[i] == 1 and Z[i] == 0):
            return 0
            break
    else:
        return 1
 
def const1(x):
    if(x[0] == 0):
        return 1
    else:
        return 0
 
def const0(x):
    if(x[-1] == 1):
        return 1
    else:
        return 0
 
def monn(x):
    cur = 0
    for i in range(len(x)):
        for j in range(i + 1 , len(x)):
            k = i
            k1 = j
            while(k > 0):
                if(k % 2 and not(k1 % 2)):
                    break
                k //= 2
                k1 //= 2
            cur |= ((k == 0) and (x[i] > x[j]))
    if(cur == 1):
        return 0
    else:
        return 1
def samod(x):
    cur = 0
    for i in range(len(x)):
        cur |= (x[i] == x[len(x) - i - 1])
    if(cur == 1):
        return 0
    else:
        return 1
 
#print(samod([0, 1, 1, 0]))
#print(monn([1 , 0]))
#print(line([0, 1, 0, 1, 0, 1, 0, 1]))
 
 
n = int(input())
 
Y = [] ; X = []
 
ans = [0 for i in range(5)]
 
for i in range(n):
    x , y = map(str,input().split())
    for j in range(len(y)):
        X.append(int(y[j]))
    Y.append(X)
    X = []
     
for j in range(n):
    ans[0] += const1(Y[j])
    ans[1] += const0(Y[j])
    ans[2] += monn(Y[j])
    ans[3] += samod(Y[j])
    ans[4] += line(Y[j])
 
#print(ans)
 
if(n not in ans):
    print("YES")
else:
    print("NO")
