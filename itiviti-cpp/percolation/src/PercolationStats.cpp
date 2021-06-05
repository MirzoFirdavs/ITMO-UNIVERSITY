#include "PercolationStats.h"

#include <math.h>
#include <random>

PercolationStats::PercolationStats(const size_t dimension, const size_t trials)
    : n(dimension)
    , trials(trials)
{
    vector_of_trials.reserve(trials);
    execute();
}

double PercolationStats::get_mean() const
{
    return std::accumulate(vector_of_trials.begin(), vector_of_trials.end(), 0.0) / trials;
}

double PercolationStats::get_standard_deviation() const
{
    return std::sqrt(std::accumulate(vector_of_trials.begin(), vector_of_trials.end(), 0.0, [&](double a, double b) { return a + pow(b - get_mean(), 2); }) / (trials - 1));
}

double PercolationStats::get_confidence_low() const
{
    return get_mean() - 1.96 * get_standard_deviation() / sqrt(trials);
}

double PercolationStats::get_confidence_high() const
{
    return get_mean() + 1.96 * get_standard_deviation() / sqrt(trials);
}

void PercolationStats::execute()
{
    double current;
    for (size_t i = 0; i < trials; ++i) {
        Percolation percolation(n);
        current = gen_random(percolation);
        vector_of_trials.push_back(current);
    }
}

double PercolationStats::gen_random(Percolation & percolation) const
{
    std::random_device rd;
    std::mt19937 eng(rd());
    std::uniform_int_distribution<> r_nd(0, n - 1);

    while (!percolation.has_percolation()) {
        const size_t r_w = r_nd(eng);
        const size_t c_l_mn = r_nd(eng);
        percolation.open(r_w, c_l_mn);
    }

    const size_t counter = percolation.get_numbet_of_open_cells();

    return static_cast<double>(counter) / (pow(n, 2));
}