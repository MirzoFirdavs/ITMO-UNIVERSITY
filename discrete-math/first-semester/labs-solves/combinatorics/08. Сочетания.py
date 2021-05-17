from itertools import permutations
from itertools import combinations
 
 
f = open("choose.in", "r")
f1 = open("choose.out", "w")
 
n, k = map(int, f.readline().split())
#n, k = map(int,input().split())
x = [i for i in range(1, n + 1)]
 
y = list(combinations(x, k))
 
for i in range(len(y) - 1):
    for j in range(k):
        f1.write(str(y[i][j]))
        f1.write(" ")
    f1.write('\n')
 
for i in range(k):
    f1.write(str(y[-1][i]))
    f1.write(" ")
 
f.close()
f1.close()
