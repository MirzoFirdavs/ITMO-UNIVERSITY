#include <bits/stdc++.h>
 
std::vector<std::vector<std::pair<int, int>>> graph;
std::vector<std::vector<int>> num;
std::vector<std::vector<std::pair<int, int>>> new_graph;
std::vector<bool> used;
 
long long t;
 
void dfs(int u, int s, int n, int M) {
    if (used[num[u][s]]) {
        return;
    } else {
        used[num[u][s]] = true;
        for (std::pair<int, int> vw: graph[u]) {
            new_graph[num[u][s]].push_back(std::make_pair(num[vw.first][(s + vw.second) % M], vw.second));
            if (vw.first == n - 1 and (s + vw.second) % M == t % M) {
                used[num[n - 1][t % M]] = true;
                return;
            } else {
                dfs(vw.first, (s + vw.second) % M, n, M);
            }
        }
    }
}
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie();
    int n, m, M;
    std::vector<int> d;
    std::cin >> n >> m;
    graph.resize(n);
    int u, v, w;
    for (int i = 0; i < m; i++) {
        std::cin >> u >> v >> w;
        u--;
        v--;
        graph[u].push_back(std::make_pair(v, w));
        graph[v].push_back(std::make_pair(u, w));
    }
    std::cin >> t;
    if (graph[n - 1].empty()) {
        std::cout << "Impossible";
    } else {
        M = 2 * graph[n - 1][0].second;
        num.resize(n, std::vector<int>(M));
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < M; j++) {
                num[i][j] = k++;
            }
        }
        used.resize(n * M);
        new_graph.resize(n * M);
        dfs(0, 0, n, M);
        if (used[num[n - 1][t % M]]) {
            d.resize(n * M, INT_MAX);
            std::vector<bool> marked(n * M, false);
            std::set<std::pair<int, int>> relax;
            relax.insert(std::make_pair(0, 0));
            d[0] = 0;
            while (true) {
                std::pair<int, int> distU = *(relax.begin());
                relax.erase(std::make_pair(distU.first, distU.second));
                for (std::pair<int, int> vw: new_graph[distU.second]) {
                    if (d[vw.first] <= d[distU.second] + vw.second) {
                        continue;
                    }
                    if (d[vw.first] != INT_MAX) {
                        relax.erase({d[vw.first], vw.first});
                    }
                    d[vw.first] = d[distU.second] + vw.second;
                    relax.insert(std::make_pair(d[vw.first], vw.first));
                }
                if (relax.empty()) {
                    break;
                }
            }
            if (d[num[n - 1][t % M]] <= t) {
                std::cout << "Possible";
            } else {
                std::cout << "Impossible";
            }
        } else {
            std::cout << "Impossible";
        }
    }
    return 0;
}