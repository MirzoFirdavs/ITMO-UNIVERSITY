f = open("nextbrackets.in", "r")
f1 = open("nextbrackets.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
  
#n = str(input())
n = str(f.readline().strip())
k = len(n)
 
ans = "-"
d = 0
 
for i in range(k - 1, -1, -1):
    if(n[i] == "("):
        d -= 1
    else:
        d += 1
    if(n[i] == "(" and d > 0):
        d -= 1
        op = (k - i - 1 - d) // 2
        cl = (k - i - 1 - op)
        ans = n[0:i] + ")" + (op * "(") + (cl * ")")
        break
 
f1.write(ans)
 
f.close()
f1.close()
