class IntMatrix(val rows: Int, val columns: Int) {
    init {
        require(rows > 0) { "The number of rows must be positive" }
        require(columns > 0) { "The number of columns must be positive" }
    }

    private val matrix = IntArray(rows * columns)

    operator fun get(row: Int, column: Int): Int {
        require(row in 0..<rows) { "Row index out of bounds" }
        require(column in 0..<columns) { "Column index out of bounds" }
        return matrix[row * columns + column]
    }

    operator fun set(row: Int, column: Int, value: Int) {
        require(row in 0..<rows) { "Row index out of bounds" }
        require(column in 0..<columns) { "Column index out of bounds" }
        matrix[row * columns + column] = value
    }
}

fun main() {
    val matrix = IntMatrix(3, 4)
    println(matrix.rows)
    println(matrix.columns)
    println(matrix[0, 0])
    matrix[2, 3] = 42
    println(matrix[2, 3])
}
