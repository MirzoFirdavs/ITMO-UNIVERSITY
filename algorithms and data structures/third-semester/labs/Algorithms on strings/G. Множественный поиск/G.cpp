#include <iostream>
#include <vector>
#include <deque>
 
struct Node {
    int alphabet[26]{};
    std::vector<int> terminal;
    int suffixLink{};
    int suffixLinks{};
};
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie();
    std::vector<Node> states;
    int sze = 0;
    states.emplace_back();
    int n;
    std::cin >> n;
    std::string s, t;
    for (int i = 0; i < n; ++i) {
        std::cin >> s;
        int cur = 0;
        for (char j : s) {
            int chr = j - 'a';
            if (!states[cur].alphabet[chr]) {
                states.emplace_back();
                states[cur].alphabet[chr] = (int) states.size() - 1;
            }
            cur = states[cur].alphabet[chr];
        }
        states[cur].terminal.push_back(sze++);
    }
    std::cin >> t;
    std::deque<int> q;
    q.push_back(0);
    while (!q.empty()) {
        int current = q.front();
        q.pop_front();
        for (int i = 0; i < 26; ++i) {
            if (!states[current].alphabet[i]) {
                states[current].alphabet[i] = states[states[current].suffixLink].alphabet[i];
            } else {
                int next = states[current].alphabet[i];
                if (current) {
                    states[next].suffixLink = states[states[current].suffixLink].alphabet[i];
                    states[next].suffixLinks = !states[states[next].suffixLink].terminal.empty() ? states[next].suffixLink
                                                                                                 : states[states[next].suffixLink].suffixLinks;
                }
                q.push_back(next);
            }
        }
    }
    std::vector<bool> result(sze);
    std::vector<bool> found(states.size());
    int cur = 0;
    for (char i : t) {
        cur = states[cur].alphabet[i - 'a'];
        int temp = cur;
        while (!found[temp]) {
            if (!states[temp].terminal.empty()) {
                for (int ends : states[temp].terminal)
                    result[ends] = true;
            }
            found[temp] = true;
            temp = states[temp].suffixLinks;
        }
    }
    for (int element : result) {
        std::cout << (element ? "YES\n" : "NO\n");
    }
    return 0;
}