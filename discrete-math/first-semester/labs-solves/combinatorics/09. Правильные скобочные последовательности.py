#f = open("brackets.in", "r")
#f1 = open("brackets.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
def gen(n, cntop, cntcl, ans):
    if(cntop + cntcl == 2 * n):
        print(ans)
        #f1.write(ans)
        #f1.write('\n')
        return
    if(cntop < n):
        gen(n, cntop + 1, cntcl, ans + "(")
    if(cntop > cntcl):
        gen(n, cntop, cntcl + 1, ans + ")")
 
#n = int(f.readline())
n = int(input())

gen(n, 0, 0, "")
 
#f.close()  
#f1.close()
