#include <bits/stdc++.h>
 
using namespace std;
 
vector<long long> heap;
    
void Insert(long long x){
    heap.push_back(x);
    long long n = heap.size();
    int i = n - 1;
    while(i > 0 and heap[i] > heap[(i - 1) /2]){
        swap(heap[i] , heap[(i - 1) / 2]);
        i = (i - 1) / 2;
    }
}
 
long long extract_max(){
    long long n = heap.size();
    long long res = heap[0];
    heap[0] = heap[--n];
    heap.pop_back();
    long long i = 0;
    while(true){
        long long j = i;
        if((2 * i + 1 < n) and (heap[2 * i + 1] > heap[j])){
            j = 2 * i + 1;
        }
        if((2 * i + 2 < n) and (heap[2 * i + 2] > heap[j])){
            j = 2 * i + 2;
        }
        if(j == i){
            break;
        }
        swap(heap[i] , heap[j]);
        i = j;
    }
    return res;
}
 
int main(){
    long long n , k , z;
    cin >> n;
    for(int i = 0 ; i < n ; ++i){
        cin >> k;
        if(k == 0){
            cin >> z;
            Insert(z);
        }
        else{
           cout << extract_max() << endl;
        }
    }
    return 0;
}