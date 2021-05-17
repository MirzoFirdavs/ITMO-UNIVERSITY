f = open("num2brackets2.in", "r")
f1 = open("num2brackets2.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
n, k = map(int,f.readline().split())
#n, k = map(int,input().split())
 
k += 1
 
d = [[0 for i in range(n + 1)] for j in range(2 * n + 1)]
 
d[0][0] = 1
 
for i in range(n * 2):
    for j in range(n + 1):
        if(j + 1 <= n):
            d[i + 1][j + 1] += d[i][j]
        if(j > 0):
            d[i + 1][j - 1] += d[i][j]
 
ans = ""
di = 0
stack = ["" for i in range(n * 2)]
 
stacksz = 0
 
for i in range(n * 2 - 1, -1, -1):
    if(di + 1 <= n):
        b = d[i][di + 1]
        for j in range((i - di - 1) // 2):
            b *= 2
        cur = b
    else:
        cur = 0
    if(cur >= k):
        ans += "("
        stack[stacksz] = "("
        stacksz += 1
        di += 1
        continue
    k -= cur
    if(stacksz > 0 and stack[stacksz - 1] == "(" and di - 1 >= 0):
        b = d[i][di - 1] 
        for j in range((i - di + 1) // 2):
            b *= 2 
        cur = b
    else:
        cur = 0
    if(cur >= k):
        ans += ")"
        stacksz -= 1
        di -= 1
        continue
    k -= cur
    if(di + 1 <= n):
        b = d[i][di + 1]
        for j in range((i - di - 1) // 2):
            b *= 2
        cur = b
    else:
        cur = 0
    if(cur >= k):
        ans += "["
        stack[stacksz] = "["
        stacksz += 1
        di += 1
        continue
    k -= cur
    ans += "]"
    stacksz -= 1
    di -= 1
 
#print(ans)
f1.write(ans)
 
f.close()
f1.close()
