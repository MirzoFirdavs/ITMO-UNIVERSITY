#include <bits/stdc++.h>
 
using namespace std;
 
int main() {
    ios::sync_with_stdio(0);
    cin.tie(0);
    int n, x;
    char k;
    cin >> n;
    deque <int> qst;
    deque <int> qfi;
    cin >> k;
    cin >> x;
    qst.push_back(x);
    for (int i = 1; i < n; ++i) {
        cin >> k;
        if (k == '+') {
            cin >> x;
            if (qst.size() < qfi.size()) {
                qst.push_back(qfi[0]);
                qfi.pop_front();
                qfi.push_back(x);
            } else {
                qfi. push_back(x);
            }
        }
        else if (k == '*') {
            cin >> x;
            if (qst.size() < qfi.size()) {
                qst.push_back(qfi[0]);
                qfi.pop_front();
                qfi.push_front(x);
            } else {
                qfi.push_front(x);
            }
        } else {
           cout << qst[0] << '\n';
           if (qst.size() > qfi.size()) {
                qst.pop_front();
           } else {
               qst.pop_front();
               qst.push_back(qfi.front());
               qfi.pop_front();
           }
        }
    }
    return 0;
}