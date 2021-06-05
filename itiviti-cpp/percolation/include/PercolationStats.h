#pragma once

#include "Percolation.h"

#include <utility>
#include <vector>

struct PercolationStats
{

public:
    /**
     * Construct a new Percolation Stats object
     * @param dimension dimension of percolation grid
     * @param trials amount of experiments
     */
    PercolationStats(size_t dimension, size_t trials);

    /**
     * Returns mean of percolation threshold (x¯ from description)
     */
    double get_mean() const;

    /**
     * Returns standard deviation of percolation threshold (s from description)
     */
    double get_standard_deviation() const;

    /**
     * Returns log edge of confidence interval
     */
    double get_confidence_low() const;

    /**
     * Returns high edge of confidence interval
     */
    double get_confidence_high() const;

    /**
     * Makes all experiments, calculates statistic values
     */
    void execute();

    double gen_random(Percolation & percolation) const;

private:
    size_t n;

    size_t trials;

    std::vector<double> vector_of_trials;
};