#include <bits/stdc++.h>
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
 
    freopen("nfc.in", "r", stdin);
    freopen("nfc.out", "w", stdout);
 
    int MOD = 1000000007;
    long long n;
    char s;
    std::string x, y, z;
    std::vector<std::vector<std::vector<long long>>> dp(101, std::vector<std::vector<long long>>(101));
 
    for (size_t i = 0; i < 101; ++i) {
        for (size_t j = 0; j < 101; ++j) {
            dp[i][j].resize(101);
        }
    }
 
    std::vector<std::pair<int, int> > graph[101];
 
    std::cin >> n >> s;
 
    for (int i = 0; i < n; ++i) {
        std::cin >> x >> y >> z;
        int a = x[0] - 'A';
        z[0] >= 'a' ? graph[a].emplace_back(z[0] - 'a', -1) : graph[a].emplace_back(z[0] - 'A', z[1] - 'A');
    }
 
    std::string word;
    std::cin >> word;
 
    for (size_t i = 0; i < word.size(); ++i) {
        for (size_t j = 0; j < 26; ++j) {
            for (auto iter : graph[j]) {
                if (iter.second == -1 and ((word[i] - 'a') == iter.first)) {
                    dp[j][i][i]++;
                }
            }
        }
    }
 
    for (size_t b = 2; b <= word.size(); ++b) {
        for (size_t iter = 0; iter <= word.size() - b; ++iter) {
            size_t cur = iter + b - 1;
            for (size_t k = iter; k < cur; ++k) {
                for (size_t i = 0; i < 26; ++i) {
                    for (std::pair<int, int> current : graph[i]) {
                        if (current.second != -1) {
                            dp[i][iter][cur] = (dp[i][iter][cur] +
                                                (dp[current.first][iter][k] * dp[current.second][k + 1][cur]) %
                                                MOD) % MOD;
                        }
                    }
                }
            }
        }
    }
 
    std::cout << dp[s - 'A'][0][word.size() - 1];
 
    return 0;
 
}