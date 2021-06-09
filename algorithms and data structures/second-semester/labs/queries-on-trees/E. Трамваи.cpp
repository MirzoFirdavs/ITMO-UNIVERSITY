#include <bits/stdc++.h>
 
int n;
std::vector<int> parent;
std::vector<int> depth;
std::vector<std::vector<int>> dp;
int logN;
std::vector<int> result;
std::vector<std::vector<int>> edge;
 
int lca(int v, int u) {
    if (depth[v] > depth[u]) {
        std::swap(v, u);
    }
    int h = depth[u] - depth[v];
    for (int j = logN; j >= 0; j--) {
        if (std::pow(2, j) <= h) {
            h -= static_cast<int>(std::pow(2, j));
            u = dp[u][j];
        }
    }
    if (v == u) {
        return v;
    }
    for (int j = logN; j >= 0; j--) {
        if (dp[v][j] != dp[u][j]) {
            v = dp[v][j];
            u = dp[u][j];
        }
    }
    return parent[v];
}
 
void dfs(int v, int prev, int d) {
    depth[v] = d;
    for (int i = 0; i < edge[v].size(); i++) {
        int to = edge[v][i];
        if (to != prev) {
            dfs(to, v, d + 1);
        }
    }
}
 
void dfsP(int v, int prev) {
    for (int i = 0; i < edge[v].size(); i++) {
        int to = edge[v][i];
        if (to != prev) {
            parent[to] = v;
            dfsP(to, v);
        }
    }
}
 
int cnt_ans(int v, int u) {
    int cnt = 0;
    for (int to : edge[v]) {
        if (to == u) {
            continue;
        }
        cnt += cnt_ans(to, v);
        result[v] = std::max(result[v], result[to] - 1);
    }
    return result[v] <= 0 ? cnt : ++cnt;
}
 
void generation() {
    dfsP(1, 0);
    for (int i = 1; i <= n; i++) {
        dp[i][0] = parent[i];
    }
    for (int j = 1; j <= logN; j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
    }
    dfs(1, 1, 0);
}
 
void install() {
    logN = static_cast<int>(log(n) / log(2) + 1);
    parent.resize(n + 1);
    depth.resize(n + 1);
    dp.resize(n + 1);
    edge.resize(n + 1);
    result.resize(n + 2, -1);
    for (int i = 0; i < n + 1; ++i) {
        dp[i].resize(logN + 1);
    }
}
 
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
 
    int v, u;
 
    std::cin >> n;
 
    install();
 
    for (int i = 0; i < n - 1; i++) {
        std::cin >> u >> v;
        edge[u].push_back(v);
        edge[v].push_back(u);
    }
 
    generation();
 
    int m;
 
    std::cin >> m;
 
    for (int i = 0; i < m; i++) {
        std::cin >> v >> u;
        int lc = lca(v, u);
        result[v] = std::max(abs(depth[v] - depth[lc]), result[v]);
        result[u] = std::max(abs(depth[u] - depth[lc]), result[u]);
    }
 
    std::cout << n - 1 - cnt_ans(1, 0) << std::endl;
 
    return 0;
}