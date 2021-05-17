def solve(l1 , l2 , S):
    n = len(l1)
    j = len(l2) - 1
    i = 0
    
    while(i != n):
        if(l1[i] + l2[j] > S):
            j -= 1
        elif(l1[i] + l2[j] < S):
            i += 1
        else:
            print(i + 1 , j + 1)
            return

l1 = list(map(int,input().split()))
l2 = list(map(int,input().split()))
S = int(input())

print(solve(l1 , l2 , S))
