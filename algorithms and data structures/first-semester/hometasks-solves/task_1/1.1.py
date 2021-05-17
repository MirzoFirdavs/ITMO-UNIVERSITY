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

def chek(x):
    n = len(x)
    
    for i in range(n - 1):
        if(x[i] == x[i + 1]):
            return True
    return False

l1 = list(map(int,input().split()))
l2 = list(map(int,input().split()))

ans = list(merge(l1,l2))

print(chek(ans))
