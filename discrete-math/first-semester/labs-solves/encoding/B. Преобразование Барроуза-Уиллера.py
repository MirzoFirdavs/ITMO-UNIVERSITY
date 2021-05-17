st = str(input())
 
ans = [st]
 
for i in range(len(st) - 1):
    ans.append(st[i + 1:] + st[:i + 1])
 
ans.sort()
 
ANS = ""
 
for i in range(len(ans)):
    ANS += ans[i][-1]
 
print(ANS)
