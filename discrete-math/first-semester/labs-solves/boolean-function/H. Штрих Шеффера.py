ans = []
 
def build(n):
    if(n == 1):
        ans.append("((((A0|B0)|(A0|B0))|((A1|A1)|(B1|B1)))|(A1|B1))")
    else:
        ans.append("((")
        build(n - 1)
        ans.append("|((A" + str(n) + "|A" + str(n) + ")|(B" + str(n) + "|B" + str(n) + ")))|(A" + str(n) + "|B" + str(n) + "))")
    return ans
 
n = int(input())
 
if(n == 1):
    print("((A0|B0)|(A0|B0))")
    exit()
 
build(n - 1)
 
ANS = ""
 
for i in range(len(ans)):
    ANS += ans[i]
 
print(ANS)
