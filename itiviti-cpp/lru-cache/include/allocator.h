#pragma once

#include "pool.h"

#include <memory>

template <class T>
class AllocatorWithPool
{
    using Alloc = PoolAllocator<T>;
public:
    AllocatorWithPool(const std::size_t max_capacity)
        : m_pool(Alloc::create_pool(max_capacity), Alloc::destroy_pool)
        , m_alloc(*m_pool)
    {}

    template <class... Args>
    T * create(Args &&... args)
    {
        return new (m_alloc.allocate(1)) T(std::forward<Args>(args)...);
    }

    void destroy(T * ptr)
    {
        ptr->~T();
        m_alloc.deallocate(ptr, 1);
    }
private:
    std::unique_ptr<pool::Pool, decltype(&Alloc::destroy_pool)> m_pool;
    Alloc m_alloc;
};
