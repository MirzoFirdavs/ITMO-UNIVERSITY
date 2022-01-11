#include <ios>
#include <iostream>
#include <vector>
#include <deque>

void reverse(std::deque<int> &q, int begin, int end) {
    for (int i = 0; i < (end - begin) / 2; ++i) {
        std::swap(q[begin + i], q[end - 1 - i]);
    }
}

int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
    int n;
    std::cin >> n;
    std::vector<std::vector<bool>> graph(n, std::vector<bool>(n));
    std::deque<int> q;
    q.push_back(0);
    for (int i = 1; i < n; ++i) {
        std::string path;
        std::cin >> path;
        for (int j = 0; j < i; ++j) {
            graph[i][j] = graph[j][i] = (path[j] == '1');
        }
        q.push_back(i);
    }
    for (int i = 0; i < n * (n - 1); ++i) {
        if (!graph[q[0]][q[1]]) {
            int j = 2;
            while (j + 1 < q.size() && !(graph[q[0]][q[j]] && graph[q[1]][q[j + 1]])) {
                j++;
            }
            if (j + 1 == q.size()) {
                j = 2;
                while (!graph[q[0]][q[j]]) {
                    j++;
                }
            }
            reverse(q, 1, j + 1);
        }
        q.push_back(q.front());
        q.pop_front();
    }
    for (int u : q) {
        std::cout << u + 1 << ' ';
    }
    return 0;
}