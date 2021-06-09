#include <iostream>
#include <vector>
 
using namespace std;
 
long long n = 0;
struct Tree{
    long long min_val, max_val;
};
vector<Tree> t(16000000, {LONG_LONG_MAX, LONG_LONG_MAX});
 
void update(long long l, long long val, long long x, long long lx, long long rx) {
    if (rx < l || l < lx)
        return;
    if (rx == lx) {
        t[x] = {val, val};
        return;
    }
    long long mm = (lx + rx) / 2;
    update(l, val, x * 2, lx, mm);
    update(l, val, x * 2 + 1, mm + 1, rx);
    t[x] = {min(t[2*x].min_val, t[2 * x + 1].min_val), max(t[2 * x].max_val, t[2 * x + 1].max_val)};
}
 
 
long long get(long long l, long long r, long long val, long long x, long long lx, long long rx) {
    if (rx < l || r < lx || val < t[x].min_val)
        return 0;
    if (t[x].max_val <= val && rx <= r && lx >= l) {
        for (long long i = lx; i <= rx; i++) {
            update(i, LONG_LONG_MAX, 1, 0, n - 1);
        }
        return rx - lx + 1;
    }
    long long mm = (lx + rx)/2;
    long long sum = 0;
    if (t[2*x].min_val <= val) {
        sum += get(l, r, val, 2 * x, lx, mm);
    }
    if (t[2*x + 1].min_val <= val) {
        sum += get(l, r, val, 2 * x + 1, mm + 1, rx);
    }
    return sum;
}
 
int main() {
    long long m, op, first, second, val;
    cin >> n >> m;
    for (long long i = 0; i < m; i++) {
        cin >> op >> first >> second;
        if (op == 1){
            update(first, second, 1, 0, n - 1);
        }
        else{
            cin >> val;
            cout << get(first, second - 1, val, 1, 0, n - 1);
            cout << '\n';
        }
    }
    return 0;
}