#include <bits/stdc++.h>
 
using namespace std;
 
struct Node {
    long long x;
    long long y;
    long long sze;
    Node *right;
    Node *left;
    bool reverse;
 
    Node(long long x, long long y) {
        left = nullptr;
        right = nullptr;
        this->x = x;
        this->y = y;
        sze = 1;
        reverse = false;
    }
};
 
Node *root = nullptr;
 
long long get_size(Node *node) {
    return node ? node->sze : 0;
}
 
void update(Node *node) {
    if (node) {
        node->sze = 1 + get_size(node->left) + get_size(node->right);
    }
}
 
void rev(Node *node) {
    node->reverse = !node->reverse;
}
 
void propagate(Node *node) {
    if (node->reverse) {
        Node *hNode;
        hNode = node->left;
        node->left = node->right;
        node->right = hNode;
        if (node->left) {
            rev(node->left);
        }
        if (node->right) {
            rev(node->right);
        }
        node->reverse = false;
    }
}
 
pair<Node *, Node *> split(Node *node, long long key) {
    if (!node) {
        return make_pair(nullptr, nullptr);
    }
    propagate(node);
    if (key > get_size(node->left)) {
        pair<Node *, Node *> sp = split(node->right, key - get_size(node->left) - 1);
        node->right = sp.first;
        update(node);
        return make_pair(node, sp.second);
    } else {
        pair<Node *, Node *> sp = split(node->left, key);
        node->left = sp.second;
        update(node);
        return make_pair(sp.first, node);
    }
}
 
Node *merge(Node *l, Node *r) {
    if (!l) {
        return r;
    }
    if (!r) {
        return l;
    }
    propagate(l);
    propagate(r);
    if (l->y > r->y) {
        l->right = merge(l->right, r);
        update(l);
        return l;
    } else {
        r->left = merge(l, r->left);
        update(r);
        return r;
    }
}
 
void insert(long long val) {
    root = merge(root, new Node(val, rand()));
}
 
void reverse(int l, int r) {
    pair<Node *, Node *> sp0 = split(root, l - 1);
    pair<Node *, Node *> sp = split(sp0.second, r - l + 1);
    rev(sp.first);
    root = merge(sp0.first, merge(sp.first, sp.second));
}
 
void print_ans(Node *node) {
    if (!node) {
        return;
    }
    propagate(node);
    if (node->left) {
        print_ans(node->left);
    }
    cout << node->x << " ";
    if (node->right) {
        print_ans(node->right);
    }
}
 
int main() {
    int n, m;
    cin >> n >> m;
    for (int i = 1; i <= n; ++i) {
        insert(i);
    }
    for (int i = 0; i < m; ++i) {
        int x, y;
        cin >> x >> y;
        reverse(x, y);
    }
    print_ans(root);
}