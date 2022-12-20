package com.github.mirzofirdavs.calctest

import junit.framework.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addTest() {
        val expr = "1.1+2.2"
        val expected = "3.3"
        Assert.assertEquals(expected,  ExpressionParser().calculate(expr))
    }

    @Test
    fun multiplyTest() {
        val expr = "50*10"
        val expected = "500"
        Assert.assertEquals(expected, ExpressionParser().calculate(expr))
    }

    @Test
    fun subtractTest() {
        val expr = "4-5"
        val expected = "-1"
        Assert.assertEquals(expected, ExpressionParser().calculate(expr))
    }

    @Test
    fun divideTest() {
        val expr = "8/2"
        val expected = "4"
        Assert.assertEquals(expected, ExpressionParser().calculate(expr))
    }

    @Test
    @Throws(Exception::class)
    fun divisionByZeroTest() {
        val expr = "10/0"
        val expected = "∞"
        Assert.assertEquals(expected, ExpressionParser().calculate(expr))
    }

    @Test
    fun doubleDivisionTest() {
        val expr = "8.8/2.2"
        val expected = "4"
        Assert.assertEquals(expected, ExpressionParser().calculate(expr))
    }

    @Test
    fun doubleMultiplyTest() {
        val expr = "8.8*2.4"
        val expected = "21.12"
        Assert.assertEquals(expected, ExpressionParser().calculate(expr))
    }
}