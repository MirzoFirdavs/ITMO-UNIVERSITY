import math

def distance(a):
    dist = 0
    for i in range(len(a)):
        dist += a[i] ** 2
    return math.sqrt(dist)

def scalar_product(a, b):
    s = 0

    for i in range(len(a)):
        s += a[i] * b[i]

    return s

def find_angle(a, b):
    temp = scalar_product(a, b) / (distance(a) * distance(b))
    temp = (math.acos(temp) * 180) / math.pi
    return temp

def vector_product(a, b):
    res = [0] * 3
    res[0] = a[1] * b[2] - a[2] * b[1]
    res[1] = a[2] * b[0] - a[0] * b[2]
    res[2] = a[0] * b[1] - a[1] * b[0]
    return res

def enemy_vector(a, b):
    res = [0 for i in range(3)]

    for i in range(len(a)):
        res[i] = a[i] - b[i]

    return res

with open('input.txt') as inf:
    v = list(map(float, inf.readline().split()))
    v.append(0)
    a = list(map(float, inf.readline().split()))
    a.append(0)
    m = list(map(float, inf.readline().split()))
    m.append(1)
    w = list(map(float, inf.readline().split()))
    w.append(0)

enemy = enemy_vector(w, v)

base = [0, 0, 1]

right_weapon = vector_product(a, base)
left_weapon = vector_product(base, a)
angle = find_angle(base, m)

right_angle = find_angle(right_weapon, enemy)
left_angle = find_angle(left_weapon, enemy)

with open('output.txt', 'w') as ouf:
    if left_angle > 60 and right_angle > 60 or angle > 60:
        ouf.write(str(0))
        ouf.write("\n")
        ouf.write("meow")
        exit()

    temp = vector_product(a, base)
    
    if left_angle <= 60:
        ouf.write(str(1))
        ouf.write("\n")

        if find_angle(a, enemy) > 90:
            left_angle *= -1
        if find_angle(m, temp) > 90:
            angle *= -1

        ouf.write(str(left_angle))
        ouf.write("\n")

    else:
        ouf.write(str(-1))
        ouf.write("\n")

        if find_angle(a, enemy) > 90:
            right_angle *= -1
        if find_angle(m, temp) < 90:
            angle *= -1

        ouf.write(str(right_angle))
        ouf.write("\n")

    ouf.write(str(angle))
    ouf.write("\n")
    ouf.write("meow")
