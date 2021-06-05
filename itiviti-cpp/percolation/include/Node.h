#pragma once

#include <vector>

struct Node
{

public:
    Node(const size_t n)
        : size(n, 1)
    {
        for (size_t i = 0; i < n; ++i) {
            indexes.push_back(i);
        }
    }

    bool is_connect(const size_t p, const size_t q) const
    {
        return root(p) == root(q);
    }

    void get_merge(const size_t p, const size_t q)
    {
        size_t i = root(p);
        size_t j = root(q);

        if (i == j) {
            return;
        }

        if (size[i] < size[j]) {
            indexes[i] = j;
            size[j] += size[i];
        }
        else {
            indexes[j] = i;
            size[i] += size[j];
        }
    }

private:
    std::vector<size_t> indexes;
    std::vector<size_t> size;

    size_t root(size_t i) const
    {
        while (i != indexes[i])
            i = indexes[i];

        return i;
    }
};