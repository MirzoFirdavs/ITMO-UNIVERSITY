#include <bits/stdc++.h>
 
using namespace std;
 
int main(){
    int n;
 
    cin >> n;
 
    vector <int> cnt(101 , 0);
    vector <int> lst(n);
 
    for(int i = 0 ; i < n ; ++i){
        cin >> lst[i];
        cnt[lst[i]]++;
    }
 
    for(int i = 0 ; i < 101 ; ++i){
        for(int j = 0 ; j < cnt[i] ; ++j){
            cout << i << " ";
        }
    }
 
    return 0;
}