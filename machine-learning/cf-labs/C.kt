package c

import java.io.*
import java.util.*
import kotlin.math.max

private val scanner = InputReader(System.`in`)
private val writer = PrintWriter(System.out)

fun main() {
    val n: Int = scanner.nextInt()
    val d: Int = scanner.nextInt()
    val nodes: MutableList<AbstractNode?> = ArrayList()

    nodes.add(Image(n, d, scanner))
    nodes[0]!!.forwardPass()

    val l: Int = scanner.nextInt()

    (0 until l).forEach { _ ->
        val type = scanner.next()
        var node: AbstractNode? = null

        when (type) {
            "relu" -> node = Relu(nodes, scanner)
            "pool" -> node = Pool(nodes, scanner)
            "bias" -> node = Bias(nodes, scanner)
            "cnvm", "cnve", "cnvc" -> node = Cnv(type, nodes, scanner)
        }

        nodes.add(node)
        assert(node != null)
        node!!.forwardPass()
    }

    nodes[nodes.size - 1]!!.readDerivative(scanner)

    (nodes.size - 1 downTo 1).forEach { i ->
        nodes[i]!!.backwardsPass()
        nodes[i]!!.giveOutputData(nodes, i)
    }

    (0 until nodes[nodes.size - 1]!!.outputDepth).forEach { i -> (0 until nodes[nodes.size - 1]!!.outputHeight).forEach { j -> (0 until nodes[nodes.size - 1]!!.outputWidth).forEach { k -> writer.write(nodes[nodes.size - 1]!!.outputData[i][j][k].toString() + " ") } } }

    writer.println()

    nodes.forEach { node -> node!!.printDerivative(writer) }

    writer.flush()
    writer.close()
}


internal abstract class AbstractNode {
    protected lateinit var inputData: Array<Array<DoubleArray>>
    lateinit var outputData: Array<Array<DoubleArray>>
    protected lateinit var derivativeOutputData: Array<Array<DoubleArray>>
    protected lateinit var derivativeInputData: Array<Array<DoubleArray>>

    protected var inputHeight = 0
    protected var inputWidth = 0
    protected var inputDepth = 0

    var outputHeight = 0
    var outputWidth = 0
    var outputDepth = 0

    constructor()

    constructor(nodes: List<AbstractNode?>) {
        val last = nodes[nodes.size - 1]
        inputData = Utils.copy3d(last!!.outputData)
        inputHeight = inputData[0].size
        inputWidth = inputData[0][0].size
        inputDepth = inputData.size
        derivativeOutputData = Array(inputDepth) { Array(inputHeight) { DoubleArray(inputWidth) } }
    }

    abstract fun forwardPass()
    abstract fun backwardsPass()
    abstract fun printDerivative(writer: PrintWriter)

    fun giveOutputData(nodes: List<AbstractNode?>, id: Int) {
        nodes[id - 1]!!.derivativeInputData = Utils.copy3d(derivativeOutputData)
    }

    fun readDerivative(scanner: InputReader) {
        derivativeInputData = Array(outputDepth) { Array(outputHeight) { DoubleArray(outputWidth) } }
        (0 until outputDepth).forEach { i -> (0 until outputHeight).forEach { j -> (0 until outputWidth).forEach { k -> derivativeInputData[i][j][k] = scanner.nextInt().toDouble() } } }
    }
}

internal class Image(n: Int, d: Int, scanner: InputReader) : AbstractNode() {
    init {
        inputHeight = n
        inputWidth = n
        inputDepth = d
        outputHeight = n
        outputWidth = n
        outputDepth = d
        inputData = Array(d) { Array(n) { DoubleArray(n) } }

        (0 until d).forEach { i -> (0 until n).forEach { j -> (0 until n).forEach { k -> inputData[i][j][k] = scanner.nextInt().toDouble() } } }
    }

    override fun forwardPass() {
        outputData = Utils.copy3d(inputData)
    }

    override fun backwardsPass() {}

    override fun printDerivative(writer: PrintWriter) {
        (0 until inputDepth).forEach { i -> (0 until inputHeight).forEach { j -> (0 until inputWidth).forEach { k -> writer.print(derivativeInputData[i][j][k].toString() + " ") } } }
        writer.println()
    }
}

internal class Relu(nodes: List<AbstractNode?>, scanner: InputReader) : AbstractNode(nodes) {
    private var alpha: Int

    init {
        alpha = scanner.nextInt()
        outputHeight = inputHeight
        outputWidth = inputWidth
        outputDepth = inputDepth
        outputData = Array(outputDepth) { Array(outputHeight) { DoubleArray(outputWidth) } }
    }

    override fun forwardPass() {
        (0 until outputDepth).forEach { i -> (0 until outputHeight).forEach { j -> (0 until outputWidth).forEach { k -> outputData[i][j][k] = when {inputData[i][j][k] >= 0 -> inputData[i][j][k] else -> 1.0 / alpha * inputData[i][j][k] } } } }
    }

    override fun backwardsPass() {
        derivativeOutputData = Array(outputDepth) { Array(outputHeight) { DoubleArray(outputWidth) } }
        (0 until outputDepth).forEach { i -> (0 until outputHeight).forEach { j ->
            (0 until outputWidth).forEach { k ->
                when {
                    inputData[i][j][k] >= 0 -> { derivativeOutputData[i][j][k] = derivativeInputData[i][j][k] * 1 }
                    else -> { derivativeOutputData[i][j][k] = derivativeInputData[i][j][k] * 1.0 / alpha }
                }
            }
        }
        }
    }

    override fun printDerivative(writer: PrintWriter) {}
}

internal class Pool(nodes: List<AbstractNode?>, scanner: InputReader) : AbstractNode(nodes) {
    private var s: Int

    init {
        s = scanner.nextInt()
        outputHeight = (inputHeight - s) / s + 1
        outputWidth = (inputWidth - s) / s + 1
        outputDepth = inputDepth
        outputData = Array(outputDepth) { Array(outputHeight) { DoubleArray(outputWidth) } }
    }

    override fun forwardPass() {
        (0 until outputDepth).forEach { d ->
            (0 until outputHeight).forEach { i ->
                (0 until outputWidth).forEach { j ->
                    var max = Double.NEGATIVE_INFINITY
                    (i * s until i * s + s).forEach { i2 -> (j * s until j * s + s).forEach { j2 -> max = max(inputData[d][i2][j2], max) } }
                    outputData[d][i][j] = max
                }
            }
        }
    }

    override fun backwardsPass() {
        (0 until outputDepth).forEach { d -> (0 until outputHeight).forEach { i -> (0 until outputWidth).forEach { j -> (i * s until i * s + s).forEach { i2 -> (j * s until j * s + s).asSequence().filter { inputData[d][i2][it] == outputData[d][i][j] }.forEach { derivativeOutputData[d][i2][it] = derivativeInputData[d][i][j] } } } } }
    }

    override fun printDerivative(writer: PrintWriter) {}
}

internal class Bias(nodes: List<AbstractNode?>, scanner: InputReader) : AbstractNode(nodes) {
    private var b: IntArray = IntArray(inputData.size)
    private var derivativeB: DoubleArray = DoubleArray(inputData.size)

    init {
        b.indices.forEach { i -> b[i] = scanner.nextInt() }
        outputHeight = inputHeight
        outputWidth = inputWidth
        outputDepth = inputDepth
        outputData = Array(outputDepth) { Array(outputHeight) { DoubleArray(outputWidth) } }
    }

    override fun forwardPass() {
        (0 until outputDepth).forEach { i -> (0 until outputHeight).forEach { j -> (0 until outputWidth).forEach { k -> outputData[i][j][k] = inputData[i][j][k] + b[i] } } }
    }

    override fun backwardsPass() {
        (0 until outputDepth).forEach { i -> (0 until outputHeight).forEach { j ->
            (0 until outputWidth).forEach { k ->
                derivativeB[i] += derivativeInputData[i][j][k]
                derivativeOutputData[i][j][k] = derivativeInputData[i][j][k]
            }
        }
        }
    }

    override fun printDerivative(writer: PrintWriter) {
        derivativeB.forEach { j -> writer.write("$j ") }
        writer.println()
    }
}

internal class Cnv(private var type: String, nodes: List<AbstractNode?>, scanner: InputReader) : AbstractNode(nodes) {
    private var hCore: Int
    private var kCore: Int
    private var step: Int
    private var padding: Int
    private var a: Array<Array<Array<DoubleArray>>>
    private var derivativeA: Array<Array<Array<DoubleArray>>>
    private lateinit var paddingInputData: Array<Array<DoubleArray>>

    init {
        hCore = scanner.nextInt()
        kCore = scanner.nextInt()
        step = scanner.nextInt()
        padding = scanner.nextInt()
        a = Array(hCore) { Array(inputDepth) { Array(kCore) { DoubleArray(kCore) } } }
        derivativeA = Array(hCore) { Array(inputDepth) { Array(kCore) { DoubleArray(kCore) } } }
        (0 until hCore).forEach { i -> (0 until inputDepth).forEach { j -> (0 until kCore).forEach { l -> (0 until kCore).forEach { m -> a[i][j][l][m] = scanner.nextInt().toDouble() } } } }
        outputHeight = (inputHeight - kCore + 2 * padding) / step + 1
        outputWidth = (inputWidth - kCore + 2 * padding) / step + 1
        outputDepth = hCore
        outputData = Array(outputDepth) { Array(outputHeight) { DoubleArray(outputWidth) } }
    }

    override fun forwardPass() {
        val paddingData = getPadding()
        paddingInputData = Utils.copy3d(paddingData)
        (0 until outputDepth).forEach { od -> (0 until inputDepth).forEach { id -> (0 until outputHeight).forEach { i ->
            (0 until outputWidth).forEach { j ->
                var i3 = i * step
                (0 until kCore).forEach { i2 ->
                    var j3 = j * step
                    (0 until kCore).forEach { j2 ->
                        outputData[od][i][j] += paddingData[id][i3][j3] * a[od][id][i2][j2]
                        j3++
                    }
                    i3++
                }
            }
        }
        }
        }
    }

    override fun backwardsPass() {
        val paddingData = makeCompress(backwardsPadding)
        (0 until inputDepth).forEach { i -> (0 until inputHeight).forEach { j -> (0 until inputWidth).forEach { k -> derivativeOutputData[i][j][k] += paddingData[i][j + padding][k + padding] } } }
    }

    override fun printDerivative(writer: PrintWriter) {
        (0 until hCore).forEach { i -> (0 until inputDepth).forEach { j -> (0 until kCore).forEach { k -> (0 until kCore).forEach { l -> writer.write(derivativeA[i][j][k][l].toString() + " ") } } } }
        writer.println()
    }

    private val backwardsPadding: Array<Array<DoubleArray>>
        get() {
            val result = Array(inputDepth) { Array(inputHeight + 2 * padding) { DoubleArray(inputWidth + 2 * padding) } }
            (0 until outputDepth).forEach { od -> (0 until inputDepth).forEach { id -> (0 until outputHeight).forEach { i ->
                (0 until outputWidth).forEach { j ->
                    var i3 = i * step
                    (0 until kCore).forEach { i2 ->
                        var j3 = j * step
                        (0 until kCore).forEach { j2 ->
                            result[id][i3][j3] += a[od][id][i2][j2] * derivativeInputData[od][i][j]
                            derivativeA[od][id][i2][j2] += paddingInputData[id][i3][j3] * derivativeInputData[od][i][j]
                            j3++
                        }
                        i3++
                    }
                }
            }
            }
            }

            return result
        }

    private fun makeCompress(matrix: Array<Array<DoubleArray>>): Array<Array<DoubleArray>> {
        when (type) {
            "cnvm" -> return makeMirrorCompress(matrix)
            "cnve" -> return makeBorderExtensionCompress(matrix)
            "cnvc" -> return makeCyclicShiftCompress(matrix)
        }
        throw IllegalStateException()
    }

    private fun makeMirrorCompress(matrix: Array<Array<DoubleArray>>): Array<Array<DoubleArray>> {
        (0 until inputDepth).forEach { d ->
            val n = inputHeight
            val m = inputWidth
            (0 until m + 2 * padding).forEach { j ->
                (0 until padding).forEach { i -> matrix[d][2 * padding - i][j] += matrix[d][i][j] }
                (n + padding until n + 2 * padding).forEach { i -> matrix[d][2 * (n + padding) - i - 2][j] += matrix[d][i][j] }
            }
            (padding until padding + n).forEach { i ->
                (0 until padding).forEach { j -> matrix[d][i][padding - j + padding] += matrix[d][i][j] }
                (m + padding until m + 2 * padding).forEach { j -> matrix[d][i][2 * m + padding - j - 2 + padding] += matrix[d][i][j] }
            }
        }

        return matrix
    }

    private fun makeBorderExtensionCompress(matrix: Array<Array<DoubleArray>>): Array<Array<DoubleArray>> {
        (0 until inputDepth).forEach { d ->
            val n = inputHeight
            val m = inputWidth
            (0 until m + 2 * padding).forEach { j ->
                (0 until padding).forEach { i -> matrix[d][padding][j] += matrix[d][i][j] }
                (n + padding until n + 2 * padding).forEach { i -> matrix[d][n + padding - 1][j] += matrix[d][i][j] }
            }
            (padding until padding + n).forEach { i ->
                (0 until padding).forEach { j -> matrix[d][i][padding] += matrix[d][i][j] }
                (m + padding until m + 2 * padding).forEach { j -> matrix[d][i][m + padding - 1] += matrix[d][i][j] }
            }
        }

        return matrix
    }

    private fun makeCyclicShiftCompress(matrix: Array<Array<DoubleArray>>): Array<Array<DoubleArray>> {
        (0 until inputDepth).forEach { d ->
            val n = inputHeight
            val m = inputWidth
            (0 until m + 2 * padding).forEach { j ->
                (padding - 1 downTo -1 + 1).forEach { i -> matrix[d][i + n][j] += matrix[d][i][j] }
                (n + padding until n + 2 * padding).forEach { i -> matrix[d][i - n][j] += matrix[d][i][j] }
            }
            (padding until padding + n).forEach { i ->
                (padding - 1 downTo -1 + 1).forEach { j -> matrix[d][i][j + m] += matrix[d][i][j] }
                (m + padding until m + 2 * padding).forEach { j -> matrix[d][i][j - m] += matrix[d][i][j] }
            }
        }

        return matrix
    }

    private fun getPadding(): Array<Array<DoubleArray>> {
        when (type) {
            "cnvm" -> return makeMirrorShift()
            "cnve" -> return makeBorderExtensionShift()
            "cnvc" -> return makeCyclicShift()
        }
        throw IllegalStateException()
    }

    private fun initPaddingData(): Array<Array<DoubleArray>> {
        val result = Array(inputDepth) { Array(inputHeight + 2 * padding) { DoubleArray(inputWidth + 2 * padding) } }
        (0 until inputDepth).forEach { i -> (0 until inputHeight).forEach { j -> (0 until inputWidth).forEach { k -> result[i][j + padding][k + padding] = inputData[i][j][k] } } }
        return result
    }

    private fun makeMirrorShift(): Array<Array<DoubleArray>> {
        val paddingData = initPaddingData()
        val paddingDataHeight = paddingData[0].size
        val paddingDataWidth = paddingData[0][0].size
        val paddingDataDepth = paddingData.size

        (0 until paddingDataDepth).forEach { k ->
            (padding until inputHeight + padding).forEach { i ->
                (0 until padding).forEach { j -> paddingData[k][i][j] += inputData[k][i - padding][padding - j] }
                (padding + inputWidth until paddingDataWidth).forEach { j -> paddingData[k][i][j] += inputData[k][i - padding][2 * inputWidth + padding - j - 2] }
            }
            (0 until paddingDataWidth).forEach { j ->
                (0 until padding).forEach { i -> paddingData[k][i][j] += paddingData[k][2 * padding - i][j] }
                (inputHeight + padding until paddingDataHeight).forEach { i -> paddingData[k][i][j] += paddingData[k][2 * (inputHeight + padding) - i - 2][j] }
            }
        }

        return paddingData
    }

    private fun makeBorderExtensionShift(): Array<Array<DoubleArray>> {
        val paddingData = initPaddingData()
        val paddingDataHeight = paddingData[0].size
        val paddingDataWidth = paddingData[0][0].size
        val paddingDataDepth = paddingData.size

        (0 until paddingDataDepth).forEach { k ->
            (padding until inputHeight + padding).forEach { i ->
                (0 until padding).forEach { j -> paddingData[k][i][j] += inputData[k][i - padding][0] }
                (padding + inputWidth until paddingDataWidth).forEach { j -> paddingData[k][i][j] += inputData[k][i - padding][inputWidth - 1] }
            }
            (0 until paddingDataWidth).forEach { j ->
                (0 until padding).forEach { i -> paddingData[k][i][j] += paddingData[k][padding][j] }
                (inputHeight + padding until paddingDataHeight).forEach { i -> paddingData[k][i][j] += paddingData[k][inputHeight + padding - 1][j] }
            }
        }

        return paddingData
    }

    private fun makeCyclicShift(): Array<Array<DoubleArray>> {
        val paddingData = initPaddingData()
        val paddingDataHeight = paddingData[0].size
        val paddingDataWidth = paddingData[0][0].size
        val paddingDataDepth = paddingData.size

        (0 until paddingDataDepth).forEach { k ->
            (padding until inputHeight + padding).forEach { i ->
                (padding - 1 downTo -1 + 1).forEach { j -> paddingData[k][i][j] += paddingData[k][i][inputWidth + j] }
                (padding + inputWidth until paddingDataWidth).forEach { j -> paddingData[k][i][j] += paddingData[k][i][j - inputWidth] }
            }
            (0 until paddingDataWidth).forEach { j ->
                (padding - 1 downTo -1 + 1).forEach { i -> paddingData[k][i][j] += paddingData[k][i + inputHeight][j] }
                (inputHeight + padding until paddingDataHeight).forEach { i -> paddingData[k][i][j] += paddingData[k][i - inputHeight][j] }
            }
        }

        return paddingData
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

internal object Utils {
    private fun copy1d(a: DoubleArray): DoubleArray {
        val res = DoubleArray(a.size)
        System.arraycopy(a, 0, res, 0, a.size)
        return res
    }

    private fun copy2d(a: Array<DoubleArray>): Array<DoubleArray> {
        val res = Array(a.size) { DoubleArray(a[0].size) }
        res.indices.forEach { i -> res[i] = copy1d(a[i]) }
        return res
    }

    fun copy3d(a: Array<Array<DoubleArray>>): Array<Array<DoubleArray>> {
        val res = Array(a.size) { Array(a[0].size) { DoubleArray(a[0][0].size) } }
        res.indices.forEach { i -> res[i] = copy2d(a[i]) }
        return res
    }
}