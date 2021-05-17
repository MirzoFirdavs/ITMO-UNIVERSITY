f = open("nextmultiperm.in", "r")
f1 = open("nextmultiperm.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
  
  
#n = int(input())
n = int(f.readline())
   
#lst = list(map(int,input().split()))
lst = list(map(int, f.readline().split()))
cur = n - 2
 
while(cur >= 0 and lst[cur] >= lst[cur + 1]):
    cur -= 1
if(cur >= 0):
    i = cur + 1
    while(i < n - 1 and lst[i + 1] > lst[cur]):
        i += 1
    lst[i], lst[cur] = lst[cur], lst[i]
    ans = lst[:cur + 1]
    for j in range(n - 1, cur, -1):
        ans.append(lst[j])
    #print(ans)
    for i in range(n - 1):
        f1.write(str(ans[i]))
        f1.write(" ")
    f1.write(str(ans[-1]))
else:
    #print("0" * n)
    f1.write("0 " * (n - 1) + "0")
 
  
f.close()
f1.close()
