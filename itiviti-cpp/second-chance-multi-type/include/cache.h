#pragma once

#include <algorithm>
#include <cstddef>
#include <deque>
#include <list>
#include <new>
#include <ostream>

template <class Key, class KeyProvider, class Allocator>
class Cache
{
public:
    template <class... AllocArgs>
    Cache(const std::size_t cache_size, AllocArgs &&... alloc_args)
        : m_max_size(cache_size)
        , m_alloc(std::forward<AllocArgs>(alloc_args)...)
    {
    }

    std::size_t size() const
    {
        return m_queue.size();
    }

    bool empty() const
    {
        return m_queue.empty();
    }

    template <class T>
    T & get(const Key & key);

    std::ostream & print(std::ostream & strm) const;

    friend std::ostream & operator<<(std::ostream & strm, const Cache & cache)
    {
        return cache.print(strm);
    }

private:
    const std::size_t m_max_size;
    Allocator m_alloc;
    struct Pair
    {
        bool priority = false;
        KeyProvider * val;
        Pair(KeyProvider * val)
            : val(val)
        {
        }
    };
    std::deque<Pair> m_queue;
};

template <class Key, class KeyProvider, class Allocator>
template <class T>
inline T & Cache<Key, KeyProvider, Allocator>::get(const Key & key)
{
    for (size_t i = 0; i < m_queue.size(); ++i) {
        if (*static_cast<KeyProvider *>(m_queue[i].val) == key) {
            m_queue[i].priority = true;
            return *static_cast<T *>(m_queue[i].val);
        }
    }
    while (m_max_size <= m_queue.size()) {
        Pair cnt = m_queue.front();
        m_queue.pop_front();
        if (cnt.priority == false) {
            m_alloc.template destroy<KeyProvider>(cnt.val);
        }
        else {
            cnt.priority = false;
            m_queue.push_back(cnt);
        }
    }
    m_queue.push_back(Pair(m_alloc.template create<T>(key)));
    return *static_cast<T *>(m_queue.back().val);
}

template <class Key, class KeyProvider, class Allocator>
inline std::ostream & Cache<Key, KeyProvider, Allocator>::print(std::ostream & strm) const
{
    return strm << "empty" << '\n';
}