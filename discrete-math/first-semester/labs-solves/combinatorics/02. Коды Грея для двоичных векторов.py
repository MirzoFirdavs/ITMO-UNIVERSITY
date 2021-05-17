f = open("gray.in", "r")
f1 = open("gray.out", "w")
 
n = int(f.readline())
 
for i in range(0, 1 << n):
    gray = i ^ (i >> 1)
    st = []
    while(gray != 0):
        st.append(str(gray % 2))
        gray //= 2
    st.reverse()
    z = "".join(st)
    z = "0" * (n - len(z)) + z
    f1.write(str(z))
    f1.write("\n")
 
f.close()
f1.close()
