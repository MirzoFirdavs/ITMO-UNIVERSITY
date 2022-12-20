package com.github.mirzofirdavs.calctest

import kotlin.properties.Delegates

import java.text.NumberFormat


class ExpressionParser {
    private lateinit var expression: String
    private lateinit var currentOperation: Operation

    private var current by Delegates.notNull<Int>()
    private var number by Delegates.notNull<Double>()
    private val numberFormat = NumberFormat.getInstance()

    fun calculate(expression: String): String {
        current = 0

        this.expression = expression

        return numberFormat.format(addSubtract()).replace(",", "")
    }

    private fun addSubtract(): Double {
        var result: Double = multiplyDivide()

        while (currentOperation == Operation.ADD || currentOperation == Operation.SUBTRACT) {
            when (currentOperation) {
                Operation.ADD -> result += multiplyDivide()
                Operation.SUBTRACT -> result -= multiplyDivide()

                else -> throw Exception("invalid location in program reached")
            }
        }

        return result
    }

    private fun multiplyDivide(): Double {
        var result: Double = maxPriority()

        while (currentOperation == Operation.MULTIPLY || currentOperation == Operation.DIVIDE) {
            when (currentOperation) {
                Operation.MULTIPLY -> result *= maxPriority()
                Operation.DIVIDE -> result /= maxPriority()

                else -> throw Exception("invalid location in program reached")
            }
        }

        return result
    }

    private fun maxPriority(): Double {
        getToken(true)

        val result: Double

        when (currentOperation) {
            Operation.NUMBER -> {
                result = number
                getToken(false)
            }

            else -> throw Exception("invalid location in program reached")
        }

        return result
    }

    private fun getToken(mayExpectNumber: Boolean) {
        if (current == expression.length) {
            if (mayExpectNumber) {
                throw ParsingException("expected operand, position: $current")
            }

            return
        }

        if (mayExpectNumber) {
            val saveCurrent = current
            var numberString = ""

            if (expression[current] == '-') {
                numberString = "-"
                current++
            }

            numberString = buildString {
                append(numberString)

                while (current < expression.length && (expression[current].isDigit() || expression[current] == '.')) {
                    append(expression[current++])
                }
            }

            if (!(numberString == "-" || numberString == "")) {
                try {
                    number = numberString.toDouble()
                } catch (e: NumberFormatException) {
                    throw ParsingException("wrong number, position: $current")
                }

                currentOperation = Operation.NUMBER

                return
            }

            current = saveCurrent
        }

        current++

        when (expression[current - 1]) {
            else -> {
                if (mayExpectNumber) {
                    throw ParsingException("expected operand, position: $current")
                }

                when (expression[current - 1]) {
                    '+' -> currentOperation = Operation.ADD
                    '-' -> currentOperation = Operation.SUBTRACT
                    '*' -> currentOperation = Operation.MULTIPLY
                    '/' -> currentOperation = Operation.DIVIDE
                }
            }
        }
    }

    private enum class Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE, NUMBER
    }
}