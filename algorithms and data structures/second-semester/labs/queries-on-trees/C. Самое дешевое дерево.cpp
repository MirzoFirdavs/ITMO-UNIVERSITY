#include <bits/stdc++.h>
 
using namespace std;
 
vector<int> depth;
vector<vector<int>> edges;
 
void setDepth(int u, int v) {
    depth[u] = v;
    for (int x: edges[u]) {
        setDepth(x, v + 1);
    }
}
 
int main() {
    int n;
    cin >> n;
    vector<int> parent(n);
    edges.resize(n);
    depth.resize(n);
    int logN = static_cast<int>((log(n) / log(2)) + 1);
    vector<int> degree_(logN + 1);
    int dp[n][logN + 1];
    int dw[n][logN + 1];
 
    for (int i = 1; i < n; ++i) {
        int u, v;
        cin >> u >> v;
        --u;
        parent[i] = u;
        dw[i][0] = v;
        edges[u].push_back(i);
    }
 
    setDepth(0, 0);
 
    degree_[0] = 1;
 
    for (int i = 1; i < logN + 1; i++) {
        degree_[i] = degree_[i - 1] * 2;
    }
 
    for (int i = 0; i < n; i++) {
        dp[i][0] = parent[i];
    }
 
    for (int j = 1; j < logN; j++) {
        for (int i = 0; i < n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
 
        for (int i = 0; i < n; i++) {
            dw[i][j] = min(dw[i][j - 1], dw[dp[i][j - 1]][j - 1]);
        }
    }
 
    int m;
    cin >> m;
 
    for (int i = 0; i < m; ++i) {
        int u, v;
        cin >> u >> v;
        --u;
        --v;
        int m_n = INT_MAX;
 
        if (depth[u] > depth[v]) {
            swap(u, v);
        }
 
        for (int j = logN - 1; j >= 0; --j) {
            if (degree_[j] <= depth[v] - depth[u]) {
                m_n = min(dw[v][j], m_n);
                v = dp[v][j];
            }
        }
 
        if (v == u) {
            cout << m_n << endl;
        } else {
            for (int j = logN - 1; j >= 0; --j) {
                if (dp[u][j] != dp[v][j]) {
                    m_n = min(dw[v][j], m_n);
                    m_n = min(dw[u][j], m_n);
                    u = dp[u][j];
                    v = dp[v][j];
                }
            }
            cout << min(min(dw[v][0], m_n), min(dw[u][0], m_n)) << endl;
        }
    }
 
    return 0;
}