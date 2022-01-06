#include <iostream>
#include <vector>
#include <algorithm>
#include <deque>
 
std::deque<int> z_function (std::string s) {
    std::deque<int> z (s.size());
    int l = 0;
    int r = 0;
    for (int i = 1; i < s.size(); ++i) {
        if (i <= r) {
            z[i] = std::min(r - i + 1, z[i - l]);
        }
        while (i + z[i] < s.size() and s[z[i]] == s[i+z[i]]) {
            ++z[i];
        }
        if (i + z[i] - 1 > r) {
            l = i;
            r = i + z[i] - 1;
        }
    }
    return z;
}
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie();
    std::string s, t;
    std::cin >> s >> t;
    std::string input = s + t;
    std::deque<int> z = z_function(input);
    std::vector<int> result;
    for (int i = (int) s.size(); i < z.size(); i++) {
        if (z[i] >= s.size()) {
            result.push_back(i);
        }
    }
    std::cout << result.size() << '\n' ;
    for (auto element : result) {
        std::cout << element - s.size() + 1 << " ";
    }
    return 0;
}