#include <bits/stdc++.h>
 
using namespace std;
 
struct Node {
    long long x;
    long long y;
    long long sze;
    long long l;
    long long r;
    long long s_m;
    Node *right;
    Node *left;
 
    Node(long long x, long long y) {
        left = nullptr;
        right = nullptr;
        this->x = x;
        this->y = y;
        sze = 1;
        l = x;
        r = x;
        s_m = x;
    }
};
 
vector<long long> arr;
 
Node *root = nullptr;
 
long long get_size(Node *node) {
    return node ? node->sze : 0;
}
 
long long get_sum(Node *node) {
    return node ? node->s_m : 0;
}
 
void update(Node *node) {
    node->sze = 1 + get_size(node->left) + get_size(node->right);
    if (!node->left) {
        node->l = node->x;
    } else {
        node->l = node->left->l;
    }
    if (!node->right) {
        node->r = node->x;
    } else {
        node->r = node->right->r;
    }
    node->s_m = node->x + get_sum(node->left) + get_sum(node->right);
}
 
pair<Node *, Node *> split(Node *node, long long k) {
    if (!node) {
        return make_pair(nullptr, nullptr);
    }
    long long l = get_size(node->left);
    if (l >= k) {
        pair<Node *, Node *> p = split(node->left, k);
        node->left = p.second;
        update(node);
        return make_pair(p.first, node);
    } else {
        pair<Node *, Node *> p = split(node->right, k - 1 - l);
        node->right = p.first;
        update(node);
        return make_pair(node, p.second);
    }
}
 
Node *merge(Node *left, Node *right) {
    if (!left) {
        return right;
    }
    if (!right) {
        return left;
    }
    if (left->y > right->y) {
        left->right = merge(left->right, right);
        update(left);
        return left;
    } else {
        right->left = merge(left, right->left);
        update(right);
        return right;
    }
}
 
void print_ans(Node *node) {
    if (node->left) {
        print_ans(node->left);
    }
    arr.push_back(node->x);
    if (node->right) {
        print_ans(node->right);
    }
}
 
void insert(long long k, long long x) {
    pair<Node *, Node *> sp = split(root, k);
    root = merge(sp.first, new Node(x, rand()));
    root = merge(root, sp.second);
}
 
 
Node *gen(Node *node) {
    if (!node->right) {
        return node->left;
    } else {
        node->right = gen(node->right);
        update(node);
        return node;
    }
}
 
void del(long long x) {
    pair<Node *, Node *> sp = split(root, x);
    sp.first = gen(sp.first);
    root = merge(sp.first, sp.second);
}
 
int main() {
    long long n, m, k, x, y;
    cin >> n >> m;
    string query;
    for (int i = 0; i < n; i++) {
        cin >> k;
        insert(i, k);
    }
 
    for (long i = 0; i < m; i++) {
        cin >> query;
        if (query == "add") {
            cin >> x >> y;
            insert(x, y);
        } else if (query == "del") {
            cin >> k;
            del(k);
        }
    }
    if (root == nullptr) {
        cout << 0;
        return 0;
    }
 
    print_ans(root);
    cout << arr.size() << endl;
    for (long long i : arr) {
        cout << i << " ";
    }
}