#include <bits/stdc++.h>
 
using namespace std;
 
int get (vector <int> p, int x) {
    return p[x];
}/*
void Union (vector <int> m2, vector <int> m1, vector <int> p, vector < vector <int> > lists, int x, int y) {
    x = get(p, x);
    y = get(p, y);
    if (x != y) {
        if (lists[x].size() > lists[y].size()) {
            swap(x, y);
        }
        m1[y] = min(m1[x], m1[y]);
        m2[y] = max(m2[x], m2[y]);
        for (auto i: lists[x]) {
            p[i] = y;
            lists[y].push_back(i);
        }
    }
}*/
 
int main() {
    int n, x, y;
    cin >> n;
 
    vector < vector <int> > lists;
    vector <int> p;
    vector <int> m1;
    vector <int> m2 ;
 
    for (int i = 0; i < n + 1; ++i) {
        p.push_back(i);
        m1.push_back(i);
        m2.push_back(i);
    }
 
    vector <int> k;
 
    for (int i = 0; i < n + 1; ++i) {
        k.push_back(i);
        //cout << k[0] << " ";
        lists.push_back(k);
        k.clear();
        //cout << lists[i][0] << " ";
    }
 
    string st;
 
    while (cin >> st) {
        if (st == "union") {
            cin >> x >> y;
            //Union(m2, m1, p, lists, x, y);
            //x = get(p, x);
            //y = get(p, y);
            x = p[x];
            y = p[y];
            if (x != y) {
                if (lists[x].size() > lists[y].size()) {
                    swap(x, y);
                }
                m1[y] = min(m1[x], m1[y]);
                m2[y] = max(m2[x], m2[y]);
                for (auto i: lists[x]) {
                    p[i] = y;
                    lists[y].push_back(i);
                }
            }
        } else {
            cin >> x;
            //int cur = get(p, x);
            int cur = p[x];
            cout << m1[cur] << " " << m2[cur] << " " << lists[cur].size() << '\n';
        }
    }
 
    /*
    for (int i = 0; i < n + 1; ++i) {
        for (int j = 0; j < lists[i].size(); ++j) {
            cout << lists[i][j] << " ";
        }
        cout << '\n';
    }*/
    return 0;
}