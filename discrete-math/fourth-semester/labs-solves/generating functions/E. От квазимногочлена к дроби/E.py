def func(a, b, m):
    result = [0 for _ in range(min(m, len(a) + len(b) - 1))]
    
    for ii in range(len(a)):
        jj = 0
        while jj < len(b) and ii + jj < m:
            result[ii + jj] += a[ii] * b[jj]
            jj += 1
    
    return result
 
 
r = int(input())
d = int(input())
p = list(map(int, input().split()))
 
denominator, temp, c = [1, -r], [1, -r], [0 for _ in range(d + 1)]
 
for i in range(d):
    denominator = func(denominator, temp, d + 2)
 
for i in range(d + 1):
    x = 1
    for j in range(d + 1):
        c[i] += x * p[j] * (r ** i)
        x *= i
 
numerator = func(c, denominator, d + 1)
 
print(len(numerator) - 1)
print(*numerator)
print(len(denominator) - 1)
print(*denominator)