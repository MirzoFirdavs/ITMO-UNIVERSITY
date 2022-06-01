mod = 998244353
 
n, m = map(int, input().split())
p = list(map(int, input().split()))
 
 
def get_value(lst, index):
    return lst[index] if index < len(lst) else 0
 
 
def get_reverse(a, mm):
    if mm == 1:
        return a
    
    if mm % 2 == 1:
        return (a * get_reverse(a, mm - 1)) % mod
    else:
        d = get_reverse(a, mm / 2)
        return (d * d) % mod
 
 
def choose(iterator):
    a, b = 1, 1
    iterator -= 1
    
    for i in range(0, iterator + 1, 1):
        a = (a * ((1 - 2 * i + mod) % mod)) % mod
        b = (b * (((i + 1) * 2) % mod)) % mod
    
    return (a * get_reverse(b, mod - 2)) % mod
 
 
def mul(pp):
    result = [0 for _ in range(min(len(pp) + len(p) + 3, m))]
    
    for i in range(0, min(len(pp) + len(p) + 3, m), 1):
        for j in range(0, i + 1, 1):
            result[i] = (result[i] + ((get_value(pp, j) * get_value(p, i - j)) % mod)) % mod
    
    degree = min(len(pp) + len(p) + 3, m) - 1
    
    while degree > 0 and result[degree] == 0:
        degree -= 1
    
    result = result[:degree + 1]
    
    return result
 
 
def get_sqrt():
    result = [1] + [0 for _ in range(m - 1)]
    q = [1]
    
    for i in range(1, m, 1):
        q = mul(q)
        c = choose(i)
        for j in range(0, m, 1):
            result[j] = (result[j] + ((c * get_value(q, j)) % mod)) % mod
    
    return result
 
 
def get_exp():
    result = [1] + [0 for _ in range(m - 1)]
    q = [1]
    f = 1
    
    for i in range(1, m, 1):
        q = mul(q)
        f = (f * i) % mod
        for j in range(0, m, 1):
            result[j] = (result[j] + ((((get_reverse(f, mod - 2)) % mod) * get_value(q, j)) % mod)) % mod
    
    return result
 
 
def get_log():
    result = [0 for _ in range(m)]
    q = [1]
    c = mod - 1
    
    for i in range(1, m, 1):
        q = mul(q)
        c = (c * (-1) + mod)
        for j in range(0, m, 1):
            result[j] = (result[j] + ((get_value(q, j) * ((c * get_reverse(i, mod - 2)) % mod)) % mod)) % mod
    
    return result
 
 
print(*get_sqrt())
print(*get_exp())
print(*get_log())