f = open("num2choose.in", "r")
f1 = open("num2choose.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
def factorial(x):
    cur = 1
    for i in range(1, x + 1):
        cur *= i
    return cur
 
def check(n, k):
    return factorial(n - 1) // factorial(k - 1) // factorial(n - k)
 
#n, k, m = map(int,input().split())
n, k, m = map(int,f.readline().split())
 
ans = ["0" for i in range(k)]
 
p, nt = 0, 1
 
while(k != 0):
    if(m < check(n, k)):
        ans[p] = str(nt)
        p += 1
        k -= 1
    else:
        m -= check(n, k)
    n -= 1
    nt += 1
     
#print(ans)
for i in range(len(ans) - 1):
    f1.write(ans[i] + " ")
f1.write(ans[-1])
 
f.close()
f1.close()
