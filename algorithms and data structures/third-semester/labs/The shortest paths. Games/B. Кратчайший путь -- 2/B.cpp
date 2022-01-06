#include <bits/stdc++.h>
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie();
    int n, m;
    int u, v, w;
    
    std::vector<std::vector<int>> graph;
    
    std::cin >> n >> m;
    
    for (int i = 0; i < m; i++) {
        std::cin >> u >> v >> w;
        std::vector<int> path1 = {u, v, w};
        std::vector<int> path2 = {v, u, w};
        graph.push_back(path1);
        graph.push_back(path2);
    }
    
    std::vector<int> result(n + 1);
    
    for (int i = 0; i < n + 1; ++i) {
        result[i] = 10000001;
    }
    
    result[1] = 0;
 
    while (true) {
        int flag = 0;
        for (int i = 0; i < graph.size(); ++i) {
            if (result[graph[i][0]] < 10000001 and result[graph[i][1]] > result[graph[i][0]] + graph[i][2]) {
                result[graph[i][1]] = result[graph[i][0]] + graph[i][2];
                flag = 1;
            }
        }
        if (flag == 0) {
            break;
        }
    }
    
    for (int i = 1; i < n + 1; ++i) {
        std::cout << result[i] << " ";
    }
    
    return 0;
}