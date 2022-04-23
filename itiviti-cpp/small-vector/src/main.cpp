#include "small_vector.h"

#include <algorithm>
#include <iostream>
#include <random>
#include <string>

namespace {

struct X
{
    X()
    {
        std::cout << "Hello\n";
    }
    X(const X &)
    {
        std::cout << "Copy!\n";
    }
    ~X()
    {
        std::cout << "Bye!\n";
    }
};

X f()
{
    X x;
    return x;
}

} // anonymous namespace

int main()
{
    [[maybe_unused]] auto x = f();
    SmallVector<std::string> lines;
    for (std::string line; std::getline(std::cin, line); ) {
        lines.push_back(line);
    }

    /*std::shuffle(lines.begin(), lines.end(), std::mt19937_64{std::random_device{}()});
    for (const auto & line : lines) {
        std::cout << line << "\n";
    }
    */
}
