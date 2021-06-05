#include "Percolation.h"

#include "Node.h"

Percolation::Percolation(const size_t dimension)
    : n(dimension)
    , n_sites(dimension * dimension)
    , gen(n_sites)
    , open_sites(n_sites, CLOSE)
{
}

void Percolation::generation(const size_t row, const size_t column)
{
    if (row > 0 && is_open(row - 1, column)) {
        gen.get_merge(n * row + column, n * (row - 1) + column);
    }
    if (column > 0 && is_open(row, column - 1)) {
        gen.get_merge(n * row + column, n * row + column - 1);
    }
    if (column < n - 1 && is_open(row, column + 1)) {
        gen.get_merge(n * row + column, n * row + column + 1);
    }
    if (row < n - 1 && is_open(row + 1, column)) {
        gen.get_merge(n * row + column, n * (row + 1) + column);
    }
    counter++;
}

void Percolation::open(const size_t row, const size_t column)
{
    if (!is_open(row, column)) {
        open_sites[n * row + column] = OPEN;
        generation(row, column);
    }
}

bool Percolation::is_open(const size_t row, const size_t column) const
{
    return open_sites[n * row + column] != CLOSE;
}

bool Percolation::is_full(const size_t row, const size_t column) const
{
    if (is_open(row, column)) {
        for (size_t i = 0; i < n; ++i) {
            if (gen.is_connect(n * row + column, i)) {
                return true;
            }
        }
    }
    return false;
}

size_t Percolation::get_numbet_of_open_cells() const
{
    return counter;
}

bool Percolation::has_percolation() const
{
    for (size_t i = 0; i < n; ++i) {
        if (is_full(n - 1, i)) {
            return true;
        }
    }
    return false;
}