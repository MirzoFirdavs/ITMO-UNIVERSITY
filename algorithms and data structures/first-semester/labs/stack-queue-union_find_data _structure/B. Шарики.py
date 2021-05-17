a = list(map(int, input().split()))
i = 0
cnt = 0
k = 0
while(k < len(a)):
    j = k
    while(j < len(a) and a[j] == a[k]):
        j+=1
    if(j - k>=3):
        cnt += (j-k)
        a = a[:k]+a[j:]        
        k = 0
    else:
        k += 1        
print(cnt)
