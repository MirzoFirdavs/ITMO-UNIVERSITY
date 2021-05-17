f = open("input.txt", "r")
f1 = open("output.txt", "w")

def build(n1, m1, x):
    cur1, cur2 = [], []
    for i in range(n1 * m1):
        cur1.append(x[i])
        if((i + 1) % m1 == 0):
            cur2.append(cur1)
            cur1 = []
    return cur2

def addition(n1, m1, x, y, n2, m2):
    cur= [[0 for i in range(m1)] for j in range(n1)]
    for i in range(n1):
        for j in range(m1):
            cur[i][j] = x[i][j] + y[i][j]
    return cur

def multiplication_to(n1, m1, x, y):
    for i in range(n1):
        for j in range(m1):
            x[i][j] = y * x[i][j]
    return x

def transposition(n1, m1, x):
    res=[]
    for j in range(m1):
        tmp=[]
        for i in range(n1):
            tmp=tmp+[x[i][j]]
        res=res+[tmp]
    return res

def multiplication(n1, m1, x, y, n2, m2):
    s_m=0     
    cur1=[]   
    cur2=[]  
    for z in range(n1):
        for j in range(m2):
            for i in range(m1):
                s_m += (x[z][i] * y[i][j])
            cur1.append(s_m)
            s_m=0
        cur2.append(cur1)
        cur1=[]           
    return cur2

A, B, C, D, E = [], [], [], [], []
alpha, betta = map(float, f.readline().split())
nA, mA = map(int, f.readline().split())
a = list(map(float, f.readline().split()))
nB, mB = map(int, f.readline().split())
b = list(map(float, f.readline().split())) 
nC, mC = map(int, f.readline().split())
c = list(map(float, f.readline().split()))
nD, mD = map(int, f.readline().split())
d = list(map(float, f.readline().split()))
nE, mE = map(int, f.readline().split())
e = list(map(float, f.readline().split()))

A = build(nA, mA, a)
B = build(nB, mB, b)
C = build(nC, mC, c)
D = build(nD, mD, d)
E = build(nE, mE, e)

for i in range(nE):
    for j in range(mE):
        E[i][j] = 0 - E[i][j]

A = multiplication_to(nA, mA, A, alpha)
B = transposition(nB, mB, B)

nB, mB= len(B), len(B[0])

B = multiplication_to(nB, mB, B, betta)

if(nA != nB or mA != mB):
        f1.write("0")
        f.close()
        f1.close()
        exit()

X = addition(nA, mA, A, B, nB, mB)
X = transposition(nA, mA, X)

nX, mX = len(X), len(X[0])

if(mC != nX):
    f1.write("0")
    f.close()
    f1.close()
    exit()

X1 = multiplication(nC, mC, C, X, nX, mX)

nX1, mX1 = len(X1), len(X1[0])

if(mX1 != nD):
    f1.write("0")
    f.close()
    f1.close()
    exit()

X2 = multiplication(nX1, mX1, X1, D, nD, mD)

nX2, mX2 = len(X2), len(X2[0])
    
if(nX2 != nE or mX2 != mE):
        f1.write("0")
        f.close()
        f1.close()
        exit()

ans = addition(nX2, mX2, X2, E, nE, mE)

f1.write("1")
f1.write('\n')
f1.write(str(len(ans)))
f1.write(" ")
f1.write(str(len(ans[0])))
f1.write('\n')

for i in range(len(ans)):
    for j in range(len(ans[i])):
        f1.write(str(ans[i][j]))
        f1.write(" ")
    
f.close()
f1.close()
