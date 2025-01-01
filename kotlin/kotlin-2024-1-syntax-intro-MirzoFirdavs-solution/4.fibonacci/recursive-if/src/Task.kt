fun fibonacciIf(n: Int): Int {
    if (n == 0) return 0
    if (n == 1) return 1
    return fibonacciIf(n - 1) + fibonacciIf(n - 2)
}
