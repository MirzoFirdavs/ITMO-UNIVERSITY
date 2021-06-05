#include "pool.h"

PoolAllocator::PoolAllocator(const size_t count, std::initializer_list<size_t> sizes)
    : empty_blocks(sizes.size())
    , blocks(sizes.size())
    , pool(count * sizes.size())
{
    size_t iter = 0;
    std::byte * cur_ptr = &pool[0];
    for (const size_t blocks_sizes : sizes) {
        idx_of_sizes.insert({blocks_sizes, iter});
        blocks[iter] = cur_ptr;
        std::byte * cur_block_ptr = cur_ptr;
        for (size_t i = 0; i < count / blocks_sizes; ++i) {
            empty_blocks[iter].push_back(cur_block_ptr);
            cur_block_ptr += blocks_sizes;
        }
        cur_ptr += count;
        ++iter;
    }
}

size_t PoolAllocator::idx_for_deallocate(const void * ptr) const
{
    size_t last = 0;
    while (last < idx_of_sizes.size() && ptr >= blocks[last]) {
        ++last;
    }
    return last ? last - 1 : 0;
}

void PoolAllocator::deallocate(const void * ptr)
{
    int idx = idx_for_deallocate(ptr);
    empty_blocks[idx].push_back(static_cast<std::byte *>(const_cast<void *>(ptr)));
}

void * PoolAllocator::allocate(const size_t n)
{
    int index = idx_of_sizes[n];
    if (!empty_blocks[index].empty()) {
        void * ptr = empty_blocks[index].back();
        empty_blocks[index].pop_back();
        return ptr;
    }
    throw std::bad_alloc();
}