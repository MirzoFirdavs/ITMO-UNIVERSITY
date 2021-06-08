f = open("problem1.in", "r")
f1 = open("problem1.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
#lst = list(input())
lst = str(f.readline().strip())
 
#n, m, k = map(int, input().split())
n, m, k = map(int, f.readline().split())
 
dka = [[0 for i in range(26)] for j in range(n + 1)]
 
terms = [0 for i in range(n + 1)]
 
#k_th = list(map(int, input().split()))
k_th = list(map(int, f.readline().split()))
 
for i in range(k):
    terms[k_th[i]] = 1
 
for i in range(m):
    #start, end, char = map(str, input().split())
    start, end, char = map(str, f.readline().split())
    start = int(start)
    end = int(end)
    dka[start][ord(char) - 97] = end
 
cur = 1
result = 1
 
for i in range(len(lst)):
    if (dka[cur][ord(lst[i]) - 97] == 0):
        result = 0
        break
    else:
        cur = dka[cur][ord(lst[i]) - 97]
 
if (result == 1 and terms[cur] == 1):
    #print("Accepts")
    f1.write("Accepts")
    f.close()
    f1.close()
    exit()
 
#print("Rejects")
f1.write("Rejects")
f.close()
f1.close()
