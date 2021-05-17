#include <bits/stdc++.h>
 
using namespace std;
 
int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    int n;
    vector< vector<long long> > st;
    vector <long long> cur;
    cin >> n;
    long long k;
    cin >> k;
    if (k == 1) {
        cin >> k;
        cur.push_back(k);
        cur.push_back(k);
        st.push_back(cur);
    }
    long long m_n = 10e10;
    for (int i = 1; i < n; ++i) {
        vector <long long> x;
        cin >> k;
        if (k == 1){
            cin >> k;
            if (st.size() == 0) {
                x.push_back(k);
                x.push_back(k);
                st.push_back(x);
            } else {
                x.push_back(min(k, st[st.size() - 1][0]));
                x.push_back(k);
                st.push_back(x);
            }
        }
        else if (k == 2) {
            st.pop_back();
        }
        else {
            cout << st[st.size() - 1][0] << '\n';
        }
    }
 
}