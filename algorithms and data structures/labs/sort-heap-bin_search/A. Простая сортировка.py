def merge(a , b):
 
    n = len(a)
    m = len(b)
 
    i , j , k = 0 , 0 , 0
 
    x = [0 for i in range(n + m)]
    
    while(i < n or j < m):
        if((j == m) or (i < n and a[i] < b[j])):
            x[k] = a[i]
            i += 1
            k += 1
        else:
            x[k] = b[j]
            j += 1
            k += 1
        #print(x)
        #print(a , b)
 
    return x
 
def s_rt(a):
 
    n = len(a)
    
 
    if(n <= 1):
        return a
 
    al = a[0 : n // 2]
    ar = a[n // 2 :]
 
    al = s_rt(al)
    ar = s_rt(ar)
    
    return merge(al , ar)
 
n = int(input())
lst = list(map(int,input().split()))
 
ans = list(s_rt(lst))
 
print(*ans)
 
