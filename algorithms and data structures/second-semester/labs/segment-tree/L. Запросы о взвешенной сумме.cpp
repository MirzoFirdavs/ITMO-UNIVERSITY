#include <bits/stdc++.h>
 
using namespace std;
 
struct Value {
    long long sum, wsum, len;
    Value operator+(Value a) {
        return {sum + a.sum, wsum + a.wsum + a.sum * len, len + a.len};
    }
    void operator += (long long d) {
        sum += d * len;
        wsum += len * (len + 1) / 2 * d;
    }
};
 
class SegmentTree {
    struct Node {
        Value v;
        long long add;
        Node* l, * r;
        Node() {
            l = NULL;
            r = NULL;
            v = {0, 0, 0};
            add = 0;
        }
    };
    Node* build(vector<long long> & arr, long long tl, long long tr) {
        Node* node = new Node();
        if (tl != tr) {
            int tm = (tl + tr) / 2;
            node->l = build(arr, tl, tm);
            node->r = build(arr, tm + 1, tr);
            node->v = node->l->v + node->r->v;
        }
        else {
            node->v = {arr[tl], arr[tl], 1};
        }
        return node;
    }
    
    void update(Node* node, long long tl, long long tr, long long l, long long r, long long d) {
        if (l > r)
            return;
        if (tl == l and tr == r) {
            node->add += d;
            node->v += d;
            return;
        }
        long long tm = (tl + tr) / 2;
        if (node->add) {
            node->l->add += node->add;
            node->r->add += node->add;
            node->l->v += node->add;
            node->r->v += node->add;
            node->add = 0;
        }
        update(node->l, tl, tm, l, min(tm, r), d);
        update(node->r, tm + 1, tr, max(tm + 1, l), r, d);
        node->v = node->l->v + node->r->v;
    }
    Value query(Node* node, long long tl, long long tr, long long l, long long r) {
        if (l > r) {
            return {0, 0, 0};
        }
        if (tl == l and tr == r) {
            return node->v;
        }
        long long tm = (tl + tr) / 2;
        if (node->add) {
            node->l->add += node->add;
            node->r->add += node->add;
            node->l->v += node->add;
            node->r->v += node->add;
            node->add = 0;
        }
        return query(node->l, tl, tm, l, min(r, tm)) + query(node->r, tm + 1, tr, max(tm + 1, l), r);
    }
 
    long long size;
    Node* root;
 
public:
    SegmentTree(vector<long long>& arr) {
        size = arr.size();
        root = build(arr, 0, size - 1);
    }
    void add(long long l, long long r, long long d) {
        update(root, 0, size - 1, l, r, d);
    }
    long long sum(long long l, long long r) {
        return query(root, 0, size - 1, l, r).wsum;
    }
};
 
int main() {
    ios_base::sync_with_stdio(false);
    long long n, m, type, l, r, v;
    cin >> n >> m;
    vector<long long> arr(n);
    int i = 0;
    while (i < n) {
        cin >> arr[i];
        ++i;
    }
    SegmentTree t(arr);
    i = 0;
    while (i < m) {
        cin >> type >> l >> r;
        l--; r--;
        if (type == 1) {
            cin >> v;
            t.add(l, r, v);
        } else {
            cout << t.sum(l, r) << endl;
        }
        ++i;
    }
    return 0;
}