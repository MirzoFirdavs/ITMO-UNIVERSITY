#include <iostream>
#include <vector>
#include <deque>
 
std::vector<std::vector<int>> graph;
std::vector<std::string> result;
std::vector<int> d;
std::vector<int> cnt;
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
    int n, m;
 
    while (std::cin >> n >> m) {
        graph.clear();
        result.clear();
        d.clear();
        cnt.clear();
        result.resize(n, "DRAW");
        graph.resize(n);
        d.resize(n);
        cnt.resize(n);
        for (int i = 0; i < m; ++i) {
            int u, v;
            std::cin >> u >> v;
            u--;
            v--;
            graph[v].push_back(u);
            d[u]++;
        }
        std::deque<int> q;
        for (int i = 0; i < n; ++i) {
            if (d[i] == 0) {
                result[i] = "SECOND";
                q.push_back(i);
            }
        }
        while (!q.empty()) {
            int k = q[0];
            q.pop_front();
            if (result[k] == "SECOND") {
                for (int u: graph[k]) {
                    if (result[u] == "DRAW") {
                        result[u] = "FIRST";
                        q.push_back(u);
                    }
                }
            } else {
                for (int u: graph[k]) {
                    cnt[u]++;
                    if (cnt[u] == d[u]) {
                        result[u] = "SECOND";
                        q.push_back(u);
                    }
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            std::cout << result[i] << '\n';
        }
        std::cout << '\n';
    }
    return 0;
}