#include <bits/stdc++.h>
 
std::vector<int> parent;
 
int generation(int x) {
    if (parent[x] != x) {
        parent[x] = generation(parent[x]);
    }
    return parent[x];
}
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
 
    int m;
    int cur = 1;
 
    std::cin >> m;
 
    std::vector<int> cur_d(m, 0);
    std::vector<std::vector<int>> dp(m);
    parent.resize(m);
 
    int logM = static_cast<int>((log(m) / log(2)) + 1);
 
    for (int i = 0; i < m; i++) {
        dp[i].resize(logM + 1);
    }
 
    for (int i = 0; i < m; i++) {
        std::string c;
        std::cin >> c;
        if (c == "+") {
            int v;
            std::cin >> v;
            --v;
            parent[cur] = cur;
            dp[cur][0] = v;
            cur_d[cur] = 1 + cur_d[v];
            int j = 1;
            while (pow(2, j - 1) < m) {
                dp[cur][j] = dp[dp[cur][j - 1]][j - 1];
                ++j;
            }
            cur++;
        } else if (c == "-") {
            int v;
            std::cin >> v;
            --v;
            int x = generation(v);
            int y = generation(dp[v][0]);
            if (x != y)
                parent[x] = y;
 
        } else {
            int u, v;
            std::cin >> u >> v;
            --u;
            --v;
            if (cur_d[v] > cur_d[u]) {
                std::swap(u, v);
            }
 
            for (int j = logM; j >= 0; --j) {
                if (cur_d[v] <= cur_d[u] - pow(2, j)) {
                    u = dp[u][j];
                }
            }
 
            if (u == v) {
                std::cout << generation(v) + 1 << std::endl;
            } else {
                for (int j = logM; j >= 0; --j) {
                    if (dp[v][j] != dp[u][j]) {
                        u = dp[u][j];
                        v = dp[v][j];
                    }
                }
                std::cout << generation(dp[u][0]) + 1 << std::endl;
            }
        }
    }
    return 0;
}