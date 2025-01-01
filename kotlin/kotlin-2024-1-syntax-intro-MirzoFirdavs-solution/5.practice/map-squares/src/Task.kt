import kotlin.math.pow

fun mapSquares(values: IntArray): IntArray {
    return IntArray(values.size) { values[it].toDouble().pow(2.0).toInt() }
}
