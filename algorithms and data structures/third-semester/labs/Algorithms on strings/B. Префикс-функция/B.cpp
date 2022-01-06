#include <iostream>
#include <vector>
 
std::vector<int> prefix_function (std::string s) {
    std::vector<int> pi(s.size());
    for (int i = 1; i < s.size(); ++i) {
        int j = pi[i - 1];
        while (j > 0 and s[i] != s[j]) {
            j = pi[j-1];
        }
        if (s[i] == s[j]){
            ++j;
        }
        pi[i] = j;
    }
    return pi;
}
 
void print(const std::vector<int>& vec) {
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
    std::vector<int> result = prefix_function(input);
    print(result);
    return 0;
}