package a

import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    var o = 0
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val m = reader.readLine().toInt()
    val fff = Array(1 shl m) { 0 }
    for (i in 0 until (1 shl m)) {
        fff[i] = reader.readLine().toInt()
        o += fff[i]
    }
    if (o > 512) {
        mkCnf(m, fff)
        return
    }
    mkDnf(m, fff)
}

fun printResult(rw: MutableList<List<Double>>) {
    println(
        """2
${rw.size} 1"""
    )
    for (l in rw) {
        for (d in l) print("$d ")
        println()
    }
    for (i in rw.indices) print(1.0.toString() + " ")
}

fun mkDnf(m: Int, fff: Array<Int>) {
    val rw: MutableList<List<Double>> = ArrayList()
    for (i in 0 until (1 shl m)) if (fff[i] == 1) rw.add(mkAnd(m, i))
    if (rw.size != 0) printResult(rw)
    else {
        println(
            """
                1
                1
                """.trimIndent()
        )
        for (i in 0 until m) print(1.0.toString() + " ")
    }
    println(-0.5)
}

fun mkCnf(m: Int, fff: Array<Int>) {
    val rw: MutableList<List<Double>> = ArrayList()
    for (i in 0 until (1 shl m)) if (fff[i] == 0) rw.add(mkOr(m, i))
    printResult(rw)
    println(-rw.size + 0.5)
}

fun mkOr(m: Int, i: Int): List<Double> {
    val r: MutableList<Double> = ArrayList()
    for (j in 0 until m) r.add(if (i shr j and 1 == 1) -1.0 else 1.0)
    r.add(Integer.bitCount(i) - 0.5)
    return r
}

fun mkAnd(m: Int, i: Int): List<Double> {
    val r: MutableList<Double> = ArrayList()
    for (j in 0 until m) r.add(if (i shr j and 1 == 1) 1.0 else -1.0)
    r.add(0.5 - Integer.bitCount(i))
    return r
}
