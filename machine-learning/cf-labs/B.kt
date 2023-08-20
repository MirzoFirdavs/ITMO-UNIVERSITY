package b

import java.io.*
import java.util.*
import kotlin.math.pow
import kotlin.math.tanh

private val scanner = InputReader(System.`in`)
private val writer = PrintWriter(System.out)

fun main() {
    val n: Int = scanner.nextInt()
    val m: Int = scanner.nextInt()
    val k: Int = scanner.nextInt()
    val nodes: MutableList<AbstractNode?> = ArrayList()

    (0 until n).forEach { _ ->
        val type = scanner.next()
        var node: AbstractNode? = null
        when (type) {
            "var" -> {
                val r = scanner.nextInt()
                val c = scanner.nextInt()
                node = VarNode(r, c)
            }
            "tnh" -> {
                val xt = scanner.nextInt() - 1
                node = TnhNode(xt)
            }
            "rlu" -> {
                val alpha = scanner.nextInt()
                val xr = scanner.nextInt() - 1
                node = RluNode(alpha, xr)
            }
            "mul" -> {
                val a = scanner.nextInt() - 1
                val b = scanner.nextInt() - 1
                node = MulNode(a, b)
            }
            "sum" -> {
                val lens = scanner.nextInt()
                val us: MutableList<Int> = ArrayList()
                (0 until lens).forEach { _ ->
                    us.add(scanner.nextInt() - 1)
                }
                node = SumNode(us)
            }

            "had" -> {
                val lenh = scanner.nextInt()
                val uh: MutableList<Int> = ArrayList()
                (0 until lenh).forEach { _ ->
                    uh.add(scanner.nextInt() - 1)
                }
                node = HadNode(uh)
            }
        }
        nodes.add(node)
    }
    (0 until m).forEach { i ->
        (nodes[i] as VarNode?)!!.readData(scanner)
        nodes[i]!!.forwardPass()
    }
    (m until n).forEach { i ->
        nodes[i]!!.readData(nodes)
        nodes[i]!!.forwardPass()
    }
    (n - k until n).forEach { i -> nodes[i]!!.readDerivative(scanner) }
    (n - 1 downTo -1 + 1).forEach { i ->
        nodes[i]!!.backwardsPass()
        nodes[i]!!.giveBackwardsData(nodes)
    }
    (n - k until n).forEach { i -> printMatrix(nodes[i]!!.outputData) }
    (0 until m).forEach { i -> printMatrix(nodes[i]!!.derivative) }
    writer.flush()
    writer.close()
}

private fun printMatrix(matrix: Array<DoubleArray>) {
    matrix.forEach { line ->
        line.forEach { elem -> writer.print("$elem ") }
        writer.println()
    }
}


internal abstract class AbstractNode protected constructor() {
    lateinit var outputData: Array<DoubleArray>
    lateinit var derivative: Array<DoubleArray>
    abstract fun forwardPass()
    abstract fun readData(nodes: List<AbstractNode?>?)
    abstract fun backwardsPass()
    abstract fun giveBackwardsData(nodes: List<AbstractNode?>?)

    fun readDerivative(scanner: InputReader) {
        outputData.indices.forEach { i -> outputData[0].indices.forEach { j -> derivative[i][j] = scanner.nextInt().toDouble() } }
    }

    override fun toString(): String {
        return "AbstractNode{" +
                "outputData=" + outputData.contentDeepToString() +
                ", derivative=" + derivative.contentDeepToString() +
                '}'
    }

    protected fun addingMatrix(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
        val res = Array(a.size) { DoubleArray(a[0].size) }
        a.indices.forEach { i -> a[i].indices.forEach { j -> res[i][j] = a[i][j] + b[i][j] } }
        return res
    }

    protected fun copy(src: Array<DoubleArray>?): Array<DoubleArray> {
        return when (src) {
            null -> { emptyArray() }
            else -> {
                val copy = arrayOfNulls<DoubleArray?>(src.size)
                src.indices.forEach { i -> copy[i] = src[i].clone() }
                copy.filterNotNull().toTypedArray()
            }
        }
    }
}


internal abstract class UnaryFunctionNode(private val argIndex: Int) : AbstractNode() {
    protected lateinit var arg: Array<DoubleArray>
    protected lateinit var backwardsData: Array<DoubleArray>

    override fun readData(nodes: List<AbstractNode?>?) {
        arg = nodes!![argIndex]!!.outputData
        outputData = Array(arg.size) { DoubleArray(arg[0].size) }
        backwardsData = Array(arg.size) { DoubleArray(arg[0].size) }
        derivative = Array(outputData.size) { DoubleArray(outputData[0].size) }
    }

    override fun giveBackwardsData(nodes: List<AbstractNode?>?) {
        check(!(nodes!![argIndex]!!.derivative.size != backwardsData.size || nodes[argIndex]!!.derivative[0].size != backwardsData[0].size))
        nodes[argIndex]!!.derivative = addingMatrix(backwardsData, nodes[argIndex]!!.derivative)
    }
}


internal abstract class BinaryFunctionNode(private val leftArgIndex: Int, private val rightArgIndex: Int) : AbstractNode() {
    protected lateinit var leftArg: Array<DoubleArray>
    protected lateinit var rightArg: Array<DoubleArray>
    protected lateinit var leftBackwardsData: Array<DoubleArray>
    protected lateinit var rightBackwardsData: Array<DoubleArray>

    override fun readData(nodes: List<AbstractNode?>?) {
        leftArg = nodes!![leftArgIndex]!!.outputData
        rightArg = nodes[rightArgIndex]!!.outputData
        outputData = Array(leftArg.size) { DoubleArray(rightArg[0].size) }
        derivative = Array(outputData.size) { DoubleArray(outputData[0].size) }
    }

    override fun giveBackwardsData(nodes: List<AbstractNode?>?) {
        check(!(nodes!![leftArgIndex]!!.derivative.size != leftBackwardsData.size || nodes[leftArgIndex]!!.derivative[0].size != leftBackwardsData[0].size))
        check(!(nodes[rightArgIndex]!!.derivative.size != rightBackwardsData.size || nodes[rightArgIndex]!!.derivative[0].size != rightBackwardsData[0].size))
        nodes[leftArgIndex]!!.derivative = addingMatrix(leftBackwardsData, nodes[leftArgIndex]!!.derivative)
        nodes[rightArgIndex]!!.derivative = addingMatrix(rightBackwardsData, nodes[rightArgIndex]!!.derivative)
    }
}


internal abstract class VariadicFunctionsNode(private val argsIndices: List<Int>) : AbstractNode() {
    protected var args: MutableList<Array<DoubleArray>> = ArrayList()
    protected var backwardsData: MutableList<Array<DoubleArray>> = ArrayList()

    override fun readData(nodes: List<AbstractNode?>?) {
        argsIndices.forEach { id -> args.add(nodes!![id]!!.outputData) }
        outputData = Array(args[0].size) { DoubleArray(args[0][0].size) }
        derivative = Array(outputData.size) { DoubleArray(outputData[0].size) }
    }

    override fun giveBackwardsData(nodes: List<AbstractNode?>?) {
        argsIndices.withIndex().forEach { (i, id) ->
            check(!(nodes!![id]!!.derivative.size != backwardsData[i].size || nodes[id]!!.derivative[0].size != backwardsData[i][0].size))
            nodes[id]!!.derivative = addingMatrix(backwardsData[i], nodes[id]!!.derivative)
        }
    }
}


internal open class VarNode(private val r: Int, private val c: Int) : AbstractNode() {
    private var arg: Array<DoubleArray> = Array(r) { DoubleArray(c) }

    override fun forwardPass() {
        outputData = copy(arg)
        derivative = Array(outputData.size) { DoubleArray(outputData[0].size) }
    }

    override fun readData(nodes: List<AbstractNode?>?) {}
    override fun backwardsPass() {}
    override fun giveBackwardsData(nodes: List<AbstractNode?>?) {}

    fun readData(scanner: InputReader) {
        (0 until r).forEach { i -> (0 until c).forEach { j -> arg[i][j] = scanner.nextInt().toDouble() } }
    }
}


internal class TnhNode(x: Int) : UnaryFunctionNode(x) {
    override fun forwardPass() {
        arg.indices.forEach { i -> arg[i].indices.forEach { j -> outputData[i][j] = tanh(arg[i][j]) } }
    }

    override fun backwardsPass() {
        arg.indices.forEach { i -> arg[i].indices.forEach { j -> backwardsData[i][j] = derivative[i][j] * (1 - tanh(arg[i][j]).pow(2.0)) } }
    }
}


internal class RluNode(private val alpha: Int, x: Int) : UnaryFunctionNode(x) {
    override fun forwardPass() {
        val coefficient = 1.0 / alpha
        arg.indices.forEach { i -> arg[i].indices.forEach { j -> outputData[i][j] = if (arg[i][j] < 0) coefficient * arg[i][j] else arg[i][j] } }
    }

    override fun backwardsPass() {
        val coefficient = 1.0 / alpha
        arg.indices.forEach { i ->
            arg[i].indices.forEach { j ->
                when {
                    arg[i][j] < 0 -> { backwardsData[i][j] = derivative[i][j] * coefficient }
                    else -> { backwardsData[i][j] = derivative[i][j] * 1 }
                }
            }
        }
    }
}


internal class MulNode(a: Int, b: Int) : BinaryFunctionNode(a, b) {
    override fun forwardPass() {
        outputData = multiplyMatrix(leftArg, rightArg)
    }

    override fun backwardsPass() {
        leftBackwardsData = multiplyMatrix(derivative, transposeMatrix(rightArg))
        rightBackwardsData = multiplyMatrix(transposeMatrix(leftArg), derivative)
    }

    private fun multiplyMatrix(a: Array<DoubleArray>, b: Array<DoubleArray>): Array<DoubleArray> {
        val res = Array(a.size) { DoubleArray(b[0].size) }
        a.indices.forEach { i -> b[0].indices.forEach { j -> b.indices.forEach { k -> res[i][j] += a[i][k] * b[k][j] } } }
        return res
    }

    private fun transposeMatrix(a: Array<DoubleArray>): Array<DoubleArray> {
        val res = Array(a[0].size) { DoubleArray(a.size) }
        a.indices.forEach { i -> a[i].indices.forEach { j -> res[j][i] = a[i][j] } }
        return res
    }
}


internal class SumNode(u: List<Int>) : VariadicFunctionsNode(u) {
    override fun forwardPass() {
        args.forEach { arg -> arg.indices.forEach { i -> arg[i].indices.forEach { j -> outputData[i][j] += arg[i][j] } } }
    }

    override fun backwardsPass() {
        args.indices.forEach { _ -> backwardsData.add(copy(derivative)) }
    }
}


internal class HadNode(u: List<Int>) : VariadicFunctionsNode(u) {
    override fun forwardPass() {
        outputData = hadamardProduct(args, -1)
    }

    override fun backwardsPass() {
        args.indices.forEach { k ->
            val temp = hadamardProduct(args, k)
            temp.indices.forEach { i -> temp[i].indices.forEach { j -> temp[i][j] *= derivative[i][j] } }
            backwardsData.add(temp)
        }
    }

    private fun hadamardProduct(args: List<Array<DoubleArray>>, ignoreIndex: Int): Array<DoubleArray> {
        var res = Array(args[0].size) { DoubleArray(args[0][0].size) }

        when {
            args.size == 2 && (ignoreIndex == 0 || ignoreIndex == 1) -> {
                res = copy(args[when (ignoreIndex) {0 -> 1 else -> 0 }])
                return res
            } else -> {
            res.forEach { row -> Arrays.fill(row, 1.0) }
            var id = 0

            args.forEach { arg ->
                when (id) {
                    ignoreIndex -> {
                        id++
                        return@forEach
                    } else -> {
                    arg.indices.forEach { i -> arg[i].indices.forEach { j -> res[i][j] *= arg[i][j] } }
                    id++
                }
                }
            }

            return res
        }
        }
    }
}

class InputReader(stream: InputStream?) {
    private val reader: BufferedReader
    private var tokenizer: StringTokenizer?

    init {
        reader = BufferedReader(InputStreamReader(stream!!), 32768)
        tokenizer = null
    }

    operator fun next(): String {
        while (tokenizer == null || !tokenizer!!.hasMoreTokens()) {
            tokenizer = try {
                StringTokenizer(reader.readLine())
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
        return tokenizer!!.nextToken()
    }

    @Suppress("unused")
    fun nextInt(): Int {
        return next().toInt()
    }

    @Suppress("unused")
    fun nextLong(): Long {
        return next().toLong()
    }

    @Suppress("unused")
    fun nextDouble(): Double {
        return next().toDouble()
    }
}