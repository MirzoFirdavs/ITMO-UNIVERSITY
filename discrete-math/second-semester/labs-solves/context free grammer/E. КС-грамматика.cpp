#include <vector>
#include <cstddef>
#include <string>
#include <iostream>
 
size_t ln;
std::string word;
int n;
 
std::vector<std::vector<std::vector<int>>> dp;
std::vector<std::vector<std::vector<std::vector<int>>>> graph;
std::vector<std::pair<char, std::string>> v;
 
void gen() {
    dp.resize('z' - 'a' + 1);
    for (size_t i = 0; i < 'z' - 'a' + 1; ++i) {
        dp[i].resize(ln);
    }
    for (size_t i = 0; i < 'z' - 'a' + 1; ++i) {
        for (size_t j = 0; j < ln; ++j) {
            dp[i][j].resize(ln);
        }
    }
    graph.resize(51);
    for (size_t i = 0; i < 51; ++i) {
        graph[i].resize(ln);
    }
    for (size_t i = 0; i < 51; ++i) {
        for (size_t j = 0; j < ln; ++j) {
            graph[i][j].resize(ln);
        }
    }
    for (size_t i = 0; i < 51; ++i) {
        for (size_t j = 0; j < ln; ++j) {
            for (size_t g = 0; g < ln; ++g) {
                graph[i][j][g].resize(6);
            }
        }
    }
    for (size_t i = 0; i < ln; ++i) {
        for (size_t j = 0; j < v.size(); ++j) {
            std::pair<char, std::string> current = v[j];
            if (dp[current.first - 'A'][i][i] or current.second.empty()) {
                dp[current.first - 'A'][i][i] = 1;
            }
            if (i < n and current.second.size() == 1 and current.second[0] == word[i]) {
                dp[current.first - 'A'][i][i + 1] = 1;
            }
            graph[j][i][i][0] = true;
        }
    }
}
 
void solve() {
    for (int m = 1; m < ln; ++m) {
        for (int i = 0; i < ln - m; ++i) {
            for (int iter = 0; iter < 10; ++iter) {
                int j = i + m;
                for (int idx = 0; idx < v.size(); ++idx) {
                    std::pair<char, std::string> current = v[idx];
                    for (int k = 1; k <= current.second.size(); ++k) {
                        for (int g = i; g < j + 1; ++g) {
                            if (current.second[k - 1] >= 'a') {
                                if (graph[idx][i][j][k] or
                                    graph[idx][i][g][k - 1] and (g + 1 == j) and (word[g] == current.second[k - 1])) {
                                    graph[idx][i][j][k] = 1;
                                }
                            } else {
                                if (graph[idx][i][j][k] or
                                    graph[idx][i][g][k - 1] and dp[current.second[k - 1] - 'A'][g][j]) {
                                    graph[idx][i][j][k] = 1;
                                }
                            }
                        }
                    }
                }
                for (int idx = 0; idx < v.size(); ++idx) {
                    std::pair<char, std::string> current = v[idx];
                    if (dp[current.first - 'A'][i][j] or graph[idx][i][j][current.second.size()]) {
                        dp[current.first - 'A'][i][j] = 1;
                    }
                }
            }
        }
    }
}
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
 
    freopen("cf.in", "r", stdin);
    freopen("cf.out", "w", stdout);
 
    char s;
 
    std::cin >> n >> s;
 
    std::string st;
    std::string x;
 
    getline(std::cin, st);
 
    for (size_t i = 0; i < n ; ++i) {
        getline(std::cin, st);
        if (st.size() > 5) {
            x = st.substr(5);
        } else {
            x = "";
        }
        v.emplace_back(st[0], x);
    }
 
    std::cin >> word;
 
    ln = word.size() + 1;
 
    gen();
 
    solve();
 
    if (dp[s - 'A'][0][ln - 1]) {
        std::cout << "yes";
    } else {
        std::cout << "no";
    }
 
    return 0;
}