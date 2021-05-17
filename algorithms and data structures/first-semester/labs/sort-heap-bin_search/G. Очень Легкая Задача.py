x , y , z = map(int , input().split())
 
l = 0
r = x * y
 
while(r - l) > 0:
    
    m = (l + r) // 2
    if ((m // y) + (m // z)) >= x - 1:
        r = m
    else:
        l = m + 1
 
print(min(r + y , r + z))
