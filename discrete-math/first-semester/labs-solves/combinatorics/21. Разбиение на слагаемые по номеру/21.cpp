#include <bits/stdc++.h>
#include <fstream>
 
using namespace std;
 
ifstream fin("num2part.in");
ofstream fout("num2part.out");
 
int main(){
    long long n, k;
    fin >> n >> k;
    vector <int> b;
 
    for (int i = 0; i < n; ++i) {
        b.push_back(1);
    }
    for (int i = 0; i < k; ++i) {
        b[b.size() - 1]--;
        b[b.size() - 2]++;
        if (b[b.size() - 2] > b[b.size() - 1]) {
            b[b.size() - 2] += b[b.size() - 1];
            b.pop_back();
        } else{
            while (b[b.size() - 2] * 2 <= b[b.size() - 1]) {
                b.push_back(b[b.size() - 1] - b[b.size() - 2]);
                b[b.size() - 2] = b[b.size() - 3];
            }
        }
    }
 
    for (int i = 0; i < b.size() - 1; ++i) {
        fout << b[i] << '+';
    }
    fout << b[b.size() - 1];
    return 0;
}