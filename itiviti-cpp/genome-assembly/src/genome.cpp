#include "genome.h"

#include <map>
#include <set>

namespace genome {

std::vector<std::pair<std::string_view, std::string_view>> find_euler_path(std::map<std::string_view, std::multiset<std::string_view>> graph)
{
    std::string_view start;
    std::vector<std::string_view> paths;
    std::vector<std::pair<std::string_view, std::string_view>> result;

    std::map<std::string_view, int> entering;
    for (const auto & iter : graph) {
        for (const auto j : iter.second) {
            entering[j]++;
        }
    }

    for (const auto & v : graph) {
        if (v.second.size() - entering[v.first] == 1) {
            start = v.first;
            break;
        }
    }

    std::vector<std::string_view> v_ctor{start};

    while (!v_ctor.empty()) {
        std::multiset<std::string_view> & set = graph[v_ctor.back()];
        if (set.empty()) {
            paths.push_back(v_ctor.back());
            v_ctor.pop_back();
        }
        else {
            v_ctor.push_back(*set.begin());
            set.erase(set.begin());
        }
    }

    for (size_t i = paths.size() - 1; i >= 1; --i) {
        result.emplace_back(paths[i], paths[i - 1]);
    }

    return result;
}

std::string assembly(const size_t k, const std::vector<std::string> & reads)
{
    if (k == 0 || reads.empty()) {
        return "";
    }
    std::map<std::string_view, std::multiset<std::string_view>> graph;
    for (size_t i = 0; i < reads.size(); ++i) {
        for (size_t j = 0; j + k < reads[i].size(); j++) {
            std::string_view genome = std::string_view(reads[i]).substr(j, k + 1);
            graph[genome.substr(0, k)].insert(genome.substr(1, k));
        }
    }

    std::vector<std::pair<std::string_view, std::string_view>> euler_path = find_euler_path(std::move(graph));
    std::string genome_assembly = std::string(euler_path.begin()->first);
    for (size_t i = 0; i < euler_path.size(); ++i) {
        genome_assembly += euler_path[i].second.back();
    }
    return genome_assembly;
}
} // namespace genome