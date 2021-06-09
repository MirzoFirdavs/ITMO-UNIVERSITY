#include <bits/stdc++.h>
 
struct node {
    node *left;
    node *right;
    node *parent;
    int size;
    long long depth;
    long long current_depth;
 
    node() : left(nullptr), right(nullptr), parent(nullptr), size(1), depth(0), current_depth(0) {};
};
 
std::vector<node *> graph;
std::vector<std::vector<int>> edge;
std::vector<int> used;
 
node *splay(node *x) {
    if (x and (x->parent == nullptr or (x->parent->left != x and x->parent->right != x))) {
        if (x->depth) {
            if (x->left) {
                x->left->depth += x->depth;
                x->left->current_depth += x->depth;
            }
            if (x->right) {
                x->right->depth += x->depth;
                x->right->current_depth += x->depth;
            }
            x->depth = 0;
        }
        x->size = 1;
        if (x->left) {
            x->size += x->left->size;
        }
        if (x->right) {
            x->size += x->right->size;
        }
        x->depth = 0;
        return x;
    }
    auto p = x->parent;
    auto g = p->parent;
    if (p->parent == nullptr or (p->parent->left != p and p->parent->right != p)) {
        auto pp = x->parent;
        auto gg = pp->parent;
        if (gg) {
            if (gg->depth) {
                if (gg->left) {
                    gg->left->depth += gg->depth;
                    gg->left->current_depth += gg->depth;
                }
                if (gg->right) {
                    gg->right->depth += gg->depth;
                    gg->right->current_depth += gg->depth;
                }
                gg->depth = 0;
            }
        }
        if (pp->depth) {
            if (pp->left) {
                pp->left->depth += pp->depth;
                pp->left->current_depth += pp->depth;
            }
            if (pp->right) {
                pp->right->depth += pp->depth;
                pp->right->current_depth += pp->depth;
            }
            pp->depth = 0;
        }
        if (x->depth) {
            if (x->left) {
                x->left->depth += x->depth;
                x->left->current_depth += x->depth;
            }
            if (x->right) {
                x->right->depth += x->depth;
                x->right->current_depth += x->depth;
            }
            x->depth = 0;
        }
        if (!(pp->parent == nullptr or (pp->parent->left != pp and pp->parent->right != pp))) {
            if (gg->left == pp) {
                gg->left = x;
            } else {
                gg->right = x;
            }
        }
        if (pp->left == x) {
            pp->left = x->right;
            x->right = pp;
        } else {
            pp->right = x->left;
            x->left = pp;
        }
        if (x->left) {
            x->left->parent = x;
        }
        if (x->right) {
            x->right->parent = x;
        }
        if (pp->left) {
            pp->left->parent = pp;
        }
        if (pp->right) {
            pp->right->parent = pp;
        }
        x->parent = gg;
        pp->size = 1;
        if (pp->left) {
            pp->size += pp->left->size;
        }
        if (pp->right) {
            pp->size += pp->right->size;
        }
        pp->depth = 0;
        x->size = 1;
        if (x->left) {
            x->size += x->left->size;
        }
        if (x->right) {
            x->size += x->right->size;
        }
        x->depth = 0;
        if (gg) {
            gg->size = 1;
            if (gg->left) {
                gg->size += gg->left->size;
            }
            if (gg->right) {
                gg->size += gg->right->size;
            }
            gg->depth = 0;
        }
        return x;
    }
    if ((p->left == x) == (g->left == p)) {
        auto pp = p->parent;
        auto gg = pp->parent;
        if (gg) {
            if (gg->depth) {
                if (gg->left) {
                    gg->left->depth += gg->depth;
                    gg->left->current_depth += gg->depth;
                }
                if (gg->right) {
                    gg->right->depth += gg->depth;
                    gg->right->current_depth += gg->depth;
                }
                gg->depth = 0;
            }
        }
        if (pp->depth) {
            if (pp->left) {
                pp->left->depth += pp->depth;
                pp->left->current_depth += pp->depth;
            }
            if (pp->right) {
                pp->right->depth += pp->depth;
                pp->right->current_depth += pp->depth;
            }
            pp->depth = 0;
        }
        if (p->depth) {
            if (p->left) {
                p->left->depth += p->depth;
                p->left->current_depth += p->depth;
            }
            if (p->right) {
                p->right->depth += p->depth;
                p->right->current_depth += p->depth;
            }
            p->depth = 0;
        }
        if (!(pp->parent == nullptr or (pp->parent->left != pp and pp->parent->right != pp))) {
            if (gg->left == pp) {
                gg->left = p;
            } else {
                gg->right = p;
            }
        }
        if (pp->left == p) {
            pp->left = p->right;
            p->right = pp;
        } else {
            pp->right = p->left;
            p->left = pp;
        }
        if (p->left) {
            p->left->parent = p;
        }
        if (p->right) {
            p->right->parent = p;
        }
        if (pp->left) {
            pp->left->parent = pp;
        }
        if (pp->right) {
            pp->right->parent = pp;
        }
        p->parent = gg;
        pp->size = 1;
        if (pp->left) {
            pp->size += pp->left->size;
        }
        if (pp->right) {
            pp->size += pp->right->size;
        }
        pp->depth = 0;
        p->size = 1;
        if (p->left) {
            p->size += p->left->size;
        }
        if (p->right) {
            p->size += p->right->size;
        }
        p->depth = 0;
        if (gg) {
            gg->size = 1;
            if (gg->left) {
                gg->size += gg->left->size;
            }
            if (gg->right) {
                gg->size += gg->right->size;
            }
            gg->depth = 0;
        }
    } else {
        auto pp = x->parent;
        auto gg = pp->parent;
        if (gg) {
            if (gg->depth) {
                if (gg->left) {
                    gg->left->depth += gg->depth;
                    gg->left->current_depth += gg->depth;
                }
                if (gg->right) {
                    gg->right->depth += gg->depth;
                    gg->right->current_depth += gg->depth;
                }
                gg->depth = 0;
            }
        }
        if (pp->depth) {
            if (pp->left) {
                pp->left->depth += pp->depth;
                pp->left->current_depth += pp->depth;
            }
            if (pp->right) {
                pp->right->depth += pp->depth;
                pp->right->current_depth += pp->depth;
            }
            pp->depth = 0;
        }
        if (x->depth) {
            if (x->left) {
                x->left->depth += x->depth;
                x->left->current_depth += x->depth;
            }
            if (x->right) {
                x->right->depth += x->depth;
                x->right->current_depth += x->depth;
            }
            x->depth = 0;
        }
        if (!(pp->parent == nullptr or (pp->parent->left != pp and pp->parent->right != pp))) {
            if (gg->left == pp) {
                gg->left = x;
            } else {
                gg->right = x;
            }
        }
        if (pp->left == x) {
            pp->left = x->right;
            x->right = pp;
        } else {
            pp->right = x->left;
            x->left = pp;
        }
        if (x->left) {
            x->left->parent = x;
        }
        if (x->right) {
            x->right->parent = x;
        }
        if (pp->left) {
            pp->left->parent = pp;
        }
        if (pp->right) {
            pp->right->parent = pp;
        }
        x->parent = gg;
        pp->size = 1;
        if (pp->left) {
            pp->size += pp->left->size;
        }
        if (pp->right) {
            pp->size += pp->right->size;
        }
        pp->depth = 0;
        x->size = 1;
        if (x->left) {
            x->size += x->left->size;
        }
        if (x->right) {
            x->size += x->right->size;
        }
        x->depth = 0;
        if (gg) {
            gg->size = 1;
            if (gg->left)
                gg->size += gg->left->size;
            if (gg->right)
                gg->size += gg->right->size;
            gg->depth = 0;
        }
    }
    auto pp = x->parent;
    auto gg = pp->parent;
    if (gg) {
        if (gg->depth) {
            if (gg->left) {
                gg->left->depth += gg->depth;
                gg->left->current_depth += gg->depth;
            }
            if (gg->right) {
                gg->right->depth += gg->depth;
                gg->right->current_depth += gg->depth;
            }
            gg->depth = 0;
        }
    }
    if (pp->depth) {
        if (pp->left) {
            pp->left->depth += pp->depth;
            pp->left->current_depth += pp->depth;
        }
        if (pp->right) {
            pp->right->depth += pp->depth;
            pp->right->current_depth += pp->depth;
        }
        pp->depth = 0;
    }
    if (x->depth) {
        if (x->left) {
            x->left->depth += x->depth;
            x->left->current_depth += x->depth;
        }
        if (x->right) {
            x->right->depth += x->depth;
            x->right->current_depth += x->depth;
        }
        x->depth = 0;
    }
    if (!(pp->parent == nullptr or (pp->parent->left != pp and pp->parent->right != pp))) {
        if (gg->left == pp) {
            gg->left = x;
        } else {
            gg->right = x;
        }
    }
    if (pp->left == x) {
        pp->left = x->right;
        x->right = pp;
    } else {
        pp->right = x->left;
        x->left = pp;
    }
    if (x->left) {
        x->left->parent = x;
    }
    if (x->right) {
        x->right->parent = x;
    }
    if (pp->left) {
        pp->left->parent = pp;
    }
    if (pp->right) {
        pp->right->parent = pp;
    }
    x->parent = gg;
    pp->size = 1;
    if (pp->left) {
        pp->size += pp->left->size;
    }
    if (pp->right) {
        pp->size += pp->right->size;
    }
    pp->depth = 0;
    x->size = 1;
    if (x->left) {
        x->size += x->left->size;
    }
    if (x->right) {
        x->size += x->right->size;
    }
    x->depth = 0;
    if (gg) {
        gg->size = 1;
        if (gg->left) {
            gg->size += gg->left->size;
        }
        if (gg->right) {
            gg->size += gg->right->size;
        }
        gg->depth = 0;
    }
    return splay(x);
}
 
node *expose(node *x) {
    node *last = nullptr;
    for (auto y = x; y; y = y->parent) {
        splay(y);
        y->left = last;
        y->size = 1;
        if (y->left) {
            y->size += y->left->size;
        }
        if (y->right) {
            y->size += y->right->size;
        }
        y->depth = 0;
        last = y;
    }
    splay(x);
    return last;
}
 
void dfs(int u) {
    used[u] = true;
    for (int v : edge[u]) {
        if (!used[v]) {
            expose(graph[v]);
            graph[v]->parent = graph[u];
            dfs(v);
        }
    }
}
 
int main() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
    int n;
    std::cin >> n;
    graph.resize(n);
    edge.resize(n);
    used.resize(n);
 
    for (int i = 0; i < n; i++) {
        graph[i] = new node();
    }
 
    for (int i = 0; i < n - 1; i++) {
        int u, v;
        std::cin >> u >> v;
        --u;
        --v;
        edge[u].push_back(v);
        edge[v].push_back(u);
    }
    dfs(0);
 
    int m;
 
    std::cin >> m;
 
    for (int i = 0; i < m; i++) {
        char c;
        std::cin >> c;
        if (c == '+') {
            int u, v;
            long long d;
            std::cin >> u >> v >> d;
            --u;
            --v;
            node *x = graph[u];
            node *y = graph[v];
            expose(x);
            node *lca = expose(y);
            if (lca != x && lca != y) {
                splay(x);
                if (x) {
                    x->depth += d;
                    x->current_depth += d;
                }
            } else {
                expose((x == lca) ? y : x);
            }
            splay(lca);
            if (lca->left) {
                lca->left->depth += d;
                lca->left->current_depth += d;
            }
            lca->current_depth += d;
        } else if (c == '?') {
            int u;
            std::cin >> u;
            --u;
            splay(graph[u]);
            std::cout << graph[u]->current_depth << std::endl;
        }
    }
    return 0;
}