f = open("automaton.in", "r")
f1 = open("automaton.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
ks = [[[] for i in range(30)] for j in range(30)]
 
#n, s = map(str, input().split())
n, s = map(str, f.readline().split())
 
n = int(n)
 
for i in range(n):
    #x, y, z = map(str, input().split())
    x, y, z = map(str, f.readline().split())
    if (len(z) == 1):
        ks[ord(x) - 65][ord(z[0]) - 97].append(26)
    else:
        ks[ord(x) - 65][ord(z[0]) - 97].append(ord(z[1]) - 65)
 
#m = int(input())
m = int(f.readline())
 
for i in range(m):
    #st = str(input().strip())
    st = str(f.readline().strip())
    res = [[0 for i in range(30)]]
    res[0][ord(s) - 65] = 1
    for j in range(len(st)):
        res.append([0 for i in range(30)])
        for x in range(30):
            if (res[j][x]):
                for y in range(len(ks[x][ord(st[j]) - 97])):
                    res[j + 1][ks[x][ord(st[j]) - 97][y]] = 1
    if (res[len(st)][26]):
        f1.write("yes")
        #print("yes")
    else:
        f1.write("no")
        #print("no")
    f1.write("\n")
      
f.close()
f1.close()
