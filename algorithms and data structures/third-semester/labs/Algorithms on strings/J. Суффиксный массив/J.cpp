#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
 
std::vector<int> countSort(const std::vector<int>& p, const std::vector<int>& c) {
    int n = (int) p.size();
    std::vector<int> cnt(n);
    for (int i : c) {
        cnt[i]++;
    }
    std::vector<int> pp(n);
    std::vector<int> pos(n);
    pos[0] = 0;
    for (int i = 1; i < n; i++) {
        pos[i] = pos[i - 1] + cnt[i - 1];
    }
    for (int i : p) {
        int k = c[i];
        pp[pos[k]] = i;
        pos[k]++;
    }
    return pp;
}
 
std::pair<std::vector<int>, std::vector<int>> buildSufArray(const std::string& s) {
    int n = (int) s.size();
    std::vector<int> p(n);
    std::vector<int> c(n);
    std::vector<std::pair<int, int>> a(n);
    for (int i = 0; i < n; i++) {
        a[i] = std::make_pair(s[i], i);
    }
    std::sort(a.begin(), a.end());
    for (int i = 0; i < n; i++) {
        p[i] = a[i].second;
    }
    for (int i = 1; i < n; i++) {
        c[p[i]] = c[p[i - 1]];
        if (a[i].first != a[i - 1].first) {
            c[p[i]]++;
        }
    }
    std::vector<int> cc(n);
    int k = 0;
    while ((1 << k) < n) {
        for (int i = 0; i < n; i++) {
            p[i] = (p[i] - (1 << k) + n) % n;
        }
        p = countSort(p, c);
        for (int i = 0; i < n; i++) {
            cc[i] = 0;
        }
        for (int i = 1; i < n; i++) {
            cc[p[i]] = cc[p[i - 1]];
            if (c[p[i - 1]] != c[p[i]] or c[(p[i - 1] + (1 << k)) % n] != c[(p[i] + (1 << k)) % n]) {
                cc[p[i]]++;
            }
        }
        for (int i = 0; i < n; ++i) {
            c[i] = cc[i];
        }
        k++;
    }
    std::vector<int> lcp(n);
    k = 0;
    for (int i = 0; i < n - 1; i++) {
        int pIndex = c[i];
        int j = p[pIndex - 1];
        while (s[i + k] == s[j + k]) {
            k++;
        }
        lcp[pIndex] = k;
        k = std::max(k - 1, 0);
    }
    return std::make_pair(p, lcp);
}
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie();
    std::string s;
    std::cin >> s;
    s += "#";
    std::pair<std::vector<int>, std::vector<int>> a = buildSufArray(s);
    for (int i = 1; i < a.first.size(); i++) {
        std::cout << a.first[i] + 1 << " ";
    }
    std::cout << '\n';
    for (int i = 2; i < a.first.size(); i++) {
        std::cout << a.second[i] << " ";
    }
    return 0;
}