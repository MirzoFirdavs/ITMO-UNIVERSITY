def update(i, j, q, k, t):
    x = i
    while (x < len(t)):
        y = j
        while (y < len(t[x])):
            z = q
            while (z < len(t[x][y])):
                t[x][y][z] += k
                z = z | (z + 1)
            y = y | (y + 1)
        x = x | (x + 1)
 
def s_m(i, j, q, t):
    sm = 0
    x = i
    while(x >= 0):
        y = j
        while(y >= 0):
            z = q
            while(z >= 0):
                sm += t[x][y][z]
                z = (z & (z + 1)) - 1
            y = (y & (y + 1)) - 1
        x = (x & (x + 1)) - 1
    return sm
 
def fnd(x1, y1, z1, x2, y2, z2, t):
    a = s_m(x2, y2, z2, t)
    b = s_m(x2, y2, z1 - 1, t)
    up  = a - b
    centre = s_m(x1 - 1, y1 - 1, z1 - 1, t)
    centresl = s_m(x1 - 1, y1 - 1, z2, t) - centre
    right = s_m(x2, y1 - 1, z2, t) - s_m(x2, y1 - 1, z1 - 1, t) - centresl
    left = s_m(x1 - 1, y2, z2, t) - s_m(x1 - 1, y2, z1 - 1, t) - centresl
    ans = up - centresl - right - left
    return ans
    
 
def solve():
    n = int(input())
    t = [[[0 for i in range(n)] for j in range(n)] for k in range(n)]
    while(True):
        lst = list(map(int, input().split()))
        m = lst[0]
        if(m == 2):
            x1, y1, z1, x2, y2, z2 = lst[1], lst[2], lst[3], lst[4], lst[5], lst[6]
            print(fnd(x1, y1, z1, x2, y2, z2, t))
        if (m == 1):
            x, y, z, d = lst[1], lst[2], lst[3], lst[4]
            update(x, y, z, d, t)
        if(m == 3):
            break
solve()
