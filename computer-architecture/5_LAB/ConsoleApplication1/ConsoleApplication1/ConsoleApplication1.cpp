﻿#include <iostream>
#include <ctime>
#include <omp.h>
#include <sstream>
#include <cstdio>

using namespace std;

int main(int argc, char* argv[]) {

    //int argc = 4;
    //char* argv[] = {"hw5.exe", "3", "input.txt", "output.txt"};

    if (argc <= 2) {
        printf("Usage: <program name> <number of threads> <input file> [<output file>].\n");
        return 0;
    }

    stringstream convert(argv[1]);
    int threads;
    if (!(convert >> threads)) {
        printf("Error: can not input number of threads.\n");
        return 0;
    }

    int maxThreads = omp_get_max_threads();
    if (threads < 0 or maxThreads < threads) {
        printf("Incorrect value number of threads: should be from 0 to %i.\n", maxThreads);
        return 0;
    }
    if (threads && threads < maxThreads) {
        omp_set_num_threads(threads); // change "OMP_NUM_THREADS"
    }
    else {
        threads = maxThreads;
    }

    FILE* file;
    file = fopen(argv[2], "r");
    if (file == NULL) {
        printf("Can not open input file.\n");
        return 0;
    }
    fclose(file);
    freopen(argv[2], "r", stdin);

    bool writing = false;
    if (argc == 4) {
        file = fopen(argv[3], "w");
        if (file == NULL) {
            printf("Can not open output file.\n");
            return 0;
        }
        writing = true;
    }

    int n;
    clock_t time;
    double det;

    cin >> n;
    auto** ma = new double* [n];
    for (int i = 0; i < n; ++i) {
        ma[i] = new double[n];
        for (int j = 0; j < n; ++j) {
            cin >> ma[i][j];
        }
    }

    time = clock();

    det = 1;

    for (int i = 0; i < n; ++i) {
        if (ma[i][i] == 0) {
            int f = i;
            for (int j = i + 1; j < n; ++j) {
                if (ma[j][i] != 0) {
                    f = j;
                    break;
                }
            }
            if (f == i) {
                det = 0;
                break;
            }
            else {
                det = -det;
                swap(ma[i], ma[f]);
            }
        }
        det *= ma[i][i];
        //#pragma omp parallel for schedule(static)
#pragma omp parallel for schedule(dynamic)
//#pragma omp parallel for schedule(guided)
        for (int j = i + 1; j < n; ++j) {
            if (ma[j][i] == 0) continue;
            double zn = -ma[j][i] / ma[i][i];
            ma[j][i] = 0;
            for (int k = i + 1; k < n; ++k) {
                ma[j][k] += ma[i][k] * zn;
            }
        }
        delete[] ma[i];
    }

    time = clock() - time;

    delete[] ma;
    if (writing) {
        fprintf(file, "Determinant: %g\n", det);
        fclose(file);
    }

    printf("Determinant: %g\n\nTime (%i thread(s)): %f sec\n", det, threads, static_cast<double>(time) / CLOCKS_PER_SEC);
    return 0;
}
