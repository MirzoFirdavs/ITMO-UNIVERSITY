#include "pi.h"
#include "math.h"
#include "random_gen.h"

double calculate_pi(unsigned long runs) {
    long double r = 1;
    if (runs == 0) {
        return 0;
    } else {
        long double all_cnt = 0;
        long double circle_cnt = 0;
        for (unsigned long i = 0; i < runs; ++i) {
            long double x = get_random_number();
            long double y = get_random_number();
            if (pow(x, 2) + pow(y, 2) <= pow(r, 2)) {
                circle_cnt++;
            }
            all_cnt++;
        }
        long double pi_ans = (circle_cnt / all_cnt) * 4;
        return pi_ans;
    }
}
