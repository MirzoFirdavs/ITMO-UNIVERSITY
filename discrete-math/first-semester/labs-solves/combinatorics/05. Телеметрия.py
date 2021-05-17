f = open("telemetry.in", "r")
f1 = open("telemetry.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
n, k = map(int,f.readline().split())
#n, k = map(int,input().split())
 
a, b, c = [0 for i in range(n)], [1 for i in range(n)], [1 for i in range(n)]
 
def gen():
    for i in range(k ** n):
        ans = ""
        for j in range(n):
            ans +=str(a[j])
        for j in range(n):
            if(c[j] == k ** j):
                a[j] += 1 * b[j]
                if(a[j] == k):
                    a[j] = k - 1
                    b[j] = -1
                if(a[j] == -1):
                    a[j] = 0
                    b[j] = 1
                c[j] = 1
            else:
                c[j] += 1
        #print(ans)
        f1.write(ans)
        f1.write('\n')
 
gen()
 
f.close()
f1.close()
