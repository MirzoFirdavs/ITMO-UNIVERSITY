#include <bits/stdc++.h>
 
using namespace std;
 
int n;
vector <long long> a;
 
int find_min(long long x){
    int l = -1, r = a.size() + 1;
 
    while(r - l > 1){
        int m = (l + r) / 2;
 
        if(a[m] <= x){
            l = m;
        }
        else{
            r = m;
        }
    }
    return l;
}
 
int find_max(long long x){
    int l = -1, r = a.size() + 1;
 
    while(r - l > 1){
        int m = (l + r) / 2;
 
        if(a[m] >= x){
            r = m;
        }
        else{
            l = m;
        }
    }
    //cout << "max " << r << endl;
    return r;
}
 
int main(){
    int k , x;
 
    cin >> n >> k;
 
    vector <long long> lst2(k);
 
    for(int i = 0 ; i < n ; ++i){
        cin >> x; 
        a.push_back(x);
    }
 
    for(int i = 0 ; i < k ; ++i){
        cin >> lst2[i];
    }
 
    for(int i = 0 ; i < k ; ++i){
        long long cur1 = min(find_min(lst2[i]) , n - 1);
        long long cur2 = max(find_max(lst2[i]) , 0);
        //cout << cur1 << " " << cur2 << endl;
        if(abs(a[cur1] - lst2[i]) <= abs(a[cur2] - lst2[i])){
            cout << a[cur1] << endl;
        }
        else{
            cout << a[cur2] << endl;
        }
 
    }
    
    return 0;
}