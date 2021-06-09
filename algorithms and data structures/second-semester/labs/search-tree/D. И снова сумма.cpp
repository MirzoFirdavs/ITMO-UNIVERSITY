#include <bits/stdc++.h>
 
using namespace std;
 
struct Node {
    int y = 0;
    long long x = 0, s_m = 0;
    Node* left = nullptr;
    Node* right = nullptr;
};
 
Node* rt = nullptr;
 
void split (Node* tr, long long k, Node* &t1, Node* &t2) {
    if (tr == nullptr) {
        t1 = t2 = nullptr;
        return;
    }
    if (tr -> x >= k) {
        t2 = tr;
        split(t2 -> left, k, t1, t2 -> left);
        t2 -> s_m = t2 -> x;
        if (t2 -> left != nullptr) {
            t2 -> s_m += t2 -> left -> s_m;
        }
        if (t2 -> right != nullptr) {
            t2 -> s_m += t2 -> right -> s_m;
        }
    } else {
        t1 = tr;
        split(t1 -> right, k, t1 -> right, t2);
        t1 -> s_m = t1 -> x;
        if (t1 -> left != nullptr) {
            t1 -> s_m += t1 -> left -> s_m;
        }
        if (t1 -> right != nullptr) {
            t1 -> s_m += t1 -> right -> s_m;
        }
    }
}
 
void merge (Node* t1, Node* t2, Node* &tr) {
    if (t1 == nullptr) {
        tr = t2;
        return;
    }
    if (t2 == nullptr) {
        tr = t1;
        return;
    }
    if (t1 -> y >= t2 -> y) {
        tr = t2;
        merge(t1, tr -> left, tr -> left);
    } else {
        tr = t1;
        merge(tr -> right, t2, tr -> right);
 
    }
    tr -> s_m = tr -> x;
    if (tr -> left != nullptr) {
        tr -> s_m += tr -> left -> s_m;
    }
    if (tr -> right != nullptr) {
        tr -> s_m += tr -> right -> s_m;
    }
}
 
bool exists (Node* tr, long long k) {
    if (tr == nullptr) {
        return false;
    }
    if (tr -> x == k) {
        return true;
    } else if (k < tr -> x) {
        return exists(tr->left, k);
    } else {
        return exists(tr->right, k);
    }
}
 
void insert (long long k) {
    if (exists(rt, k)) {
        return;
    }
    Node* temp = new Node;
    temp -> x = k;
    temp -> y = rand();
    temp -> s_m = temp -> x;
    if (temp -> left != nullptr) {
        temp -> s_m += temp -> left -> s_m;
    }
    if (temp -> right != nullptr) {
        temp -> s_m += temp -> right -> s_m;
    }
    Node* l = new Node;
    Node* r = new Node;
    split(rt, k, l, r);
    merge(l, temp, l);
    merge(l, r, rt);
}
 
long long get_sum (int left, int right) {
    Node* l;
    Node* m;
    Node* r;
    split(rt, left, l, m);
    split(m, right + 1, m, r);
    long long res = 0;
    if (m != nullptr) {
        res = m -> s_m;
    }
    merge(l, m, m);
    merge(m, r, rt);
    return res;
}
 
int main () {
    int n;
    cin >> n;
    long long last = 0;
    bool was = false;
    for (int i = 0; i < n; ++i) {
        char c;
        cin >> c;
        if (c == '?') {
            int l, r;
            cin >> l >> r;
            was = true;
            last = get_sum(l, r);
            cout << last << endl;
        } else if (c == '+') {
            long long m;
            cin >> m;
            if (was) m = (m + last) % 1000000000;
            was = false;
            insert(m);
        }
    }
}