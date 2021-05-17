#include <bits/stdc++.h>
 
using namespace std;
 
vector <int> a;
 
int find_min(int x){
    int l = -1 , r = a.size() + 1;
 
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
 
int find_max(int x){
    int l = -1 , r = a.size() + 1;
 
    while(r - l > 1){
        int m = (l + r) / 2;
 
        if(a[m] >= x){
            r = m;
        }
        else{
            l = m;
        }
    }
    return r;
}
 
int main(){
    int n, x;
 
    cin >> n;
 
    for(int i = 0 ; i < n ; ++i){
        cin >> x;
        a.push_back(x);
    }
    sort(a.begin() , a.end());
 
 
    int l , r;
 
    cin >> x;
 
    for(int i = 0 ; i < x ; ++i){
        cin >> l >> r;
        int L = min(find_min(r) , n - 1);
        int R = max(find_max(l) , 0);
        cout << max((L - R + 1) , 0) << " ";
    }
 
    return 0;
}