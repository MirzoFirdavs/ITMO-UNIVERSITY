#include <bits/stdc++.h>
 
using namespace std;
 
int n, N;
long long t[400004];
long long a[400004];
long long e = LONG_LONG_MAX;
 
long long build(int i) {
    if (i >= N - 1) {
        if (i >= N + n - 1) {
            return t[i] = e;
        }
        return t[i];
    }
    return t[i] = min(build(2 * i + 1), build(2 * i + 2)); 
}
 
void push(int i) {
    if (a[i] != 0) {
        a[2 * i + 1] += a[i];
        t[2 * i + 1] += a[i];
        a[2 * i + 2] += a[i];
        t[2 * i + 2] += a[i];
        a[i] = 0;
    }
}
 
long long get(int l, int r, int i, int lx, int rx) {
    if (l >= rx || lx >= r) {
        return e;
    }
    if (lx >= l && rx <= r) {
        return t[i];
    }
    push(i);
    return min(get(l, r, 2 * i + 1, lx, (lx + rx) / 2), get(l, r, 2 * i + 2, (lx + rx) / 2, rx));
}
 
void add(int l, int r, long v, int i, int lx, int rx) {
    if (l >= rx || lx >= r) {
        return;
    }
    if (lx >= l && rx <= r) {
        a[i] += v;
        t[i] += v;
        return;
    }
    push(i);
    add(l, r, v, 2 * i + 1, lx, (lx + rx) / 2);
    add(l, r, v,2 * i + 2, (lx + rx) / 2, rx);
    t[i] = min(t[2 * i + 1], t[2 * i + 2]);
}
int main() {
    int m;
    cin >> n >> m;
    N = pow(2, (ceil(log(n) / log(2))));
    long long t[2 * N];
    long long a[2 * N];
    for (int i = N - 1; i < N + n - 1; i++) {
        t[i] = 0;
    }
    build(0);
    int x, y;
    long long z;
    int b;
    for (int i = 0; i < m; ++i) {
        cin >> b;
        if (b == 1) {
            cin >> x >> y >> z;
            add(x + N - 1, y + N - 1, z, 0, N - 1, 2 * N - 1);
        } else {
            cin >> x >> y;
            cout << get(x + N - 1, y + N - 1, 0, N - 1, 2 * N - 1) << endl;
        }
    }
    return 0;
}