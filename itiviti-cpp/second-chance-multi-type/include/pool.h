#pragma once

#include <algorithm>
#include <cstddef>
#include <unordered_map>
#include <vector>
class PoolAllocator
{
public:
    PoolAllocator(size_t count, std::initializer_list<size_t> sizes);

    void * allocate(size_t n);

    void deallocate(const void * ptr);

private:
    size_t idx_for_deallocate(const void * ptr) const;
    std::vector<std::vector<std::byte *>> empty_blocks;
    std::vector<std::byte *> blocks;
    std::unordered_map<size_t, size_t> idx_of_sizes;
    std::vector<std::byte> pool;
};