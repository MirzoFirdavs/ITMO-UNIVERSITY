f = open("antigray.in", "r")
f1 = open("antigray.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
 
#n = int(input())
n = int(f.readline())
a = [0 for i in range(n)]
s = 3 ** (n - 1)
 
for i in range(s):
    q = i
    for j in range(n):
        a[j] = q % 3
        q //= 3
    for j in range(3):
        ans = ""
        for b in range(len(a)):
            ans += str(a[b])
        #print(ans)
        f1.write(str(ans))
        f1.write('\n')
        for k in range(n):
            a[k] += 1
            a[k] %= 3
     
     
f.close()
f1.close()
