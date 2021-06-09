#include <bits/stdc++.h>
 
using namespace std;
 
int N = 1 << 19;
int MX = 200000;
 
struct node
{
    int mx;
    int id;
    int ad;
};
 
vector<node> tree(2 * N - 1);
 
struct rect
{
    int x;
    int y1;
    int y2;
    bool open;
};
 
vector<rect> queries;
 
bool gen(rect a, rect b) {
    if(a.x == b.x) {
        return a.open > b.open;
    } else {
        return a.x < b.x;
    }
}
 
void update(int L, int R, int v, int l, int r, int x) {
    if (R <= l or r <= L) {
        return;
    }
    if (l >= L and r <= R) {
        tree[v].ad += x;
        tree[v].mx += x;
        return;
    }
    tree[2 * v + 1].mx += tree[v].ad;
    tree[2 * v + 2].mx += tree[v].ad;
    tree[2 * v + 1].ad += tree[v].ad;
    tree[2 * v + 2].ad += tree[v].ad;
    tree[v].ad = 0;
    int m = (l + r) / 2;
    update(L, R, 2 * v + 1, l, m, x);
    update(L, R, 2 * v + 2, m, r, x);
    tree[v].mx = max(tree[2 * v + 1].mx, tree[2 * v + 2].mx);
    if(tree[2 * v + 1].mx > tree[2 * v + 2].mx) {
        tree[v].id = tree[2 * v + 1].id;
    } else {
        tree[v].id = tree[2 * v + 2].id;
    }
}
 
int main() {
    int n;
    cin >> n;
    int x1, y1, x2, y2;
    int i = 0;
    while (i < n) {
        cin >> x1 >> y1 >> x2 >> y2;
        queries.push_back({x1 + MX, y1 + MX, y2 + MX, true});
        queries.push_back({x2 + MX, y2 + MX, y1 + MX, false});
        ++i;
    }
    sort(queries.begin(), queries.end(), gen);
    
    int cur = N - 1;
    
    while (cur < 2 * N - 1) {
        tree[cur] = {0, cur - N + 1, 0};
        cur++;
    }
    
    int cur1 = N - 2;
    
    while (cur1 > 0) {
        tree[cur1] = {0, tree[2 * cur1 + 1].id, 0};
        --cur1;
    }
    
    int x = 0, y = 0, m_x = 0;
    
    i = 0;
    while (i < queries.size()) {
        if (queries[i].open) {
            update(queries[i].y1 + N - 1, queries[i].y2 + N, 0, N - 1, 2 * N - 1, 1);
        } else {
            update(queries[i].y2 + N - 1, queries[i].y1 + N, 0, N - 1, 2 * N - 1, -1);
        }
        node res = tree[0];
        if (res.mx > m_x)
        {
            m_x = res.mx;
            y = res.id;
            x = queries[i].x;
        }
        ++i;
    }
    cout << m_x << '\n' << x - MX << " " << y - MX;
    return 0;
}