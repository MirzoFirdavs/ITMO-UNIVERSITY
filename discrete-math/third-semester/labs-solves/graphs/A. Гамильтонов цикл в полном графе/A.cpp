#include <iostream>
#include <deque>
#include <algorithm>
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie();
    int n;
    std::string s;
    std::cin >> n;
    bool graph[n][n];
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            graph[i][j] = false;
        }
    }
    std::deque<int> q;
    q.push_back(0);
    for (int i = 1; i < n; ++i) {
        std::cin >> s;
        for (int j = 0; j < i; ++j) {
            if (s[j] == '1') {
                graph[i][j] = graph[j][i] = true;
            }
        }
        q.push_back(i);
    }
    for (int i = 0; i < n * (n - 1); i++) {
        if (!graph[q[0]][q[1]]) {
            int j = 2;
            while (!graph[q[0]][q[j]] or !graph[q[1]][q[j + 1]]) {
                j++;
            }
            reverse(q.begin() + 1, q.begin() + j + 1);
        }
        q.push_back(q[0]);
        q.pop_front();
    }
    for (auto i: q) {
        std::cout << i + 1 << " ";
    }
}