l = list(map(str,input().split()))
 
sign = ['+', '-', '*']
x = []
 
for i in range(len(l)):
    if(l[i] in sign):
        if(l[i] == '+'):
            cur = x[-1] + x[-2]
            x.pop()
            x.pop()
            x.append(cur)
            
        elif(l[i] == '-'):
            cur = x[-2] - x[-1]
            x.pop()
            x.pop()
            x.append(cur)
            
        elif(l[i] == '*'):
            cur = x[-1] * x[-2]
            x.pop()
            x.pop()
            x.append(cur)
    else:
        x.append(int(l[i]))
 
print(*x)
