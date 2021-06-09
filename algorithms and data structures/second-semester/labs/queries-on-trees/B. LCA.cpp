#include <iostream>
#include <vector>
 
using namespace std;
 
int main()
{
    int n, m, t, cur = 0, j = 1, u, v;
    
    cin >> n;
    
    vector<int> cur_d(n);
    vector<vector<int>> dp(n);
    dp[0].push_back(0);
 
    for (int i = 1; i < n; i++) {
        int t;
        cin >> t;
        dp[i].push_back(t - 1);
        cur_d[i] = cur_d[t - 1] + 1;
    }
 
    while(1 << (j - 1) < n) {
        for (int i = 0; i < n; i++) {
            dp[i].push_back(dp[dp[i][j - 1]][j - 1]);
        }
        ++cur;
        ++j;
    }
 
    cin >> m;
 
    for (int i = 0; i < m; i++) {
        cin >> u >> v;
        
        u --;
        v--;
        
        if (cur_d[v] > cur_d[u]) {
            swap(u, v);
        }
 
        for (int i = cur; i >= 0; i--) {
            if (cur_d[v] <= cur_d[u] - (1 << i)) {
                u = dp[u][i];
            }
        }
 
        if (u != v) {
            for (int i = cur; i >= 0; i--) {
                if (dp[v][i] != dp[u][i]) {
                    v = dp[v][i];
                    u = dp[u][i];
                }
            }
            cout << dp[v][0] + 1 << endl;
        } else {
            cout << v + 1 << endl;
        }
    }
    return 0;
}