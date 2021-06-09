#include <bits/stdc++.h>
 
using namespace std;
 
 
void s_t(int i, int x, int n, int t[]) {
    i += n - 1;
    t[i] = x;
    while (i > 0) {
        i = (i - 1) / 2;
        t[i] = max(t[2 * i + 1], t[2 * i + 2]);
    }
}
 
int maxSimple(int val, int x, int n, int t[]) {
    if (x >= n - 1) {
        return x - n + 1;
    }
    if (t[2 * x + 1] >= val) {
        return maxSimple(val, 2 * x + 1, n, t);
    } else if (t[2 * x + 2] >= val) {
        return maxSimple(val, 2 * x + 2, n, t);
    }
    return -1;
}
 
int m_x(int val, int l, int r, int x, int lx, int rx, int n, int t[]) {
    if (t[x] < val || l >= rx || lx >= r) {
        return -1;
    }
    if (lx >= l && rx <= r) {
        return maxSimple(val, x, n, t);
    }
    int m = (lx + rx) / 2;
    int k = m_x(val, l, r, 2 * x + 1, lx, m, n, t);
    if (k != -1) {
        return k;
    }
    k = m_x(val, l, r, 2 * x + 2, m, rx, n, t);
    return k;
}
 
int main() {
    int k, m;
    cin >> k >> m;
    int b;
    int n = pow(2, ceil(log(k) / log(2)));
    int t[2 * n];
    for (int i = 0; i < k; ++i) {
        cin >> b;
        s_t(i, b, n, t);
    }
    for (int i = k; i < n; ++i) {
        s_t(i, 0, n, t);
    }
    int x, y, z;
    for (int i = 0; i < m; ++i) {
        cin >> x >> y >> z;
        if (x == 1) {
            s_t(y, z, n, t);
        } else {
            cout << m_x(y, z + n - 1, 2 * n -1, 0, n - 1, 2 * n - 1, n, t) << endl;
        }
    }
    return 0;
    
}