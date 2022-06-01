#include <complex>
#include <vector>
#include <iostream>
 
double const pi = 3.14159265358979323846;
int new_size = 1;
std::vector<int> result;
 
void fft(std::vector<std::complex<double>> &lst, bool flag) {
    int n = (int) lst.size();
    if (n == 1) {
        return;
    }
 
    std::vector<std::complex<double>> a_0(n / 2);
    std::vector<std::complex<double>> a_1(n / 2);
 
    int i = 0;
    int j = 0;
 
    while (i < n) {
        a_0[j] = lst[i];
        a_1[j] = lst[i + 1];
        i += 2;
        ++j;
    }
 
    fft(a_0, flag);
    fft(a_1, flag);
 
    double arg = 2 * pi / n * (flag ? -1 : 1);
 
    std::complex<double> w(1);
    std::complex<double> w_n(cos(arg), sin(arg));
 
    for (int ii = 0; ii < n / 2; ++ii) {
        lst[ii] = a_0[ii] + w * a_1[ii];
        lst[ii + n / 2] = a_0[ii] - w * a_1[ii];
 
        if (flag) {
            lst[ii] /= 2;
            lst[ii + n / 2] /= 2;
        }
 
        w *= w_n;
    }
}
 
void mul(std::vector<int> &lst_a, std::vector<int> &lst_b) {
    std::vector<std::complex<double>> f_a(lst_a.begin(), lst_a.end());
    std::vector<std::complex<double>> f_b(lst_b.begin(), lst_b.end());
 
    while (new_size < std::max(lst_a.size(), lst_b.size())) {
        new_size *= 2;
    }
 
    new_size *= 2;
 
    f_a.resize(new_size);
    f_b.resize(new_size);
 
    fft(f_a, false);
    fft(f_b, false);
 
    for (int i = 0; i < new_size; ++i) {
        f_a[i] *= f_b[i];
    }
 
    fft(f_a, true);
 
    for (auto element: f_a) {
        result.emplace_back(int(element.real() + 0.5));
    }
}
 
int main() {
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);
 
    int n;
 
    std::cin >> n;
 
    std::vector<int> lst_a(n + 1);
    std::vector<int> lst_b(n + 1);
 
    for (int i = 0; i <= n; ++i) {
        std::cin >> lst_a[i];
    }
 
    for (int i = 0; i <= n; ++i) {
        std::cin >> lst_b[i];
    }
 
    mul(lst_a, lst_b);
 
    while (!result.back()) {
        result.pop_back();
    }
 
    for (auto element: result) {
        std::cout << element << " ";
    }
 
    return 0;
}