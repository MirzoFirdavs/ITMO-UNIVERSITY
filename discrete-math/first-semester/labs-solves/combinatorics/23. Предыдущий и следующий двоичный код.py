f = open("nextvector.in", "r")
f1 = open("nextvector.out", "w")
#f = open("input.txt", "r")
#f1 = open("output.txt", "w")
 
#n = str(input())
n = str(f.readline().strip())
 
if(n == "1"):
    #print(0)
    #print("-")
    f1.write("0")
    f1.write('\n')
    f1.write("-")
    f.close()
    f1.close()
    exit()
     
if (len(set(n)) == 1 and n[0] == "0"):
    #print("-")
    #print("0" * (len(n) - 1) + "1")
    f1.write("-")
    f1.write('\n')
    f1.write("0" * (len(n) - 1) + "1")
     
elif(len(set(n)) == 1 and n[0] == "1"):
    #print("1" * (len(n) - 1) + "0")
    #print("-")
    f1.write("1" * (len(n) - 1) + "0")
    f1.write("\n")
    f1.write("-")
     
else:
    cur1, cur2 = 0, 0
    for i in range(len(n) - 1, -1, -1):
        if(n[i] == "1"):
            cur1 = i
            break
    for i in range(len(n) - 1, -1, -1):
        if(n[i] == "0"):
            cur2 = i
            break
    #print(cur1, cur2)
    ans1 = n[:cur1] + "0" + "1" * (len(n) - cur1 - 1)
    ans2 = n[:cur2] + "1" + "0" * (len(n) - cur2 - 1)
    #print(ans1, ans2)
    f1.write(ans1)
    f1.write('\n')
    f1.write(ans2)
     
f.close()
f1.close()
