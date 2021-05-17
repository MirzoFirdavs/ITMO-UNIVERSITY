f = open("num2perm.in", "r")
f1 = open("num2perm.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
   
n , k = map(int, f.readline().split())
#n , k = map(int, input().split())
fact = [1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800,  39916800, 479001600, 6227020800, 87178291200, 1307674368000, 20922789888000, 355687428096000, 6402373705728000, 121645100408832000, 2432902008176640000]
  
used = [0 for i in range(n + 1)]
b = 0
def notUsed(used, b):
    pos = 0
    j = 0
    for j in range(1, len(used)):
        if(not used[j]):
            pos += 1
        if(b == pos):
            break
    return j
  
def perm(n, num):
    cur = [0 for i in range(n)]
    for i in range(n):
        b = (num - 1) // fact[n - i - 1] + 1
        j = notUsed(used, b)
        cur[i] = j
        used[j] = 1
        num = (num - 1) % fact[n - i - 1] + 1
  
    return cur
  
ans = perm(n, k + 1)
#print(ans)
  
for i in range(n - 1):
    f1.write(str(ans[i]))
    f1.write(' ')
f1.write(str(ans[-1]))
  
f.close()
f1.close()
