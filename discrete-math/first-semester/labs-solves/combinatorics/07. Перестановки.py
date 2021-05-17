from itertools import permutations
 
f = open("permutations.in", "r")
f1 = open("permutations.out", "w")
 
n = int(f.readline())
x = [i for i in range(1, n + 1)]
 
y = list(permutations(x))
 
for i in range(len(y) - 1):
    for j in range(n):
        f1.write(str(y[i][j]))
        f1.write(" ")
    f1.write('\n')
 
for i in range(n):
    f1.write(str(y[-1][i]))
    f1.write(" ")
 
f.close()
f1.close()
