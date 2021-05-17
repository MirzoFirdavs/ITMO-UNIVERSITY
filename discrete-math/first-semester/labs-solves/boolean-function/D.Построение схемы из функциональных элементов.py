def scheme(lst):
    global k
    while(len(lst) != 1):
        print('3 ' + lst[0] + ' ' + lst[1])
        lst[1] = str(k)
        k += 1
        lst.pop(0)
 
n = int(input())
 
z , ans = [] , []
k = 2*n + 1
 
for i in range(2 ** n):
    x , y = map(str,input().split())
    if(y == '0'):
        z.append(x)
 
for i in range(len(z)):
    cur = []
    for j in range(len(z[i])):
        if(z[i][j] == '1'):
            cur.append(str(n + j + 1))
        else:
            cur.append(str(j + 1))
 
    ans.append(cur)
if(len(z) != 0):
    print(n * (len(ans) + 2) - 1)
    for i in range(n):
        print("1 " + str(i + 1))
    for i in range(len(ans)):
        scheme(ans[i])
        ans[i] = ans[i][0]
 
    while(len(ans) != 1):
        print("2 " + ans[0] + " " + ans[1])
        ans[1] = str(k)
 
        k += 1
 
        ans.pop(0)
 
else:
    print(n + 2)
    print("1 1")
    print("3 1 " + str(n + 1))
