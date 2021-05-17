f = open("partition.in", "r")
f1 = open("partition.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
def prnt(x):
    cur = ""
    for i in range(len(x) - 1):
        cur += str(x[i])
        cur += "+"
    cur += str(x[-1])
    #print(cur)
    f1.write(cur)
    f1.write('\n')
 
s = []
 
def gen(last, sm):
    if(sm == n):
        prnt(s)
    for i in range(last, n - sm + 1):
        s.append(i)
        gen(i, sm + i)
        s.pop()
 
#n = int(input())
n = int(f.readline())
for i in range(1, n + 1):
    s.append(i)
    gen(i, i)
    s.pop()
 
f.close()
f1.close()
