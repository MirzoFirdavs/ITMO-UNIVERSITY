#include <bits/stdc++.h>
 
using namespace std;
 
unsigned long long n;
unsigned long long a[100000];     
unsigned long long tree[400004];  
 
void build(unsigned long long v, unsigned long long tl, unsigned long long tr) {
    if (tl == tr) {
        tree[v] = a[tl];    
    } else {
        int tm = (tl + tr) / 2;
        build(v * 2, tl, tm);
        build(v * 2 + 1, tm + 1, tr);
        tree[v] = tree[v * 2] + tree[v * 2 + 1];
    }
}
 
unsigned long long s_m(unsigned long long l, unsigned long long r, unsigned long long v, unsigned long long tl, unsigned long long tr) {
    if (l <= tl && tr <= r) {
        return tree[v];
    }
    if (tr < l || r < tl) {
        return 0;
    }
    unsigned long long tm = (tl + tr) / 2;
    return s_m(l, r, v * 2, tl, tm) + s_m(l, r, v * 2 + 1, tm + 1, tr);
}
 
void s_t(unsigned long long idx, unsigned long long val, unsigned long long v, unsigned long long tl, unsigned long long tr) {
    if (idx <= tl && tr <= idx) {
        a[idx] = val;
        tree[v] = val;
        return;
    }
    if (tr < idx || idx < tl) {
        return;
    }
    unsigned long long tm = (tl + tr) / 2;
    s_t(idx, val, v * 2, tl, tm);
    s_t(idx, val, v * 2 + 1, tm + 1, tr);
    tree[v] = tree[v * 2] + tree[v * 2 + 1];
}
 
int main() {
    unsigned long long m;
    cin >> n >> m;
    unsigned long long x, y, z;
    
    for (int i = 0; i < n; ++i) {
        cin >> a[i];
    }
    
    build(1, 0, n - 1); 
    
    
    for (int i = 0; i < m; ++i) {
        cin >> x >> y >> z;
        if (x == 1) {
            s_t(y, z, 1, 0, n - 1);
        } else {
            cout << s_m(y, z - 1, 1, 0, n - 1) << endl;
        }
    }
    
    /*
    for (int i = 0; i < 2 * n; ++i) {
        cout << tree[i] << " ";
    }*/
}