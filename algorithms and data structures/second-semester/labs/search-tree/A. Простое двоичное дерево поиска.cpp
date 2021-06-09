#include <bits/stdc++.h>
 
using namespace std;
 
struct Node{
    int value;
    Node* left;
    Node* right;
    Node* parent;
    Node(int v) {
        value = v;
        left = NULL;
        right = NULL;
        parent = NULL;
    }
};
 
Node* exists(Node* v, int key) {
    if (v == 0) {
        return v;
    }
    if (v -> value == key) {
        return v;
    }
    if (key > v -> value) {
        return exists(v -> right, key);
    } else {
        return  exists(v -> left, key);
    }
}
 
Node* insert(Node* v, int key) {
    if (v == 0) {
        return new Node(key);
    }
    if (key < v -> value) {
        v -> left = insert(v -> left, key);
    } else if (key > v -> value) {
        v -> right = insert(v -> right, key);
    }
    return v;
}
 
Node* next(Node* v, int key) {
    if (v == 0) {
        return v;
    }
    if (key < v -> value) {
        Node* ans = next(v -> left, key);
        if (ans) {
            return ans;
        } else {
            return v;
        }
    } else {
        return next(v -> right, key);
    }
}
 
Node* prev(Node* v, int key) {
    if (v == 0) {
        return v;
    }
    if (v -> value < key) {
        Node* ans = prev(v -> right, key);
        if (ans) {
            return ans;
        } else {
            return v;
        }
    } else {
        return prev(v -> left, key);
    }
}
 
Node* del(Node* v, int key) {
    if (v == 0) {
        return v;
    }
    if (v -> value == key) {
        Node v2 = (*v);
        delete v;
        if (!v2.left) {
            return v2.right;
        }
        Node* p = v2.left;
        while (p -> right) {
            p = p -> right;
        }
        p -> right = v2.right;
        return v2.left;
    } else if (key < v -> value) {
        v -> left = del(v -> left, key);
    } else {
        v -> right = del(v -> right, key);
    }
    return v;
}
 
int main() {
    ios_base::sync_with_stdio(0), cin.tie(0), cout.tie(0);
    Node* tree = NULL;
    string operations;
    int key;
    while (cin >> operations >> key) {
        if (operations == "insert") {
            tree = insert(tree, key);
        } else if (operations == "exists") {
            if (exists(tree, key)) {
                cout << "true" << endl;
            } else {
                cout << "false" << endl;
            }
        } else if (operations == "next") {
            Node *v = next(tree, key);
            if (v) {
                cout << v -> value << endl;
            } else {
                cout << "none" << endl;
            }
        } else if (operations == "prev") {
            Node *v = prev(tree, key);
            if (v) {
                cout << v -> value << endl;
            } else {
                cout << "none" << endl;
            }
        } else {
            tree = del(tree, key);
        }
    }
    return 0;
}