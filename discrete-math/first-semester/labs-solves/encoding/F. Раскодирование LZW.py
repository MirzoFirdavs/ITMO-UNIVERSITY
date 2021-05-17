numChars = {}
charsNum = {}
for i in range(97, 123):
    numChars[i - 97] = chr(i)
    charsNum[chr(i)] = i - 97
  
n = int(input())
  
codes = list(map(int, input().split()))
  
lastIndex = 26
 
t = ""
ans = ""
 
j = 0
 
while(j < n):
    if(codes[j] < lastIndex):
        chars = numChars[codes[j]]
        for k in range(len(chars)):
            ch = chars[k]
            t += ch
            try:
                charsNum[t]
            except:
                charsNum[t] = lastIndex
                numChars[lastIndex] = t
                lastIndex += 1
                t = ""
                t += ch
        ans += chars
    else:
        chars = numChars[codes[j - 1]]
        for k in range(len(chars)):
            ch = chars[k]
            t += ch
            try:
                charsNum[t]
            except:
                charsNum[t] = lastIndex
                numChars[lastIndex] = t
                lastIndex += 1
                t = ""
                t += ch
        j -= 1
        t = ""
    j += 1
 
print(ans)
