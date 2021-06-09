n = int(input())
lst = list(map(int, input().split()))
 
x = [[] for i in range(n + 1)]
 
x[0] = [0]
 
for i in range(1, n + 1):
    x[i].append(lst[i - 1])
 
i = 1
 
while(1 << (i - 1) < n):
    for j in range(n + 1):
        x[j].append(x[x[j][i - 1]][i - 1])
    i += 1
 
y = []
ans = []
 
for i in range(1, n + 1):
    for j in range(len(x[i])):
        if(x[i][j] != 0):
            y.append(x[i][j])
    ans.append(y)
    y = []
 
for i in range(n):
    print(str(i + 1) + ":", *ans[i])
 
