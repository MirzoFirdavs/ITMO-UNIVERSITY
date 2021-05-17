f = open("nextperm.in", "r")
f1 = open("nextperm.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
def next_perm(x):
    cur = []
    for i in range(n - 2, -1, -1):
        if(x[i] < x[i + 1]):
            m_n = i + 1
            for j in range(i + 1, n):
                if(x[j] < x[m_n] and x[j] > x[i]):
                    m_n = j
            x[i], x[m_n] = x[m_n], x[i]
            cur = x[:i + 1]
            for j in range(n - 1, i, -1):
                cur.append(x[j])
            return cur
 
def last_perm(x):
    cur = []
    for i in range(n - 2, -1, -1):
        if(x[i] > x[i + 1]):
            m_x = i + 1
            for j in range(i + 1, n):
                if(x[j] > x[m_x] and x[j] < x[i]):
                    m_x = j
            x[i], x[m_x] = x[m_x], x[i]
            cur = x[: i + 1]
            for j in range(n - 1, i, -1):
                cur.append(x[j])
            return cur
 
#n = int(input())
n = int(f.readline())
  
cur = [0 for i in range(n)]
k = [i for i in range(1, n + 1)]
  
#lst = list(map(int,input().split()))
lst = list(map(int, f.readline().split()))
 
if(n == 1):
    f1.write("0")
    f1.write("\n")
    f1.write("0")
    f.close()
    f1.close()
    exit()
      
k = lst[:]
k.sort()
 
if(lst == k):
    #print("0 " * (n - 1) + "0")
    f1.write("0 " * (n - 1) + "0")
    f1.write('\n')
    cur = next_perm(lst)
    for i in range(n - 1):
        f1.write(str(cur[i]))
        f1.write(" ")
    f1.write(str(cur[-1]))
    #print(next_perm(lst))
    f.close()
    f1.close()
    exit()
 
k.reverse()
 
if (lst == k):
    cur = last_perm(lst)
    for i in range(n - 1):
        f1.write(str(cur[i]))
        f1.write(" ")
    f1.write(str(cur[-1]))
    f1.write("\n")
    f1.write("0 " * (n - 1) + "0")
    #print(last_perm(lst))
    #print("0 " * (n - 1) + ("0"))
    f.close()
    f1.close()
    exit()
 
cur = lst[:]
 
#print(last_perm(lst))
#print(next_perm(cur))
ans1 = last_perm(lst)
ans2 = next_perm(cur)
 
for i in range(n - 1):
    f1.write(str(ans1[i]))
    f1.write(" ")
f1.write(str(ans1[-1]))
f1.write('\n')
for i in range(n - 1):
    f1.write(str(ans2[i]))
    f1.write(" ")
f1.write(str(ans2[-1]))
 
f.close()
f1.close()
