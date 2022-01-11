def bin_search(x):
    l = -1
    r = len(x)
    while(r > 1 + l):
        m = (l + r) // 2
        print(1, x[m], i)
        st = str(input())
        if (st[0] == "Y"):
            l = m
        else:
            r = m
    return r

n = int(input())
x = [1]
for i in range(2, n + 1):
    x.insert(bin_search(x), i)
print(0, *x)
