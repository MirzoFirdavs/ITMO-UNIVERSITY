#include <iostream>
#include <vector>
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie();
    std::string s;
    std::cin >> s;
    std::vector<int> pow(s.size() + 1);
    std::vector<int> hash(s.size() + 1);
    pow[0] = 1;
    hash[0] = 0;
    for (int i = 1; i < s.size() + 1; i++) {
        pow[i] = 11 * pow[i - 1];
        hash[i] = hash[i - 1] * 11 + s[i - 1];
    }
    int m;
    std::cin >> m;
    for (int i = 0; i < m; i++) {
        int l1, r1, l2, r2;
        std::cin >> l1 >> r1 >> l2 >> r2;
        if (hash[r1] - hash[l1 - 1] * pow[r1 - (l1 - 1)] == hash[r2] - hash[l2 - 1] * pow[r2 - (l2 - 1)]) {
            std::cout << "Yes\n";
        } else {
            std::cout << "No\n";
        }
    }
    return 0;
}