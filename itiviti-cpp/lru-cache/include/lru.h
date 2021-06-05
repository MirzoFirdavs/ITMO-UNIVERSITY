#pragma once

#include "allocator.h"

#include <algorithm>
#include <list>
#include <ostream>
#include <type_traits>

template <class Key, class T>
class LRU
{
    static_assert(std::is_constructible_v<T, const Key &>, "T has to be constructible from Key");

    using Alloc = AllocatorWithPool<T>;
public:
    LRU(const std::size_t cache_size)
        : m_size(cache_size)
        , m_alloc(cache_size)
    { }

    std::size_t size() const
    {
        return m_queue.size();
    }

    bool empty() const
    {
        return m_queue.empty();
    }

    T & get(const Key & key);

    void debug_dump(std::ostream & strm) const;

private:
    const std::size_t m_size;
    Alloc m_alloc;
    std::list<T *> m_queue;
};

template <class Key, class T>
inline T & LRU<Key, T>::get(const Key & key)
{
    auto it = std::find_if(m_queue.begin(), m_queue.end(), [&key] (const T * elem) {
                return *elem == key;
            });
    if (it != m_queue.end()) {
        while (it != m_queue.begin()) {
            const auto old = it--;
            std::iter_swap(it, old);
        }
    }
    else {
        if (m_size == m_queue.size()) {
            m_alloc.destroy(m_queue.back());
            m_queue.pop_back();
        }
        m_queue.push_front(m_alloc.create(key));
    }
    return *m_queue.front();
}

template <class Key, class T>
inline void LRU<Key, T>::debug_dump(std::ostream & strm) const
{
    bool first = true;
    for (const auto x : m_queue) {
        if (!first) {
            strm << " ";
        }
        else {
            first = false;
        }
        strm << *x;
    }
    strm << std::endl;
}
