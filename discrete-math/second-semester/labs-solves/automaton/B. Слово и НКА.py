f = open("problem2.in", "r")
f1 = open("problem2.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
#lst = list(input())
lst = str(f.readline().strip())
 
#n, m, k = map(int, input().split())
n, m, k = map(int, f.readline().split())
 
nka = [[0 for i in range(101)] for j in range(10001)]
x = [[[] for i in range(30)] for j in range(10001)]
 
nka[0][1] = 1
 
#k_th = list(map(int, input().split()))
k_th = list(map(int, f.readline().split()))
 
for i in range(m):
    #start, end, char = map(str, input().split())
    start, end, char = map(str, f.readline().split())
    start = int(start)
    end = int(end)
    x[end][ord(char) - 97].append(start)
 
for i in range(1, len(lst) + 1):
    for j in range(1, n + 1):
        way = 0
        b = x[j][ord(lst[i - 1]) - 97]
        for g in range(len(b)):
            way = way | nka[i - 1][b[g]]
            if (way):
                break
        nka[i][j] = way
 
for i in range(k):
    if (nka[len(lst)][k_th[i]]):
        #print("Accepts")
        f1.write("Accepts")
        f.close()
        f1.close()
        exit()
 
f1.write("Rejects")
f.close()
f1.close()
#print("Rejects")
