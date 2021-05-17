f = open("nextpartition.in", "r")
f1 = open("nextpartition.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
  
  
#lst = str(input().strip())
lst = str(f.readline().strip())
  
if("+" not in lst):
    #print("No solution")
    f1.write("No solution")
    f.close()
    f1.close()
    exit()
  
st = ""
cur = lst.index("=")
sol = lst[:cur]
adad = []
  
for i in range(cur + 1, len(lst)):
    if(lst[i] != "+"):
        st += lst[i]
    else:
        adad.append(int(st))
        st = ""
  
adad.append(int(st))
  
adad[-1] -= 1
adad[-2] += 1
ans = ""
if(adad[-2] > adad[-1]):
    adad[-2] += adad[-1]
    adad = adad[:- 1]
    for i in range(len(adad)):
        if(adad[i] != 0):
            ans += str(adad[i]) + "+"
    ans = ans[:-1]
else:
    while(adad[-2] * 2 <= adad[-1]):
        adad.append(adad[-1] - adad[-2])
        adad[-2] = adad[-3]
    for i in range(len(adad)):
        if(adad[i] != 0):
            ans += str(adad[i]) + "+"
    ans = ans[:-1]
  
#print(str(sol), "=", ans, sep = "")
f1.write(str(sol))
f1.write("=")
f1.write(str(ans))
  
f.close()
f1.close()
