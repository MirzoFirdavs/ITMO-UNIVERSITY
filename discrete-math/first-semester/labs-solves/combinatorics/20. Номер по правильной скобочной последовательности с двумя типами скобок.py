f = open("brackets2num2.in", "r")
f1 = open("brackets2num2.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
  
lst = str(f.readline().strip())
#lst = str(input().strip())
n = len(lst) // 2
  
d = [[0 for i in range(n + 1)] for j in range(2 * n + 1)]
   
d[0][0] = 1
  
def gen():
    for i in range(n * 2):
        for j in range(n + 1):
            if(j + 1 <= n):
                d[i + 1][j + 1] += d[i][j]
            if(j > 0):
                d[i + 1][j - 1] += d[i][j]
gen()
 
def get_num():
    st = []
    ans = 0
    x, y, z = 0, 0, 0
    for i in range(2 * n):
        if(lst[i] == "("):
            x += 1
            y += 1
            z += 1
            st.append("(")
        elif(lst[i] == ")"):
            if(y < n):
                cur = d[2 * n - i - 1][z + 1]
                for j in range((2 * n - i - 1 - z - 1) // 2):
                    cur *= 2
                ans += cur
            x -= 1
            z -= 1
            st.pop()
        elif(lst[i] == "["):
            st.append("[")
            if(y < n):
                cur = d[2 * n - i - 1][z + 1]
                for j in range((2 * n - i - 1 - z - 1) // 2):
                    cur *= 2
                ans += cur
            if(x > 0 and st[z - 1] == "("):
                cur = d[2 * n - i - 1][z - 1]
                for j in range((2 * n - i - 1 - z + 1) // 2):
                    cur *= 2
                ans += cur
            y += 1
            z += 1
        else:
            if(y < n):
                cur = d[2 * n - i - 1][z + 1]
                for j in range((2 * n - i - 1 - z - 1) // 2):
                       cur *= 2
                ans += cur
                cur = d[2 * n - i - 1][z + 1]
                for j in range((2 * n - i - 1 - z - 1) // 2):
                    cur *= 2
                ans += cur
            st.pop()
            z -= 1
             
    f1.write(str(ans))
    #print(ans)
 
get_num()
 
f.close()
f1.close()
