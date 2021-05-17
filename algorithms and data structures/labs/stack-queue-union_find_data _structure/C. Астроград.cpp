#include <bits/stdc++.h>
 
using namespace std;
 
int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    int n, k;
    cin >> n;
    vector<int> a;
    for (int i = 0; i < n; ++i) {
        cin >>k;
        if (k == 1) {
            cin >> k;
            a.push_back(k);
        }
        else if (k == 2) {
            a.erase(a.begin());
        }
        else if (k == 3) {
            a.pop_back();
        }
        else if (k == 4) {
            cin >> k;
            for (int i = 0; i < a.size(); ++i) {
                if (a[i] == k) {
                   cout << i << '\n';
                   break;
                }
            }
        } else {
            cout << a[0] << '\n';
        }
    }
    return 0;
}