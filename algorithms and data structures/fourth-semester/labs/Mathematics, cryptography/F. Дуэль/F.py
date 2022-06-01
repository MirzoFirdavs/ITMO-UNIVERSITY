import math
 
PI = 3.14159265358979323846
 
 
def fft(a, flag):
    if len(a) == 1:
        return
 
    a_0 = [0 for _ in range(len(a) // 2)]
    a_1 = [0 for _ in range(len(a) // 2)]
 
    i, j = 0, 0
 
    while i < len(a):
        a_0[j] = a[i]
        a_1[j] = a[i + 1]
        j += 1
        i += 2
 
    fft(a_0, flag)
    fft(a_1, flag)
 
    arg = (2 * PI) / len(a) * (-1 if flag else 1)
    w = complex(1, 0)
    deg = complex(math.cos(arg), math.sin(arg))
 
    for i in range(len(a) // 2):
        a[i] = a_0[i] + w * a_1[i]
        a[i + len(a) // 2] = a_0[i] - w * a_1[i]
 
        if flag:
            a[i] /= 2
            a[i + len(a) // 2] /= 2
 
        w *= deg
 
 
def mul(a, b, result):
    f_a = a[:]
    f_b = b[:]
 
    size = 1
 
    while size < max(len(a), len(b)):
        size *= 2
 
    size *= 2
 
    f_a += [0 for _ in range(size - len(f_a))]
    f_b += [0 for _ in range(size - len(f_b))]
 
    fft(f_a, False)
    fft(f_b, False)
 
    for i in range(size):
        f_a[i] *= f_b[i]
 
    fft(f_a, True)
 
    for element in f_a:
        result.append(int(element.real + 0.5))
 
    return result
 
 
lst = list(input())
 
x = [int(lst[_]) for _ in range(len(lst))]
x.reverse()
 
ans = 0
 
k = mul(x, x, [])
 
for ii in range(len(x)):
    if x[ii] != 1:
        continue
    ans += k[2 * ii] // 2
 
print(ans)