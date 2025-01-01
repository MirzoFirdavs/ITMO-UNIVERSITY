import kotlin.math.sqrt

fun isPrime(n: Int): Boolean {
    if (n <= 1) {
        return false
    }
    if (n == 2) {
        return true
    }
    if (n % 2 == 0) {
        return false
    }

    val sqrtN = sqrt(n.toDouble()).toInt()

    for (i in 3..sqrtN step 2) {
        if (n % i == 0) {
            return false
        }
    }

    return true
}

fun piFunction(x: Double): Int {
    var count = 0

    for (i in 2..x.toInt()) {
        if (isPrime(i)) {
            count++
        }
    }

    return count
}
