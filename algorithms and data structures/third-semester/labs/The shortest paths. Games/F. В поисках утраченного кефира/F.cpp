#include <bits/stdc++.h>
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie();
    int m, n;
    std::cin >> n >> m;
    std::vector<std::vector<std::pair<long long, long long>>> graph;
    std::vector<long long> d;
    graph.resize(n);
    long long u, v, w;
    for (int i = 0; i < m; i++) {
        std::cin >> u >> v >> w;
        u--;
        v--;
        graph[u].push_back(std::make_pair(v, w));
        graph[v].push_back(std::make_pair(u, w));
    }
    int a, b, c;
    std::cin >> a >> b >> c;
    a--;
    b--;
    c--;
    std::vector<long long> result;
    d.clear();
    d.resize(graph.size(), LONG_LONG_MAX);
    std::set<std::pair<long long, long long>> relax;
    relax.insert(std::make_pair(0, a));
    d[a] = 0;
    while (true) {
        std::pair<long long, long long> distU = *(relax.begin());
        relax.erase(std::make_pair(distU.first, distU.second));
        for (std::pair<long long, long long> vw: graph[distU.second]) {
            if (d[vw.first] <= d[distU.second] + vw.second)
                continue;
            if (d[vw.first] != LONG_LONG_MAX)
                relax.erase(std::make_pair(d[vw.first], vw.first));
            d[vw.first] = d[distU.second] + vw.second;
            relax.insert(std::make_pair(d[vw.first], vw.first));
        }
        if (relax.empty()) {
            break;
        }
    }
    result.push_back(d[b]);
    result.push_back(d[c]);
    d.clear();
    d.resize(graph.size(), LONG_LONG_MAX);
    relax.clear();
    relax.insert(std::make_pair(0, b));
    d[b] = 0;
    while (true) {
        std::pair<long long, long long> distU = *(relax.begin());
        relax.erase(std::make_pair(distU.first, distU.second));
        for (std::pair<long long, long long> vw: graph[distU.second]) {
            if (d[vw.first] <= d[distU.second] + vw.second)
                continue;
            if (d[vw.first] != LONG_LONG_MAX)
                relax.erase(std::make_pair(d[vw.first], vw.first));
            d[vw.first] = d[distU.second] + vw.second;
            relax.insert(std::make_pair(d[vw.first], vw.first));
        }
        if (relax.empty()) {
            break;
        }
    }
    result.push_back(d[c]);
    std::sort(result.begin(), result.end());
    if (result[1] == LONG_LONG_MAX) {
        std::cout << -1;
    } else {
        std::cout << result[0] + result[1];
    }
    return 0;
}