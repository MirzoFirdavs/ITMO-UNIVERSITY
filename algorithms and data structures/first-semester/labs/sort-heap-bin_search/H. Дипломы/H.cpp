#include <bits/stdc++.h>
 
using namespace std;
 
long long check(long long n , long long w , long long h){
    long long l = 0, r = n * max(w , h); 
    
    while(r - l > 0){
        long long m = (l + r) / 2;
        
        if((m / w) * (m / h) >= n){
            r = m;
        }
        
        else{
            l = m + 1;
        }
    }
    
    return r;
}
int main(){
    long long w , h , n;
    
    cin >> w >> h >> n;
    
    cout << check(n , w , h);
    
    return 0;
}