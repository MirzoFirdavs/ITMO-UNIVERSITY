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
 
void print(const std::deque<int>& vec) {
    for (auto element: vec) {
        std::cout << element << " ";
    }
}
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie();
    std::string input;
    std::cin >> input;
    std::deque<int> result = z_function(input);
    result.pop_front();
    print(result);
   