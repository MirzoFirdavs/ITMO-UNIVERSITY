#include <vector>
#include <valarray>
#include <iostream>
 
int n;
int logN;
int tic_tac;
int vertex;
 
std::vector<int> parent;
std::vector<int> depth;
std::vector<int> used;
std::vector<std::vector<int>> dp;
std::vector<std::vector<int>> edge;
 
void dfs(int v, int u) {
    used[v] = tic_tac++;
    depth[v] = u;
    used[v] = tic_tac;
    for (int to : edge[v]) {
        dfs(to, u + 1);
    }
}
 
void generation() {
 
    int tmp;
 
    used.resize(n + 10);
    parent.resize(n + 10);
    depth.resize(n + 10);
    logN = 18;
    edge.resize(n + 10);
    dp.resize(n + 10);
 
    for (int i = 0; i < n + 10; ++i) {
        dp[i].resize(logN + 1);
    }
 
    for (int i = 1; i <= n; i++) {
        scanf("%d", &tmp);
        //std::cin >> tmp;
        parent[i] = tmp;
        if (tmp == -1) {
            vertex = i;
            parent[i] = 0;
        } else {
            edge[tmp].push_back(i);
        }
    }
 
    for (int i = 1; i <= n; i++) {
        dp[i][0] = parent[i];
    }
 
    for (int j = 1; j <= logN; j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
    }
 
    dfs(vertex, 0);
}
 
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
 
    //std::cin >> n;
    scanf("%d", &n);
 
    generation();
 
    int m;
    int u;
    int v;
    int ans;
 
    //std::cin >> m;
    scanf("%d", &m);
 
    std::vector<std::vector<int>> qq(m + 1);
 
    for (int i = 0; i < m; ++i) {
        //std::cin >> u;
        scanf("%d", &u);
        qq[i].resize(u);
        for (int j = 0; j < u; ++j) {
            int current = 0;
            scanf("%d", &current);
            qq[i][j] = current;
            //std::cin >> qq[i][j];
        }
    }
 
    for (int i = 0; i < m; ++i) {
        std::vector<int> group = qq[i];
        sort(group.begin(), group.end(), [](int a, int b) {
            return used[a] > used[b];
        });
        if (group.size() == 1) {
            printf("%d", depth[group[0]] + 1);
            //std::cout << depth[group[0]] + 1;
        } else {
            ans = depth[group[0]];
            u = group[0];
            for (int h = 1; h < group.size(); ++h) {
                v = group[h];
                if (depth[v] > depth[u]) {
                    std::swap(u, v);
                }
                int hh = depth[u] - depth[v];
                for (int j = logN; j >= 0; j--) {
                    if (pow(2, j) <= hh) {
                        hh -= static_cast<int>(pow(2, j));
                        u = dp[u][j];
                    }
                }
                if (u != v) {
                    for (int j = logN; j >= 0; j--) {
                        if (dp[v][j] != dp[u][j]) {
                            v = dp[v][j];
                            u = dp[u][j];
                        }
                    }
                    u = parent[v];
                }
                ans += depth[group[h]] - depth[u];
                u = group[h];
            }
            printf("%d", ans + 1);
            //std::cout << ans + 1;
        }
        printf("\n");
        //std::cout << std::endl;
    }
 
    return 0;
}