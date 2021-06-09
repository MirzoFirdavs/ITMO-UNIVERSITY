#include <bits/stdc++.h>
 
using namespace std;
 
int n, N;
long long t[400004];
long long a[400004];
long long e = LONG_LONG_MAX;
 
long long build(long long i) {
    if (i >= N - 1) {
        if (i >= N + n - 1) {
            return t[i] = e;
        }
        return t[i];
    }
    return t[i] = min(build(2 * i + 1), build(2 * i + 2)); 
}
 
long long get(long long l, long long r, long long i, long long lx, long long rx) {
    if (l >= rx || lx >= r) {
        return e;
    }
    if (lx >= l && rx <= r) {
        return t[i];
    }
    if (a[i] != e) {
        a[2 * i + 1] = a[i];
        t[2 * i + 1] = a[i];
        a[2 * i + 2] = a[i];
        t[2 * i + 2] = a[i];
        a[i] = e;
    }
    long long m = (lx + rx) / 2;
    return min(get(l, r, 2 * i + 1, lx, m), get(l, r, 2 * i + 2, m, rx));
}
 
void update(long long l, long long r, long long v, long long i, long long lx, long long rx) {
    if (l >= rx or lx >= r) {
        return;
    }
    if (lx >= l and rx <= r) {
        a[i] = v;
        t[i] = v;
        return;
    }
    if (a[i] != e) {
        a[2 * i + 1] = a[i];
        t[2 * i + 1] = a[i];
        a[2 * i + 2] = a[i];
        t[2 * i + 2] = a[i];
        a[i] = e;
    }
    long long m = (lx + rx) / 2;
    update(l, r, v, 2 * i + 1, lx, m);
    update(l, r, v,2 * i + 2, m, rx);
    t[i] = min(t[2 * i + 1], t[2 * i + 2]);
}
int main() {
    long long m;
    cin >> n >> m;
    N = pow(2, (ceil(log(n) / log(2))));
    long long t[2 * N];
    long long a[2 * N];
    for (long long i = 0; i < 2 * N; ++i) {
        a[i] = e;
    }
    for (int i = N - 1; i < N + n - 1; i++) {
        t[i] = 0;
    }
    build(0);
    long long x, y, z;
    long long b;
    for (int i = 0; i < m; ++i) {
        cin >> b;
        if (b == 1) {
            cin >> x >> y >> z;
            update(x + N - 1, y + N - 1, z, 0, N - 1, 2 * N - 1);
        } else {
            cin >> x >> y;
            cout << get(x + N - 1, y + N - 1, 0, N - 1, 2 * N - 1) << endl;
        }
    }
    return 0;
}