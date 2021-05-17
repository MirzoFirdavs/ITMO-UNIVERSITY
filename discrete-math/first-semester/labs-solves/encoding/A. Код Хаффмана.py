n = int(input())
 
ans = 0
 
lst = list(map(int, input().split()))
 
lst.sort()
 
for i in range(n - 1):
    x = lst[0]
    lst.pop(0)
    y = lst[0]
    lst.pop(0)
    lst.append(x + y)
    ans += (x + y)
    lst.sort()
    
print(ans)
