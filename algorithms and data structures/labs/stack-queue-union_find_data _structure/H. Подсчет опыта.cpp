#include<bits/stdc++.h>
 
using namespace std;
 
vector<int>p;
vector <int> ex;
 
int get(int x) {
    if (x == p[x]) {
        return x;
    } else {
        return get(p[x]);
    }
}
 
/*
void Union(int x, int y, vector<int> clans, vector <int> p, vector <int> exp) {
    x = get(x, p);
    y = get(y, p);
    if (x != y) {
       if (clans[x] < clans[y]) {
            swap(x, y);
       }
       p[y] = x;
       exp[y] -= exp[x];
       if (clans[x] == clans[y]) {
            clans[x] += 1;
       }
    }
}
*/
 
/*
void add(int x, int y, vector<int> exp, vector<int> p) {
    exp[get(x, p)] += y;
}
*/
 
int countExp(int x) {
    if (x == p[x]) {
        return ex[x];
    } else {
        return countExp(p[x]) + ex[x];
    }
}
 
int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    int n, m;
    cin >> n >> m;
    vector <int> clans;
    for (int i = 0; i <= n; ++i) {
        clans.push_back(0);
        ex.push_back(0);
        p.push_back(i);
    }
 
    string k;
    int y, x;
 
    for (int i = 0; i < m; ++i) {
        cin >> k;
        if (k == "join") {
            cin >> x >> y;
            //Union(y, z, clans, p, exp);
            x = get(x);
            y = get(y);
            if (x != y) {
                if (clans[x] < clans[y]) {
                    swap(x, y);
                }
                p[y] = x;
                ex[y] -= ex[x];
                if (clans[x] == clans[y]) {
                    clans[x] += 1;
                }
            }
        } else if (k == "add") {
            cin >> x >> y;
            //add(y, z, exp, p);
            ex[get(x)] += y;
        } else if (k == "get") {
            cin >> x;
            cout << countExp(x) << '\n';
        }
    }
 
    /*
    for (int i = 0; i <= n; ++i) {
        cout << clans[i] << " ";
    }*/
 
    return 0;
}