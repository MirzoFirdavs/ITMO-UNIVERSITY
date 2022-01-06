#include <vector>
#include <ios>
#include <iostream>
#include <algorithm>
#include <set>
 
std::vector<bool> used;
std::vector<int> tI;
std::vector<int> check;
std::set<int> result;
std::vector<std::vector<int>> graph;
int n, m, t = 0;
 
void dfs(int u, int p) {
    used[u] = true;
    tI[u] = check[u] = t++;
    int current = 0;
    for (int i = 0; i < graph[u].size(); ++i) {
        int to = graph[u][i];
        if (to == p) {
            continue;
        }
        if (used[to]) {
            check[u] = std::min(check[u], tI[to]);
        } else {
            dfs(to, u);
            current++;
            check[u] = std::min(check[u], check[to]);
            if (check[to] >= tI[u] && p != -1) {
                result.insert(u);
            }
        }
    }
    if (p == -1 && current > 1) {
        result.insert(u);
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
        graph[u - 1].push_back(v - 1);
        graph[v - 1].push_back(u - 1);
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
        std::cout << result.size() << '\n';
        for (auto v: result) {
            std::cout << v + 1 << ' ';
        }
    }
    return 0;
}