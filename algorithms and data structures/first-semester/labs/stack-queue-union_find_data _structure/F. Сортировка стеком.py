n = int(input())
lst = list(map(int,input().split()))
 
cur = 1
k = 0
stack = []
 
ans = []
l = 0
 
for i in range(n):
    stack.append(lst[i])
    ans.append("push")
    l += 1
    while(l > 0 and stack[l - 1] == cur):
        stack.pop()
        ans.append("pop")
        l -= 1
        cur += 1
 
if(len(stack) > 0):
    print("impossible")
    exit()
    
print(*ans, sep = '\n')
