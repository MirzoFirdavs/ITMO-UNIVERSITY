f = open("vectors.in", "r")
f1 = open("vectors.out", "w")
 
n = int(f.readline())
#n = int(input())
st = []
x = []
for i in range(2 ** n):
    while(i != 0):
        st.append(str(i % 2))
        i //= 2
    st.reverse()
    z = "".join(st)
    z = "0" * (n - len(z)) + z
    if("11" not in z):
        x.append(z)
    st = []
 
#print(len(x))
f1.write(str(len(x)))
f1.write('\n')
 
for i in range(len(x) - 1):
    #print(x[i])
    f1.write(str(x[i]))
    f1.write('\n')
 
f1.write(str(x[-1]))
 
f.close()
f1.close()
