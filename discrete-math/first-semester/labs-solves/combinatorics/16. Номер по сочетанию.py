f = open("choose2num.in", "r")
f1 = open("choose2num.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
def gen(i, j):
    return factorial(n - j) // factorial(k - i) // factorial(n - j - k + i)
 
def factorial(x):
    cur = 1
    for i in range(1, x + 1):
        cur *= i
    return cur
 
#n, k = map(int, input().split())
#lst = list(map(int,input().split()))
n, k = map(int, f.readline().split())
lst = list(map(int, f.readline().split()))
 
ans = 0
 
lst.reverse()
lst.append(0)
lst.reverse()
 
i = 1
while i <= k:
    j = lst[i - 1] + 1
    while(j < lst[i]):
        ans += gen(i, j)
        j += 1
    i += 1
 
 
#print(ans)
f1.write(str(ans))
 
f.close()
f1.close()
