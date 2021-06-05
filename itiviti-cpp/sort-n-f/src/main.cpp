#include <iostream>
#include <fstream>
#include <algorithm>
#include <vector>

bool ignore_case(const std::string &first, const std::string &second) {
    return std::lexicographical_compare(first.begin(), first.end(), second.begin(),
                                        second.end(),
                                        [](char first_char, char second_char) {
                                            return toupper(first_char) < toupper(second_char);
                                        });
}

size_t count_blanks(const std::string &line) {
    return std::count_if(line.begin(), line.end(),
                         [](unsigned char c) { return std::isblank(c); });
}

bool is_number(const std::string &line) {
    size_t current = 0;

    while (isblank(line[current])) {
        ++current;
    }

    if (current == line.size()) {
        return false;
    }

    ++current;
    for (size_t i = current; i < line.size(); ++i) {
        if (!std::isdigit(line[i])) {
            return false;
        }
    }
    return true;
}

bool numeric_sort(const std::string &first, const std::string &second) {
    if (count_blanks(first) == first.size()) {
        return !is_number(second) || std::stoi(second) >= 0;
    } else if (is_number(first) && is_number(second)) {
        return std::stoi(first) < std::stod(second);
    } else {
        return first < second;
    }
}

std::vector<std::string> stream_read_all(std::istream &input) {
    std::vector<std::string> result;
    std::string line;
    while (std::getline(input, line)) {
        result.push_back(line);
    }
    return result;
}

int main(int argc, char **argv) {
    bool input_format = true;
    bool ignoreCase = false;
    bool numericSort = false;
    std::vector<std::string> result;
    std::string input;

    for (int iter = 0; iter < argc - 1; ++iter) {
        if (argv[iter][0] == '-' && iter != argc - 2) {
            input_format = false;
        }
        if (argv[iter] == std::string("-f") || argv[iter] == std::string("--ignore-case")) {
            ignoreCase = true;
        } else if (argv[iter] == std::string("-nf") || argv[iter] == std::string("-n") ||
                   argv[iter] == std::string("--numeric-sort")) {
            numericSort = true;
        }
    }
    if (input_format) {
        std::ifstream f(argv[argc - 1]);
        result = stream_read_all(f);
    } else {
        result = stream_read_all(std::cin);
    }

    if (ignoreCase && numericSort) {
        std::sort(result.begin(), result.end(), numeric_sort);
    } else if (numericSort) {
        std::sort(result.begin(), result.end(), numeric_sort);
    } else if (ignoreCase) {
        std::sort(result.begin(), result.end(), ignore_case);
    } else {
        std::sort(result.begin(), result.end());
    }

    for (auto &element : result) {
        std::cout << element << std::endl;
    }
    return 0;
}