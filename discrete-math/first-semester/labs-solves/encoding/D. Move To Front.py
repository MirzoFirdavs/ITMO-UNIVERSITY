st = str(input())
 
x = [i for i in range(26)]
 
ans = []
i = 0
 
while(i < len(st)):
    j = 0
    while(x[j] != ord(st[i]) - ord("a")):
        j += 1
    ans.append(j + 1)
    k = x[j]
    while(j > 0):
        x[j] = x[j - 1]
        j -= 1
    x[0] = k
    i += 1
 
print(*ans)
