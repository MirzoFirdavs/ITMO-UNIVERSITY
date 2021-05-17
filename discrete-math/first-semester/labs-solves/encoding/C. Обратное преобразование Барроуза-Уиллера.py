lst = list(input())
  
n = len(lst) 
  
table = ["" for i in range(n)]
  
for i in range(n):
    for j in range(n):
        table[j] = lst[j] + table[j]
    table.sort()
  
print(table[0])
