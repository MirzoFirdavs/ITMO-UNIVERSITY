#include <bits/stdc++.h>
#include <fstream>
 
using namespace std;
 
typedef vector<int>  T_summands;
ifstream fin("partition.in");
ofstream fout("partition.out");
 
void print_summands(int n_start, const T_summands&  summands) {
    for(T_summands::const_iterator  summands_it = summands.begin(); summands_it != summands.end(); ++summands_it) {
        fout << *summands_it << (summands_it != summands.end() - 1 ? "+" : "\n");
    }
}
 
void summands_partition(int n_start, int n_cur, const T_summands&  summands) {
    if (!n_cur) {
        print_summands(n_start, summands);
    }
 
    for (int cur_slag = summands.empty() ? 1 : summands.back(); n_cur - cur_slag >= 0; ++cur_slag) {
        T_summands  summands_new(summands);
        summands_new.push_back(cur_slag);
        summands_partition(n_start, n_cur - cur_slag, summands_new);
    }
}
 
int main() {
    int n = 0;
    fin >> n;
 
    T_summands  summands;
    summands_partition(n, n, summands);
 
    return 0;
}