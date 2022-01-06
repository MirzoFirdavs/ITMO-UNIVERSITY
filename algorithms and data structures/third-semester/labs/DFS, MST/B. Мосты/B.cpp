#include <vector>
#include <ios>
#include <iostream>
#include <algorithm>
 
std::vector<bool> used;
std::vector<int> tI;
std::vector<int> check;
std::vector<int> result;
std::vector<std::vector<std::pair<int, int>>> graph;
int n, m, t = 0;
 
void dfs(int u, int p) {
    used[u] = true;
    tI[u] = check[u] = t++;
 
    for (int i = 0; i < graph[u].size(); ++i) {
        int to = graph[u][i].first;
        if (to == p) {
            continue;
        }
        if (used[to]) {
            check[u] = std::min(check[u], tI[to]);
        } else {
            dfs(to, u);
            check[u] = std::min(check[u], check[to]);
            if (check[to] > tI[u]) {
                result.push_back(graph[u][i].second);
            }
        }
    }
}
 
void generate() {
    int u, v;
    used.resize(n, false);
    tI.resize(n, 0);
    check.resize(n, 0);
    graph.resize(n);
    for (int i = 0; i < m; ++i) {
        std::cin >> u >> v;
        graph[u - 1].push_back(std::make_pair(v - 1, i + 1));
        graph[v - 1].push_back(std::make_pair(u - 1, i + 1));
    }
    for (int i = 0; i < n; ++i) {
        if (!used[i]) {
            dfs(i, -1);
        }
    }
}
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie();
    std::cin >> n >> m;
    generate();
    if (result.empty()) {
        std::cout << 0;
    } else {
        std:: cout << result.size() << '\n';
        std::sort(result.begin(), result.end());
        for (auto v: result) {
            std::cout << v << '\n';
        }
    }
    return 0;
}