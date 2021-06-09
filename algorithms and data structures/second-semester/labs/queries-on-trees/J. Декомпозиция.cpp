#include <bits/stdc++.h>
 
std::vector<std::set<int>> edge;
std::vector<int> len;
std::vector<int> parent;
 
int sizes;
int n;
 
int dfs(int u, int x) {
    len[u] = 1;
    for (int v : edge[u]) {
        if (v != x) {
            len[u] += dfs(v, u);
        }
    }
    return len[u];
}
 
int find_centroid(int u, int p) {
    for (int v : edge[u]) {
        if (v != p and len[v] > sizes / 2) {
            return find_centroid(v, u);
        }
    }
    return u;
}
 
void decompose(int u, int x) {
    dfs(u, -1);
    sizes = len[u];
    int centroid = find_centroid(u, -1);
    parent[centroid] = x + 1;
    for (int v : edge[centroid]) {
        edge[v].erase(centroid);
        decompose(v, centroid);
    }
    edge[centroid] = {};
}
 
void generator() {
    int u, v;
    edge.resize(n);
    parent.resize(n, 0);
    len.resize(n, 0);
 
    for (int i = 0; i < n - 1; i++) {
        scanf("%d", &v);
        scanf("%d", &u);
        --u;
        --v;
        edge[u].insert(v);
        edge[v].insert(u);
    }
 
    decompose(0, -1);
}
 
int main() {
    std::iostream::sync_with_stdio(false);
 
    scanf("%d", &n);
 
    generator();
 
    for (int i : parent) {
        printf("%d", i);
        printf(" ");
    }
 
    return 0;
}