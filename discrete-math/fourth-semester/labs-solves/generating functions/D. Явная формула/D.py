import math
 
r, k = map(int, input().split())
p = list(map(int, input().split()))
 
r_pow, s_m = [1] + [0 for i in range(10)], []
 
for i in range(1, 11, 1):
    r_pow[i] = r * r_pow[i - 1]
 
for i in range(k + 1):
    res, ff = [1], [0, 1]
    
    for j in range(k):
        ff[0] = j + 1 - i
        result = [0 for _ in range(len(res) + len(ff))]
    
        for g in range(len(res) + len(ff)):
            for gg in range(g + 1):
                result[g] += ((res[gg] if gg < len(res) else 0) * (ff[g - gg] if g - gg < len(ff) else 0))
    
        res = result
    
    rrr = [0 for _ in range(len(res))]
    
    for j in range(len(res)):
        rrr[j] = res[j] * r_pow[k - i] * p[i]
    
    f = rrr
    rr = [0 for _ in range(max(len(s_m), len(f)))]
    
    for j in range(max(len(s_m), len(f))):
        rr[j] = ((s_m[j] if j < len(s_m) else 0) + (f[j] if j < len(f) else 0))
    s_m = rr
 
for i in range(k + 1):
    gc_d = math.gcd(abs(s_m[i]), abs(r_pow[k] * math.factorial(k)))
    print(str(s_m[i] // gc_d) + "/" + str(r_pow[k] * math.factorial(k) // gc_d), end=' ')