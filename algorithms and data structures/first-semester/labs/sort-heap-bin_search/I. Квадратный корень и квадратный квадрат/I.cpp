#include <bits/stdc++.h>
 
using namespace std;
 
int main(){
    cout.precision(12);
    double c;
 
    cin >> c;
 
    double l = 0 , r = 100000002;
 
    while(r - l > 0.000001){
        double m = (l + r) / 2;
        if(pow(m , 2) + pow(m , 0.5) < c){
            l = m;
        }
        else{
            r = m;
        }
    }
    cout << l;
    return 0;
}