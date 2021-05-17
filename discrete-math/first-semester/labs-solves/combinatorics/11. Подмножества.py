f = open("subsets.in", "r")
f1 = open("subsets.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
n = 0
 
def b_n(x):
    st = []
    while(x != 0):
        st.append(str(x % 2))
        x //= 2
    st.reverse()
    z = "".join(st)
    z = "0" * (n - len(z)) + z
    st = []
    return z
 
#n = int(input())
n = int(f.readline())
 
ans = []
cur = []
 
for i in range(1 , 2 ** n):
    x = b_n(i)
    for j in range(n - 1, -1, -1):
        if(x[j] == "1"):
            cur.append(n - j)     
    ans.append(cur)
    cur = []
 
ans.sort()
f1.write('\n')
 
for i in range(len(ans)):
    for j in range(len(ans[i])):
        f1.write(str(ans[i][j]))
        f1.write(" ")
    f1.write('\n')
         
#print(ans)
 
f.close()
f1.close()
