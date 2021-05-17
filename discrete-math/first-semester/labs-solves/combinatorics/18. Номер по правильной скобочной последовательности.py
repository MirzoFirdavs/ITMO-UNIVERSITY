f = open("brackets2num.in", "r")
f1 = open("brackets2num.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
def gen(d):
    for i in range(2 * n):
        for j in range(n + 2):
            if (j + 1 <= n + 1):
                d[i + 1][j + 1] += d[i][j]
            if(j > 0):
                d[i + 1][j - 1] += d[i][j]
 
def get(st):
    cur = 0
    di = 0
    for i in range(2 * n):
        if(st[i] == "("):
            di += 1
        else:
            cur += d[2 * n - i - 1][di + 1]
            di -= 1
    return cur
 
#st = str(input().strip())
st = str(f.readline().strip())
 
n = len(st) // 2
 
d = [[0 for i in range(n + 2)] for j in range(2 * n + 1)]
 
d[0][0] = 1
 
gen(d)
 
ans = get(st)
 
#print(ans)
f1.write(str(ans))
 
f.close()
f1.close()
