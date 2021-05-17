f = open("perm2num.in", "r")
f1 = open("perm2num.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
def factorial(x):
    cur = 1
    for i in range(1, x + 1):
        cur *= i
    return cur
 
#n = int(input())
#lst = list(map(int,input().split()))
n = int(f.readline())
lst = list(map(int, f.readline().split()))
 
 
ans = (lst[0] - 1) * factorial(n - 1)
cur = [lst[0]]
 
for i in range(1, n):
    k = 1
    b = 0
    while(k < lst[i]):
        if(k not in cur):
            b += 1
        k += 1
    ans += b * factorial(n - i - 1)
    cur.append(lst[i])
 
#print(ans)
f1.write(str(ans))
 
f.close()
f1.close()
