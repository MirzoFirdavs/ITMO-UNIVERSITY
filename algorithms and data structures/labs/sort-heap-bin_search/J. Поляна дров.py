def check(x, V1 , V2 , C):
    return (((1 - C) ** 2 + x ** 2) ** (1 / 2)) / V1 + (((C * C) + (1 - x) ** 2) ** (1/2)) / V2
def solve():
    V1 , V2 = map(float,input().split())
    C = float(input())
    l = 0
    r = 1
    eps = 0.00001
    while(r - l > eps):
        m1 = (2 * l + r) / 3
        m2 = (2 * r + l) / 3
        if(check(m1, V1, V2, C) < check(m2, V1, V2, C)):
            r = m2
        else:
            l = m1
    print(round(r , 6))
    
solve()
