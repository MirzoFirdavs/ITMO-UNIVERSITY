#include <iostream>
#include <vector>
#include <unordered_set>
#include <string>
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie();
    std::vector<std::vector<long long>> hashes(2);
    std::vector<std::string> strings(2);
    std::vector<long long> powers( 100001);
    powers[0] = 1;
    for (int i = 1; i < 100001; i++) {
        powers[i] = 1553 * powers[i - 1];
    }
    int l = -1, r = 1e9;
    for (int i = 0; i < 2; i++) {
        std::cin >> strings[i];
        r = std::min(r, (int) strings[i].size() + 1);
        hashes[i].resize(strings[i].size() + 1);
        hashes[i][0] = 0;
        for (int j = 1; j < hashes[i].size(); j++) {
            hashes[i][j] = hashes[i][j - 1] * 1553 + strings[i][j - 1];
        }
    }
    std::string res;
    while (r - l > 1) {
        int m = (l + r) / 2;
        std::unordered_set<long long> set;
        for (int j = 0; j < hashes[0].size() - m; j++) {
            set.insert(hashes[0][j + m] - hashes[0][j] * powers[m]);
        }
        std::string ans;
        for (int i = 0; i < hashes.size(); i++) {
            std::unordered_set<long long> next_set;
            for (int j = 0; j < hashes[i].size() - m; j++) {
                auto hash = hashes[i][j + m] - hashes[i][j] * powers[m];
                if (set.count(hash)) {
                    next_set.insert(hash);
                    if (i == hashes.size() - 1) {
                        ans = strings[i].substr(j, m);
                        break;
                    }
                }
            }
            set = next_set;
        }
        if (!ans.empty() || !m) {
            res = ans;
            l = m;
        } else {
            r = m;
        }
    }
    std::cout << res;
    return 0;
}