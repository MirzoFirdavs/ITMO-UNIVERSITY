def refl(x):
    ans1 = 1 ; ans2 = 1
    for i in range(n):
        ans1 &= x[i][i]
        ans2 &= (x[i][i] == 0)
 
    return (ans1 , ans2)
 
def simm(x):
    ans1 = 1 ; ans2 = 1
    for i in range(n):
        for j in range(n):
            if(i != j):
                if(x[i][j]):
                    ans1 &= x[j][i]
                    ans2 &= (x[j][i] == 0)
 
    return(ans1 , ans2)
 
def tranz(x):
    ans = 1
    for i in range(n):
        for j in range(n):
            if(x[i][j]):
                for k in range(n):
                    if(x[j][k]):
                        ans &= x[i][k]
 
    return ans
     
             
n = int(input())
 
X , Y = [] , []
 
for i in range(n):
    X.append(list(map(int,input().split())))
     
for i in range(n):
    Y.append(list(map(int,input().split())))
 
r1 , r2 = refl(X)
r3 , r4 = refl(Y)
 
t1 = tranz(X) ; t2 = tranz(Y)
 
comp = [[0 for i in range(n)] for j in range(n)]
 
for i in range(n):
    for j in range(n):
        if(X[i][j]):
            for k in range(n):
                if(Y[j][k]):
                    comp[i][k] = 1
 
s1 , s2 = simm(X)
s3 , s4 = simm(Y)
 
print(r1 , r2 , s1 , s2 , t1)
print(r3 , r4 , s3 , s4 , t2)
 
for i in range(n):
    print(*comp[i])
