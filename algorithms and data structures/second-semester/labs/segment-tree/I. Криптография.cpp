#include <bits/stdc++.h>
 
using namespace std;
 
int r;
 
struct node
{
    int x11;
    int x12;
    int x21;
    int x22;
};
 
node E = {1, 0, 0, 1};
vector<node> tree((2 * (1 << 18)) - 1);
 
node update(int l, int rr) {
    node ls = E, rs = E;
    while (l <= rr) {
        if (l % 2 == 0) {
            ls = {(ls.x11 * tree[l].x11 + ls.x12 * tree[l].x21) % r,
            (ls.x11 * tree[l].x12 + ls.x12 * tree[l].x22) % r,
            (ls.x21 * tree[l].x11 + ls.x22 * tree[l].x21) % r,
            (ls.x21 * tree[l].x12 + ls.x22 * tree[l].x22) % r,};
        }
        l = l / 2;
        if (rr % 2 == 1) {
            rs = {(tree[rr].x11 * rs.x11 + tree[rr].x12 * rs.x21) % r,
            (tree[rr].x11 * rs.x12 + tree[rr].x12 * rs.x22) % r,
            (tree[rr].x21 * rs.x11 + tree[rr].x22 * rs.x21) % r,
            (tree[rr].x21 * rs.x12 + tree[rr].x22 * rs.x22) % r,};
        }
        rr = rr / 2 - 1;
    }
    return {(ls.x11 * rs.x11 + ls.x12 * rs.x21) % r,
            (ls.x11 * rs.x12 + ls.x12 * rs.x22) % r,
            (ls.x21 * rs.x11 + ls.x22 * rs.x21) % r,
            (ls.x21 * rs.x12 + ls.x22 * rs.x22) % r,};
}
 
int main() {
    ios_base::sync_with_stdio(false);
    int n, m;
    cin >> r >> n >> m;
 
    int v = 1;
    while (v < n) {
        v *= 2;
    }
 
    int x11, x22, x12, x21;
    int i = v - 1;
    while (i < v + n - 1) {
        cin >> x11 >> x12 >> x21 >> x22;
        tree[i].x11 = x11 % r;
        tree[i].x12 = x12 % r;
        tree[i].x21 = x21 % r;
        tree[i].x22 = x22 % r;
        ++i;
    }
    i = v + n - 1;
    while (i < 2 * v - 1) {
        tree[i] = E;
        ++i;
    }
    i = v - 2;
    while(i >= 0) {
        tree[i] = {(tree[2 * i + 1].x11 * tree[2 * i + 2].x11 + tree[2 * i + 1].x12 * tree[2 * i + 2].x21) % r,
            (tree[2 * i + 1].x11 * tree[2 * i + 2].x12 + tree[2 * i + 1].x12 * tree[2 * i + 2].x22) % r,
            (tree[2 * i + 1].x21 * tree[2 * i + 2].x11 + tree[2 * i + 1].x22 * tree[2 * i + 2].x21) % r,
            (tree[2 * i + 1].x21 * tree[2 * i + 2].x12 + tree[2 * i + 1].x22 * tree[2 * i + 2].x22) % r,};
        --i;
    }
    int x1, x2;
    i = 0;
    while (i < m) {
        cin >> x1 >> x2;
        node ans = update(v - 1 + x1 - 1, v - 1 + x2 - 1);
        cout << ans.x11 << " " << ans.x12 << endl << ans.x21 << " " << ans.x22 << endl << endl;
        ++i;
    }
    return 0;
}