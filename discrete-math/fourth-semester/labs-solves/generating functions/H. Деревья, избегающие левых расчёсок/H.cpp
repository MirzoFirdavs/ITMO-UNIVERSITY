#include <iostream>
#include <vector>
 
long const MOD = 998244353;
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
 
    long long k, n;
    std::cin >> k >> n;
 
    std::vector<long long> left, right;
    std::vector<std::vector<long long>> dp(k + 1, std::vector<long long>(k + 1, 0));
 
    for (int i = 0; i < k + 1; ++i) {
        dp[i][0] = dp[i][i] = 1;
        for (int j = 1; j < i; ++j) {
            dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j]) % MOD;
            dp[i][j] = dp[i][j] + (dp[i][j] < 0 ? MOD : 0);
        }
    }
 
    int cnt = 0;
    while (2 * cnt < k - 1) {
        left.emplace_back(dp[k - cnt - 2][cnt] * (cnt % 2 == 0 ? 1 : -1));
        ++cnt;
    }
 
    cnt = 0;
    while (2 * cnt < k) {
        right.emplace_back(dp[k - cnt - 1][cnt] * (cnt % 2 == 0 ? 1 : -1));
        ++cnt;
    }
 
    std::vector<long long> x(n + 1, 0);
    x[0] = 1 / (!right.empty() ? right[0] : 0);
 
    for (int i = 0; i < n + 1; ++i) {
        for (int j = 0; j < i; ++j) {
            x[i] = (x[i] - ((x[j] * (i - j < right.size() ? right[i - j] : 0)) % MOD)) % MOD;
            x[i] = x[i] + (x[i] < 0 ? MOD : 0);
        }
        
        x[i] /= (!right.empty() ? right[0] : 0);
    }
 
    for (int i = 0; i < n; ++i) {
        long long result = 0;
        for (int j = 0; j < i + 1; ++j) {
            result = (result + (((j < left.size() ? left[j] : 0) * (i - j < x.size() ? x[i - j] : 0)) % MOD)) % MOD;
            result = result + (result < 0 ? MOD : 0);
        }
        
        std::cout << result << '\n';
    }
 
    return 0;
}