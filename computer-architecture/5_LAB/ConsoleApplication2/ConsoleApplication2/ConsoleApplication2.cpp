#include <iostream>
#include <omp.h>
#include <ctime>
#include <chrono>
#include <fstream>

using namespace std;

void MatrixDeterminant() {

    double start_time = omp_get_wtime();
    int i = 0, j = 0, k = 0;
    int n;
    double det = 1;
    clock_t time;
    cout << "Enter n: ";
    cin >> n;

    auto** Matrix = new double* [n];
    auto** Matrixcopy = new double* [n];
    for (int i = 0; i < n; ++i) {
        Matrix[i] = new double[n];
        Matrixcopy[i] = new double[n];
        for (int j = 0; j < n; ++j) {
            //Matrix[i][j] = rand() % 10;
            cin >> Matrix[i][j];
            Matrixcopy[i][j] = Matrix[i][j];
        }
    }

    time = clock();

    double temp;
    for (i = 0; i < n; i++)
    {
        for (j = i + 1; j < n; j++)
        {
            if (Matrix[i][i] == 0)
            {
                if (Matrix[i][j] == 0)
                    temp = 0;
            }   
            else temp = Matrix[j][i] / Matrix[i][i];
            for (k = i; k < n; k++)
                Matrix[j][k] = Matrix[j][k] - Matrix[i][k] * temp;
        }
        det *= Matrix[i][i];
    }

    time = clock() - time;
    printf("Determinant: %g\nTime (sec): %g\n\n", det, static_cast<float>(time) / CLOCKS_PER_SEC);
    //cout << "Det of matrix: "<< det << " " << omp_get_wtime() - start_time << '\n';
    
    delete[] Matrix;
    Matrix = Matrixcopy;
    det = 1;
    
    //time = clock();

    temp = 0;

    for (i = 0; i < n; i++) {
    #pragma omp parallel for private(j,k,temp) shared(i)
        for (j = i + 1; j < n; j++)
        {
            if (Matrix[i][i] == 0)
            {
                if (Matrix[i][j] == 0)
                    temp = 0;
            }
            else temp = Matrix[j][i] / Matrix[i][i];

            for (k = i; k < n; k++)
                Matrix[j][k] = Matrix[j][k] - Matrix[i][k] * temp;
        }

    }

    #pragma omp parallel for reduction(*:det)
    for (i = 0; i < n; i++) {
        det *= Matrix[i][i];
    }
    time = clock() - time;

    printf("Determinant: %g\nTime (sec): %g\n\n", det, static_cast<float>(time) / CLOCKS_PER_SEC);

    //cout << "Det of matrix: " << det << " " << omp_get_wtime() - start_time << '\n';
}

int main() {
    MatrixDeterminant(); 

    return 0;
}