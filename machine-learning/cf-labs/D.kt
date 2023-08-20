package d

import java.io.*
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.tanh

private val `in` = StreamTokenizer(BufferedReader(InputStreamReader(System.`in`)))
private val out = PrintWriter(System.out)

@Throws(IOException::class)
fun nextInt(): Int {
    `in`.nextToken()
    return `in`.nval.toInt()
}

private val diffs: MutableList<M> = ArrayList()

private fun getInput(n: Int): List<Var> {
    val wf = Var(n, n)
    wf.readMatrix()
    val uf = Var(n, n)
    uf.readMatrix()
    val bf = Var(n, 1)
    bf.readMatrix()
    val wi = Var(n, n)
    wi.readMatrix()
    val ui = Var(n, n)
    ui.readMatrix()
    val bi = Var(n, 1)
    bi.readMatrix()
    return listOf(wf, uf, bf, wi, ui, bi)
}

@Throws(IOException::class)
fun main() {
    val n = nextInt()
    val cur1: List<Var> = getInput(n)
    val cur2: List<Var> = getInput(n)
    val m = nextInt()
    val ft: MutableList<M> = ArrayList(listOf(Var(1, 1)))
    val it: MutableList<M> = ArrayList(listOf(Var(1, 1)))
    val ot: MutableList<M> = ArrayList(listOf(Var(1, 1)))
    val crt: MutableList<M> = ArrayList(listOf(Var(1, 1)))
    val hts: MutableList<M> = ArrayList(listOf(Var(n, 1)))
    hts[0].readMatrix()
    val cts: MutableList<M> = ArrayList(listOf(Var(n, 1)))
    cts[0].readMatrix()
    val xts: MutableList<M> = ArrayList(listOf(Var(1, 1)))
    (1..m).forEach { i ->
        xts.add(Var(n, 1))
        xts[i].readMatrix()
    }
    (1..m).forEach { i ->
        doOps(cur1[0], cur1[1], cur1[2], ft, xts, hts, i)
        doOps(cur1[3], cur1[4], cur1[5], it, xts, hts, i)
        doOps(cur2[0], cur2[1], cur2[2], ot, xts, hts, i)
        val tmp: List<M> = generate(cur2[3], cur2[4], cur2[5], xts, hts, i)
        crt.add(Tnh(tmp[2]))
        crt[i].op()
        diffs.addAll(arrayListOf(tmp[0], tmp[1], tmp[2], crt[i]))
        val operation4: M = Had(arrayOf(ft[i], cts[i - 1]))
        operation4.op()
        val operation5: M = Had(arrayOf(it[i], crt[i]))
        operation5.op()
        cts.add(Sum(arrayOf(operation4, operation5)))
        cts[i].op()
        hts.add(Had(arrayOf(ot[i], cts[i])))
        hts[i].op()
        diffs.addAll(arrayListOf(operation4, operation5, cts[i], hts[i]))
    }
    (1..m).forEach { i -> ot[i].printMatrix() }
    hts[hts.size - 1].printMatrix()
    cts[cts.size - 1].printMatrix()
    hts[hts.size - 1].readDiff()
    cts[cts.size - 1].readDiff()
    (m downTo 1).forEach { i -> ot[i].readDiff() }
    diffs.indices.reversed().forEach { i -> diffs[i].differentiate() }
    (m downTo 1).forEach { i -> xts[i].printDiff() }
    hts[0].printDiff()
    cts[0].printDiff()
    (cur1.indices).forEach { i -> cur1[i].printDiff() }
    (cur2.indices).forEach { i -> cur2[i].printDiff() }
    out.flush()
}

private fun generate(w: M, u: M, b: M, xts: List<M>, hts: List<M>, i: Int): List<M> {
    val operation1: M = Mul(w, xts[i])
    operation1.op()
    val operation2: M = Mul(u, hts[i - 1])
    operation2.op()
    val operation3: M = Sum(arrayOf(operation1, operation2, b))
    operation3.op()
    return listOf(operation1, operation2, operation3)
}

private fun doOps(w: M, u: M, b: M, list: MutableList<M>, xts: List<M>, hts: List<M>, i: Int) {
    val tmp: List<M> = generate(w, u, b, xts, hts, i)
    list.add(Sigmoid(tmp[2]))
    list[i].op()
    diffs.addAll(arrayListOf(tmp[0], tmp[1], tmp[2], list[i]))
}

abstract class M {
    var matrix: Array<DoubleArray>? = null
        protected set
    var diff: Array<DoubleArray>? = null
    val rows: Int get() = matrix!!.size
    val columns: Int get() = matrix!![0].size

    fun op(): M {
        return if (matrix != null) this
        else {
            operationImpl()
            this
        }
    }

    protected abstract fun operationImpl()
    abstract fun differentiate()

    @Throws(IOException::class)
    fun readMatrix() {
        (0 until rows).forEach { i -> (0 until columns).forEach { j -> matrix!![i][j] = nextInt().toDouble() } }
    }

    fun printMatrix() {
        (0 until rows).forEach { i ->
            (0 until columns).forEach { j -> out.print(matrix!![i][j].toString() + " ") }
            out.println()
        }
    }

    @Throws(IOException::class)
    fun readDiff() {
        diff = Array(rows) { DoubleArray(columns) }
        (0 until rows).forEach { i -> (0 until columns).forEach { j -> diff!![i][j] = nextInt().toDouble() } }
    }

    fun printDiff() {
        (0 until rows).forEach { i ->
            (0 until columns).forEach { j -> out.print(diff!![i][j].toString() + " ") }
            out.println()
        }
    }
}

private class Var(r: Int, c: Int) : M() {
    init {
        matrix = Array(r) { DoubleArray(c) }
        diff = Array(r) { DoubleArray(c) }
    }

    override fun operationImpl() {}
    override fun differentiate() {}
}

private class Tnh(private val x: M) : M() {
    override fun operationImpl() {
        x.op()
        matrix = Array(x.rows) { DoubleArray(x.columns) }
        val cur = x.matrix
        (0 until rows).forEach { i -> (0 until columns).forEach { j -> matrix!![i][j] = tanh(cur!![i][j]) } }
    }

    override fun differentiate() {
        when (x.diff) {null -> x.diff = Array(x.rows) { DoubleArray(x.columns) } }
        (0 until x.rows).forEach { i -> (0 until x.columns).forEach { j -> x.diff!![i][j] += (1 - tanh(x.matrix!![i][j]).pow(2.0)) * diff!![i][j] } }
    }
}

private class Sigmoid(private val x: M) : M() {
    override fun operationImpl() {
        x.op()
        matrix = Array(x.rows) { DoubleArray(x.columns) }
        (0 until rows).forEach { i -> (0 until columns).forEach { j -> matrix!![i][j] = 1 / (1 + exp(-x.matrix!![i][j])) } }
    }

    override fun differentiate() {
        when (x.diff) {null -> x.diff = Array(x.rows) { DoubleArray(x.columns) } }
        (0 until x.rows).forEach { i -> (0 until x.columns).forEach { j -> x.diff!![i][j] += matrix!![i][j] * diff!![i][j] * (1 - matrix!![i][j]) } }
    }
}

private class Mul(private val a: M, private val b: M) : M() {
    override fun operationImpl() {
        a.op()
        b.op()
        matrix = Array(a.rows) { DoubleArray(b.columns) }
        (0 until a.rows).forEach { x -> (0 until a.columns).forEach { y -> (0 until b.columns).forEach { z -> matrix!![x][z] += a.matrix!![x][y] * b.matrix!![y][z] } } }
    }

    override fun differentiate() {
        when (a.diff) {null -> a.diff = Array(rows) { DoubleArray(b.rows) } }
        when (b.diff) {null -> b.diff = Array(a.columns) { DoubleArray(columns) } }
        (0 until rows).forEach { x -> for (y in 0 until b.rows) for (z in 0 until columns) a.diff!![x][y] += diff!![x][z] * b.matrix!![y][z] }
        (0 until a.columns).forEach { y -> for (z in 0 until columns) for (x in 0 until rows) b.diff!![y][z] += a.matrix!![x][y] * diff!![x][z] }
    }
}

private class Sum(private val matrices: Array<M>) : M() {
    override fun operationImpl() {
        matrix = Array(matrices[0].op().rows) { DoubleArray(matrices[0].op().columns) }
        matrices.forEach { matrix -> (0 until rows).forEach { i -> for (j in 0 until columns) this.matrix!![i][j] += matrix.op().matrix!![i][j] } }
    }

    override fun differentiate() {
        matrices.forEach { prev ->
            when (prev.diff) {null -> prev.diff = Array(prev.rows) { DoubleArray(prev.columns) } }
            (0 until prev.rows).forEach { i -> (0 until prev.columns).forEach { j -> prev.diff!![i][j] += diff!![i][j] } }
        }
    }
}

@Suppress("LABEL_NAME_CLASH")
private class Had(private val matrices: Array<M>) : M() {
    override fun operationImpl() {
        matrix = Array(matrices[0].op().rows) { DoubleArray(matrices[0].op().columns) }
        (0 until matrices[0].op().rows).forEach { i -> when {matrices[0].op().columns >= 0 -> System.arraycopy(matrices[0].op().matrix!![i], 0, matrix!![i], 0, matrices[0].op().columns) } }
        (1 until matrices.size).forEach { z -> (0 until rows).forEach { i -> for (j in 0 until columns) matrix!![i][j] *= matrices[z].op().matrix!![i][j] } }
    }

    override fun differentiate() {
        matrices.indices.forEach { i ->
            when (matrices[i].diff) {null -> matrices[i].diff = Array(rows) { DoubleArray(columns) } }
            val temp = Array(rows) { DoubleArray(columns) }
            (0 until rows).forEach { j -> if (columns >= 0) System.arraycopy(diff!![j], 0, temp[j], 0, columns) }
            matrices.indices.forEach { restId ->
                when (i) {
                    restId -> return@forEach
                    else -> { (0 until matrices[i].rows).forEach { k -> for (j in 0 until matrices[i].columns) temp[k][j] *= matrices[restId].matrix!![k][j] } }
                }
            }
            (0 until matrices[i].rows).forEach { k -> (0 until matrices[i].columns).forEach { j -> matrices[i].diff!![k][j] += temp[k][j] } }
        }
    }
}
