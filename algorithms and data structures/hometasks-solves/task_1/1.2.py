def solve(a , b):
    n = len(a)
    m = len(b)

    i = 0
    j = 0

    x = []
    
    while(True):
        if(abs(a[i + 1] - b[j]) < abs(a[i] - b[j])):
            x.append([abs(a[i + 1] - b[j]) , i + 1 , j])
            i += 1
        else:    
            x.append([abs(a[i] - b[j]) , i , j])
            j += 1

        if(i == n - 1 or j == m - 1):
            break

        x.append([abs(a[-1] - b[-1]) , n - 1 , m - 1])

    m_n = 100000000000000

    for i in range(len(x)):
        m_n = min(m_n , x[i][0])

    for i in range(len(x)):
        if(x[i][0] == m_n):
            print(x[i][1] + 1 , x[i][2] + 1)
            return

l1 = list(map(int,input().split()))
l2 = list(map(int,input().split()))

solve(l1 , l2)
