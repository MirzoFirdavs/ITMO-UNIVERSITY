f = open("chaincode.in", "r")
f1 = open("chaincode.out", "w")
 
n = int(f.readline())
 
m = {}
 
a = "0" * n
 
m[a] = 1
 
while(True):
    f1.write(str(a))
    f1.write('\n')
    a = a[1:]
    a += "1"
    b = 0
    try:
        if(m[a]):
            a = a[:n - 1]
            a += "0"
            try:
                if(m[a]):
                    b = 1
                    break
            except:
                m[a] = 1
    except:
        m[a] = 1
    if b: break
     
 
f.close()
f1.close()
