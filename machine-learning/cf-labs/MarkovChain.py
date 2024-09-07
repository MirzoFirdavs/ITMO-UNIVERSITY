from collections import defaultdict


def get_neighbors(s):
    result = []

    for i in range(0, len(s) - 1):
        result.append([s[i], s[i + 1]])

    return result


def get_markov_chain(strings):
    chain = defaultdict(lambda: defaultdict(int))

    for s in strings:
        pairs = get_neighbors(s)
        for [a, b] in pairs:
            chain[a][b] += 1

    return chain


def score(chain, s):
    score = 1
    pairs = get_neighbors(s)

    for [a, b] in pairs:
        score *= chain[a][b] / sum(chain[a].values())

    return score


n = int(input())

strings = []
indexes_for_strings = {}

for i in range(n):
    s = str(input())
    indexes_for_strings[s] = i + 1
    strings.append(s)

chain = get_markov_chain(strings)
scores = [score(chain, s) for s in strings]

min_score = min(scores)
index = 0

for i in range(n):
    if scores[i] == min_score:
        index = i
        break

print(indexes_for_strings[strings[index]])