f = open("part2num.in", "r")
f1 = open("part2num.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
def get_nums(x):
    st = ""
    cur = []
    for i in range(len(x)):
        if(x[i] != "+"):
            st += x[i]
        else:
            cur.append(int(st))
            st = ""
    cur.append(int(st))
    return cur
 
#lst = str(input().strip())
lst = str(f.readline().strip())
  
 
st = get_nums(lst)
n = sum(st)
d = [[0 for i in range(n + 1)] for j in range(n + 1)]
d[0][0] = 1
 
for i in range(1, n + 1):
    for j in range(1, i + 1):
        if(i == j):
            d[i][j] = 1
        k = j
        while(k + j <= i):
            d[i][j] += d[i - j][k]
            k += 1
 
ans = 0
cur = 1
 
for i in range(len(st)):
    for j in range(cur, st[i]):
        ans += d[n][j]
    n -= st[i]
    cur = st[i]
 
#print(ans)
f1.write(str(ans))
 
f.close()
f1.close()
