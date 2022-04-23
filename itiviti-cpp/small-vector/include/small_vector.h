#pragma once

#include <array>
#include <variant>

template <class T>
class SmallVector
{
    static constexpr std::size_t N = 10;
    std::size_t m_size = 0;
    std::variant<std::array<T, N>, T *> m_data;

public:
    SmallVector() = default;
    SmallVector(const SmallVector & other)
        : m_size(other.m_size)
        , m_data(other.m_data)
    {
        if (m_data.index() != 0) {
            T * tmp = new T[m_size];
            T * current = std::get<1>(m_data);
            std::copy(current, current + m_size, tmp);
            m_data = tmp;
        }
    }
    SmallVector(SmallVector && other)
        : m_size(std::exchange(other.m_size, 0))
        , m_data(std::exchange(other.m_data, nullptr))
    {
    }

    SmallVector & operator = (SmallVector other)
    {
        std::swap(m_data, other.m_data);
        std::swap(m_size, other.m_size);
        return *this;
    }

    ~SmallVector()
    {
        if (m_data.index() != 0) {
            delete [] std::get<1>(m_data);
        }
    }

    void push_back(const T & x)
    {
        if (m_data.index() == 0) {
            if (m_size < N) {
                std::get<0>(m_data)[m_size] = x;
                ++m_size;
            }
            else {
                auto & current = std::get<0>(m_data);
                T * tmp = new T[m_size + 1];
                std::move(current.begin(), current.end(), tmp);
                tmp[m_size] = x;
                m_data = tmp;
                ++m_size;
            }
        }
        else {
            T * current = std::get<1>(m_data);
            T * tmp = new T[m_size + 1];
            std::move(current, current + m_size, tmp);
            tmp[m_size] = x;
            delete [] current;
            m_data = tmp;
            ++m_size;
        }
    }

    void push_back(T && x)
    {
        if (m_data.index() == 0) {
            if (m_size < N) {
                std::get<0>(m_data)[m_size] = std::move(x);
                ++m_size;
            }
            else {
                auto & current = std::get<0>(m_data);
                T * tmp = new T[m_size + 1];
                std::move(current.begin(), current.end(), tmp);
                tmp[m_size] = std::move(x);
                m_data = tmp;
                ++m_size;
            }
        }
        else {
            T * current = std::get<1>(m_data);
            T * tmp = new T[m_size + 1];
            std::move(current, current + m_size, tmp);
            tmp[m_size] = std::move(x);
            delete [] current;
            m_data = tmp;
            ++m_size;
        }
    }

    bool empty() const
    {
        return m_size == 0;
    }

    std::size_t size() const
    {
        return m_size;
    }

    T & operator [] (const std::size_t i)
    {
        if (m_data.index() == 0) {
            return std::get<0>(m_data)[i];
        }
        else {
            return std::get<1>(m_data)[i];
        }
    }

    const T & operator [] (const std::size_t i) const
    {
        if (m_data.index() == 0) {
            return std::get<0>(m_data)[i];
        }
        else {
            return std::get<1>(m_data)[i];
        }
    }

    class const_iterator
    {
        const T * m_ptr = nullptr;

        friend class SmallVector;

        const_iterator(const T * ptr)
            : m_ptr(ptr)
        { }
    public:
        using value_type = const T;
        using pointer = value_type *;
        using reference = value_type &;
        using difference_type = std::ptrdiff_t;
        using iterator_category = std::random_access_iterator_tag;

        reference operator * () const { return *m_ptr; }
        pointer operator -> () const { return m_ptr; }
        reference operator [] (const difference_type i) const { return *(m_ptr + i); }

        const_iterator & operator ++ ()
        {
            ++m_ptr;
            return *this;
        }
        const_iterator operator ++ (int)
        {
            auto tmp = *this;
            operator ++ ();
            return tmp;
        }
        const_iterator & operator -- ()
        {
            --m_ptr;
            return *this;
        }
        const_iterator operator -- (int)
        {
            auto tmp = *this;
            operator -- ();
            return tmp;
        }

        friend const_iterator operator + (const const_iterator lhs, const difference_type rhs)
        {
            return lhs.m_ptr + rhs;
        }
        friend const_iterator operator + (const difference_type lhs, const const_iterator rhs)
        {
            return rhs + lhs;
        }
        friend const_iterator operator - (const const_iterator lhs, const difference_type rhs)
        {
            return lhs.m_ptr - rhs;
        }
        friend difference_type operator - (const const_iterator lhs, const const_iterator rhs)
        {
            return lhs.m_ptr - rhs.m_ptr;
        }

        const_iterator & operator += (const difference_type i)
        {
            m_ptr += i;
            return *this;
        }
        const_iterator & operator -= (const difference_type i)
        {
            m_ptr -= i;
            return *this;
        }

        friend bool operator == (const const_iterator lhs, const const_iterator rhs)
        {
            return lhs.m_ptr == rhs.m_ptr;
        }
        friend bool operator != (const const_iterator lhs, const const_iterator rhs)
        {
            return !(lhs == rhs);
        }
        friend bool operator < (const const_iterator lhs, const const_iterator rhs)
        {
            return lhs.m_ptr < rhs.m_ptr;
        }
        friend bool operator > (const const_iterator lhs, const const_iterator rhs)
        {
            return lhs.m_ptr > rhs.m_ptr;
        }
        friend bool operator <= (const const_iterator lhs, const const_iterator rhs)
        {
            return !(lhs > rhs);
        }
        friend bool operator >= (const const_iterator lhs, const const_iterator rhs)
        {
            return !(lhs < rhs);
        }
    };

    const_iterator begin() const
    {
        if (m_data.index() == 0) {
            return &std::get<0>(m_data)[0];
        }
        else {
            return std::get<1>(m_data);
        }
    }

    const_iterator end() const
    {
        if (m_data.index() == 0) {
            return std::get<0>(m_data).data() + m_size;
        }
        else {
            return std::get<1>(m_data) + m_size;
        }
    }
};
