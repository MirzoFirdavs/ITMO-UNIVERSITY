#include <bits/stdc++.h>
 
using namespace std;
 
long long n;
long long a[100000];     
long long tree[400004];  
 
void build(long long v, long long tl, long long tr) {
    if (tl == tr) {
        tree[v] = a[tl];    
    } else {
        long long tm = (tl + tr) / 2;
        build(v * 2, tl, tm);
        build(v * 2 + 1, tm + 1, tr);
        tree[v] = tree[v * 2] + tree[v * 2 + 1];
    }
}
 
long long kth(long long v, long long tl, long long tr, long long k) {
    if (k > tree[v])
        return -1;
    if (tl == tr)
        return tl;
    int tm = (tl + tr) / 2;
    if (tree[v * 2] > k)
        return kth(v * 2, tl, tm, k);
    else
        return kth(v * 2 + 1, tm + 1, tr, k - tree[v * 2]);
}
 
void s_t(long long idx, long long v, long long tl, long long tr) {
    if (idx <= tl && tr <= idx) {
        if(a[idx] == 0) {
            a[idx] = 1;
            tree[v] = 1;
            return;
        } else {
            a[idx] = 0;
            tree[v] = 0;
            return;
        }
    }
    if (tr < idx || idx < tl) {
        return;
    }
    
    long long tm = (tl + tr) / 2;
    s_t(idx, v * 2, tl, tm);
    s_t(idx, v * 2 + 1, tm + 1, tr);
    tree[v] = tree[v * 2] + tree[v * 2 + 1];
}
 
int main() {
    long long m;
    cin >> n >> m;
    long long x, y;
    
    for (int i = 0; i < n; ++i) {
        cin >> a[i];
    }
    
    build(1, 0, n - 1); 
    
    for (int i = 0; i < m; ++i) {
        cin >> x >> y;
        if (x == 1) {
            s_t(y, 1, 0, n - 1);
        } else {
            cout << kth(1, 0, n - 1, y) << endl;
        }
    }
    
    /*
    for (int i = 0; i < 2 * n; ++i) {
        cout << tree[i] << " ";
    }*/
    return 0;
}