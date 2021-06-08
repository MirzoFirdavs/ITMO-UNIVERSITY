f = open("epsilon.in", "r")
f1 = open("epsilon.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
#n, s = map(str, input().split())
n, s = map(str, f.readline().split())
 
n = int(n)
 
ks = [[] for i in range(26)]
pos = [0 for i in range(26)]
 
for i in range(n):
    #lst = list(map(str, input().split()))
    lst = list(map(str, f.readline().split()))
    if (len(lst) == 2):
        pos[ord(lst[0]) - 65] = 1
    else:
        ks[ord(lst[0]) - 65].append([lst[2], 0])
 
x = 1
 
while(x):
    x = 0
    for i in range(26):
        if (pos[i]):
            continue
        for j in range(len(ks[i])):
            if (ks[i][j][1]):
                continue
            nt = 0
            for g in range(len(ks[i][j][0])):
                if (65 <= ord(ks[i][j][0][g]) and ord(ks[i][j][0][g]) <= 90):
                    if (not pos[ord(ks[i][j][0][g]) - 65]):
                        nt = 1
                        break
                elif(97 <= ord(ks[i][j][0][g]) and ord(ks[i][j][0][g]) <= 122):
                    nt = 1
                    ks[i][j][1] = 1
                    break
            if (nt):
                continue
            else:
                pos[i] = 1
                x = 1
 
for i in range(26):
    if (pos[i]):
        f1.write(str(chr(i + 65)))
        f1.write(" ")
        #print(chr(i + 65))
 
f.close()
f1.close()
