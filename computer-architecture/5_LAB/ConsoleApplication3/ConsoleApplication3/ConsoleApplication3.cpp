#include <string>
#include <fstream>
#include <omp.h>
#include <vector>
#include <chrono>
#include <iostream>

int i = 0, j = 0, k = 0;
int n;
int t;
std::vector < std::vector <float> > a;
std::vector < std::vector <float> > b;
std::ofstream* out = new std::ofstream;

void solve() {

    auto start = std::chrono::steady_clock::now();

    int c = 0;
    while (b[c][0] == 0 and c < n) {
        c++;
    }
    swap(b[0], b[c]);
    float dif = 1;
    for (i = 0; i < n; ++i) {
        dif *= b[i][i];
        if (b[i][i] != 0) {
            for (j = i + 1; j < n; ++j) {
                float d = -b[j][i] / b[i][i];

                for (k = i; k < n; ++k) {
                    b[j][k] += b[i][k] * d;
                }
            }
        }
    }
    *out << "Determinant: " << dif << '\n';

    auto finish = std::chrono::steady_clock::now();
    auto elapsed_ms = std::chrono::duration_cast<std::chrono::milliseconds>(finish - start);
    std::cerr << std::endl << "Time for solution: " << elapsed_ms.count() << " ms\n";

}

void solveparallel() {
    auto start = std::chrono::steady_clock::now();
    int c = 0;
    while (a[c][0] == 0 and c < n) {
        c++;
    }
    swap(a[0], a[c]);
    float dif = 1;
    for (i = 0; i < n; ++i) {
        dif *= a[i][i];
        if (a[i][i] != 0) {
#pragma omp parallel for private(j, k, d) shared(i) num_threads(t);
            for (j = i + 1; j < n; ++j) {
                float d = -a[j][i] / a[i][i];

                for (k = i; k < n; ++k) {
                    a[j][k] += a[i][k] * d;
                }
            }
        }
    }
    *out << "Determinant: " << dif << '\n';

    auto finish = std::chrono::steady_clock::now();
    auto elapsed_ms = std::chrono::duration_cast<std::chrono::milliseconds>(finish - start);
    std::cerr << std::endl << "Time for parallel solution: " << elapsed_ms.count() << " ms\n";

}

int main(int argc, char* argv[]) {
    auto start = std::chrono::steady_clock::now();
    if (argc < 3) {
        printf("following input is wrong!\n");
        for (int i = 1; i < argc; ++i) {
            printf("%s ", argv[i]);
        }
        system("pause");
        return 0;
    }
    try {
        t = std::stoi(argv[1]);
    }
    catch (...) {
        printf("%s shoud be correct number\n", argv[1]);
        system("pause");
        return 0;
    }
    std::ifstream in(argv[2]);
    if (!in) {
        printf("There is no file by name %s\n", argv[2]);
        system("pause");
        return 0;
    }
    in >> n;
    a = std::vector < std::vector <float> >(n, std::vector<float>(n));
    b = std::vector < std::vector <float> >(n, std::vector<float>(n));
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            in >> a[i][j];
            b[i][j] = a[i][j];
        }
    }
    in.close();

    if (argc > 3) {
        out = new std::ofstream(argv[3]);
    }
   
    solve();
    solveparallel();

    out->close();
    auto finish = std::chrono::steady_clock::now();
    auto elapsed_ms = std::chrono::duration_cast<std::chrono::milliseconds>(finish - start);
    std::cerr << std::endl << "Total time: " << elapsed_ms.count() << " ms\n";
    system("pause");
    return 0;
}