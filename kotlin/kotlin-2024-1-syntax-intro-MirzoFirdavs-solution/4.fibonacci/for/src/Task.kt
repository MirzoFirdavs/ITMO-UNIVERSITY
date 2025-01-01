fun fibonacciFor(n: Int): Int {
    var f1 = 0
    var f2 = 1

    if (n == 0) return 0
    if (n == 1) return 1

    for (i in 2..n) {
        val fI = f1 + f2
        f1 = f2
        f2 = fI
    }

    return f2
}
