#include<bits/stdc++.h>
 
using namespace std;
 
pair<long long, long long> a[100001];
pair<long long, long long> tree[400004];
pair<long long, long long> get_min(long long ql, long long qr, long long v, long long l, long long r) {
    if(r < ql or qr < l) {
        return {INT_MAX,INT_MAX};
    } 
    if (ql <= l and r <= qr) {
        return tree[v];
    }
 
    long long m = (l + r) / 2;
    pair<long long, long long> x = get_min(ql, qr, v * 2, l, m);
    pair<long long, long long> y = get_min(ql, qr, v * 2 + 1, m + 1, r);
    if(x.first == y.first){
        x.second += y.second;
        return x;
    } else{
       return min(y, x);
    }
}
 
pair <long long, long long> combine(pair <long long, long long> a, pair <long long, long long> b) {
    if(a.first == b.first){
        return make_pair(a.first, a.second + b.second);
    } else {
        return min(a, b);
    } 
}
void build_tree (long long v, long long l, long long r) {
    if(l == r){
        tree[v] = a[l];
   }
   else {
        long long m = (l + r) / 2;
        build_tree(v * 2, l, m);
        build_tree(v * 2 + 1, m + 1, r);
        tree[v] = combine(tree[v * 2], tree[v * 2 + 1]);
    }
}
 
void update(long long idx, long long val, long long v, long long tl, long long tr) {
    if (idx <= tl && tr <= idx) {      
        a[idx].first = val;
        tree[v].first = val;
        return;
    }
    if (tr < idx || idx < tl) {
        return;
    }
    long long m = (tl + tr) / 2;
    update(idx, val, v * 2, tl, m);
    update(idx, val, v * 2 + 1, m + 1, tr);
    tree[v] = combine(tree[v * 2], tree[v * 2 + 1]);
}
 
int main() {
    long long m, n, x, y, z;
    cin >> n >> m;
    
    for(int i = 0; i < n; i++) {
       cin >> a[i].first;
       a[i].second = 1;
    }
    build_tree(1, 0, n - 1);
    pair<long long, long long> ans;
    for(int i = 0; i < m; i++) {
        cin >> x >> y >> z;
        if(x == 1){
            update(y, z, 1, 0, n - 1);
        } else {
            ans = get_min(y, z - 1, 1, 0, n - 1);
            cout << ans.first << " " << ans.second << endl;
       }
    }                   
    return 0;
}