#include <bits/stdc++.h>
 
using namespace std;
 
struct Node {
    long long s_m;
    long long s_t;
    long long a_d;
};
 
vector<Node> tree;
 
void push_set(long long v, long long lx, long long rx) {
    if (tree[v].s_t == LLONG_MAX) {
        return;
    }
    tree[v].s_m = (rx - lx) * tree[v].s_t;
    if (rx - lx != 1) {
        tree[2 * v + 1].s_t = tree[2 * v + 2].s_t = tree[v].s_t;
        tree[2 * v + 1].a_d = tree[2 * v + 2].a_d = 0;
    }
    tree[v].s_t = LLONG_MAX;
}
    
void push_add(long long v, long long lx, long long rx) {
    if (tree[v].a_d == 0) {
        return;
    }
    tree[v].s_m += (rx - lx) * tree[v].a_d;
    if (rx - lx != 1) {
        tree[2 * v + 1].a_d += tree[v].a_d;
        tree[2 * v + 2].a_d += tree[v].a_d;
        long long m = (lx + rx) / 2;
        push_set(2 * v + 1, lx, m);
        push_set(2 * v + 2, m, rx);
        tree[2 * v + 1].s_t = tree[2 * v + 2].s_t = LLONG_MAX;
    }
    tree[v].a_d = 0;
}
    
void update_set(long long l, long long r, long long val, long long v, long long lx, long long rx) {
    if (tree[v].a_d == 0) {
        push_set(v, lx, rx);
    } else {
        push_add(v, lx, rx);
    }
    if (l >= rx or r <= lx) {
        return;
    }
    if (lx >= l and rx <= r) {
        tree[v].s_t = val;
        if (tree[v].a_d == 0) {
            push_set(v, lx, rx);
        } else {
            push_add(v, lx, rx);
        }
        return;
    }
    long long m = (lx + rx) / 2;
    update_set(l, r, val, 2 * v + 1, lx, m);
    update_set(l, r, val, 2 * v + 2, m, rx);
    tree[v] = {tree[2 * v + 1].s_m + tree[2 * v + 2].s_m, LLONG_MAX, 0};
}
    
void update_add(long long l, long long r, long long val, long long v, long long lx, long long rx) {
    if (tree[v].a_d == 0) {
        push_set(v, lx, rx);
    } else {
        push_add(v, lx, rx);
    }
    if (l >= rx or r <= lx) {
        return;
    }
    if (lx >= l and rx <= r) {
        tree[v].a_d = val;
        if (tree[v].a_d == 0) {
            push_set(v, lx, rx);
        } else {
            push_add(v, lx, rx);
        }
        return;
    }
    long long m = (lx + rx) / 2;
    update_add(l, r, val, 2 * v + 1, lx, m);
    update_add(l, r, val, 2 * v + 2, m, rx);
    tree[v] = {tree[2 * v + 1].s_m + tree[2 * v + 2].s_m, LLONG_MAX, 0};
}
    
long long get(long long l, long long r, long long v, long long lx, long long rx) {
    if (tree[v].a_d == 0) {
        push_set(v, lx, rx);
    } else {
        push_add(v, lx, rx);
    }
    if (l >= rx or r <= lx) {
        return 0;
    }
    if (lx >= l and rx <= r) {
        return tree[v].s_m;
    }   
    long long m = (lx + rx) / 2;
    long long left = get(l, r, 2 * v + 1, lx, m);
    long long right = get(l, r, 2 * v + 2, m, rx);
    return (left + right);
}
 
int main() {
    long long l, r, value; 
    long long n, m;
    cin >> n >> m;
    long long f = 1;
    while (f < n) {
        f *= 2;
    }
    tree.assign(2 * f - 1, {0, LLONG_MAX, 0});
    long long i = 0;
    while (i < m) {
        long long c;
        cin >> c;
        if (c == 1) {
            cin >> l >> r >> value;
            update_set(l, r, value, 0, 0, f);
        } else if (c == 2) {
            cin >> l >> r >> value;
            update_add(l, r, value, 0, 0, f);
        } else {
            cin >> l >> r;
            cout << get(l, r, 0, 0, f) << endl;
        }
        ++i;
    }
    return 0;
}