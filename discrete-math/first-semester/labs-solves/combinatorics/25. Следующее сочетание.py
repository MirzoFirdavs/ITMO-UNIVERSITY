f = open("nextchoose.in", "r")
f1 = open("nextchoose.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
   
#n, k = map(int, input().split())
#lst = list(map(int, input().split()))
n, k = map(int, f.readline().split())
lst = list(map(int, f.readline().split()))
 
lst.append(n + 1)
b = 0
 
for i in range(k, -1, -1):
    if(lst[i - 1] <= lst[i] - 2):
        b = i - 1
        break
     
lst[b] += 1
 
for i in range(b + 1, k, 1):
    lst[i] = lst[i - 1] + 1
 
for i in range(k):
    if(lst[i] > n or lst[i] < 1):
        f1.write("-1")
        #print(-1)
        f.close()
        f1.close()
        exit()
for i in range(k - 1):
    f1.write(str(lst[i]))
    f1.write(" ")
f1.write(str(lst[-2]))
#print(lst)    
 
f.close()
f1.close()
