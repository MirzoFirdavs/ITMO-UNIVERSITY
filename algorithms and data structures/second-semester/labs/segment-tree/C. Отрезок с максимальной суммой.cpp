#include <bits/stdc++.h>
 
using namespace std;
 
long long a[100001];
 
struct Node
{
    long long s_m, prefix, suffix, m_x;
} Tree[400004];
 
void build(long long *a, long long v, long long ls, long long rs) {
    if (ls == rs) {
        Tree[v].m_x = Tree[v].prefix = Tree[v].suffix = Tree[v].s_m = a[ls];
    } else {
        long long m = (ls + rs) / 2;
        build(a, v * 2, ls, m);
        build(a, v * 2 + 1, m + 1, rs);
        Tree[v].prefix = max(Tree[v * 2].prefix, Tree[v * 2].s_m + Tree[v * 2 + 1].prefix);
        Tree[v].suffix = max(Tree[v * 2 + 1].suffix, Tree[v * 2 + 1].s_m + Tree[v * 2].suffix);
        Tree[v].s_m = Tree[v * 2].s_m + Tree[v * 2 + 1].s_m;
        Tree[v].m_x = max(max(Tree[v * 2].m_x, Tree[v * 2 + 1].m_x), Tree[v * 2].suffix + Tree[v * 2 + 1].prefix);
    }
}
 
struct Node query(long long v, long long ls, long long rs, long long l, long long r) {
    if (l == ls and r == rs){ 
        return Tree[v];
    }
    long long m = (ls + rs) / 2;
    if (r <= m){ 
        return query(2 * v, ls, m, l, r);
    }
    if (l > m) {
        return query(2 * v + 1, m + 1, rs, l, r);
    }
    struct Node LeftNode = query(2 * v, ls, m, l, m);
    struct Node RightNode = query(2 * v + 1, m + 1, rs, m + 1, r);
    Node cur;
    cur.prefix = max(LeftNode.prefix, LeftNode.s_m + RightNode.prefix);
    cur.suffix = max(RightNode.suffix, RightNode.s_m + LeftNode.suffix);
    cur.s_m = LeftNode.s_m + RightNode.s_m;
    cur.m_x = max(max(LeftNode.m_x, RightNode.m_x), LeftNode.suffix + RightNode.prefix);
    return cur;
}
 
void update(long long v, long long ls, long long rs, long long pos, long long val) {
    if (ls == rs) {
        Tree[v].m_x = val;
        Tree[v].prefix = val;
        Tree[v].suffix = val;
        Tree[v].s_m = val;
        return;
    }
    long long m = (ls + rs) / 2;
    if (pos <= m) {
        update(2 * v, ls, m, pos, val);
    } else {
        update(2 * v + 1, m + 1, rs, pos, val);
    }
    Tree[v].prefix = max(Tree[v * 2].prefix, Tree[v * 2].s_m + Tree[v * 2 + 1].prefix);
    Tree[v].suffix = max(Tree[v * 2 + 1].suffix, Tree[v * 2 + 1].s_m + Tree[v * 2].suffix);
    Tree[v].s_m = Tree[v * 2].s_m + Tree[v * 2 + 1].s_m;
    Tree[v].m_x = max(max(Tree[v * 2].m_x, Tree[v * 2 + 1].m_x), Tree[v * 2].suffix + Tree[v * 2 + 1].prefix);
}
 
int main() {
    long long n, m;
    cin >> n >> m;
    for (int i = 0; i < n; ++i) {
        cin >> a[i];
    }
    build(a, 1, 0, n - 1);
    int pos, val;
    Node ans = query(1, 0, n - 1, 0, n - 1);
    if(ans.m_x <= 0) {
        cout << 0 << endl;
    } else {
        cout << ans.m_x << endl;
    }
    for(int i = 0; i < m; ++i) {
        cin >> pos >> val;
        update(1, 0, n - 1, pos, val);
        Node ans = query(1, 0, n - 1, 0, n - 1);
        if(ans.m_x <= 0) {
            cout << 0 << endl;
        } else {
            cout << ans.m_x << endl;
        }
    }
    return 0;
}