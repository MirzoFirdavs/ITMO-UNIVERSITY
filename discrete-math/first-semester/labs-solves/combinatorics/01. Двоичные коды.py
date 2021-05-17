f = open("allvectors.in", "r")
f1 = open("allvectors.out", "w")
 
n = int(f.readline())
 
st = []
 
for i in range((2 ** n) - 1):
    while(i != 0):
        st.append(str(i % 2))
        i //= 2
    st.reverse()
    z = "".join(st)
    z = "0" * (n - len(z)) + z
    if(i == (2 ** n) - 1):
        f1.write(str(z))
        break
    else:
        f1.write(str(z))
        f1.write('\n')
    st = []
f1.write("1" * n)
f.close()
f1.close()
