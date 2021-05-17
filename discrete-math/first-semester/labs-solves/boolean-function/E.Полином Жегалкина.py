n = int(input())
 
X , Y = [] , []
check = []
 
for i in range(2 ** n):
    x , y = map(str,input().split())
    X.append(x)
    Y.append(int(y))
 
check.append(Y[0])
 
while(len(Y) != 1):
    for i in range(len(Y) - 1):
        Y[i] = Y[i] ^ Y[i + 1]
    check.append(Y[0])
    Y.pop()
 
for i in range(2 ** n):
    print(X[i] , check[i])
