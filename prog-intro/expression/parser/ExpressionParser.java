package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements Parser {

    /*

    L - logic operation
    E - expression + -
    T - term * /
    F - fundamental
    U - unary minus

    C - const
    V - variable

    L -> E | L -> E ^ L -> E & L -> E
    E -> T + E | T - E | T
    T -> F * T | F / T | F
    F -> C     | V     | (L)   | U
    U -> -C    | -V    | -(L)

     */

    public Operand parse(String expression) {
        this.source = new StringSource(expression);
        nextChar();
        Operand result = parseL();
        if (eof()) {
            return result;
        }

        throw error("Expected end of input, actual: '" + ch + '\'');
    }

    private Operand parseL() {
        return parseLogicOperation('|');
    }

    private Operand parseE() {
        return parseBinaryOperation('+', '-');
    }

    private Operand parseT() {
        return parseBinaryOperation('*', '/');
    }

    private Operand parseF() {
        skipWhiteSpace();

        if (isDigit()) {
            return parseConst();
        } else if (test('(')) {
            Operand lowestLevel = parseL();
            expect(')');
            return lowestLevel;
        } else if (ch == '-') {
            return parseU();
        } else if (isLetter()) {
            return new Variable(readVariable());
        }

        throw error("Expected constant, variable or parentheses, actual: '" + ch +'\'');
    }

    private Operand parseU() {

        expect('-');

        skipWhiteSpace();
        if (isDigit()) {
            return parseNegativeConst();
        } else if (test('(')) {
            Operand nestedOperand = parseL();
            expect(')');
            return new UnaryMinus(nestedOperand);
        } else if (isLetter()) {
            return new UnaryMinus(new Variable(readVariable()));
        }
        return new UnaryMinus(parseU());
    }

    private Operand parseBinaryOperation(char firstOperation, char secondOperation) {
        Operand left = firstOperation == '+' ? parseT() : parseF();

        while (!test(END)) {
            skipWhiteSpace();
            char op = ch;
            if (op != firstOperation && op != secondOperation) {
                break;
            }
            nextChar();

            Operand right = firstOperation == '+' ? parseT() : parseF();
            if (op == '+') {
                left = new Add(left, right);
            } else if (op == '-') {
                left = new Subtract(left, right);
            } else if (op == '*') {
                left = new Multiply(left, right);
            } else if (op == '/') {
                left = new Divide(left, right);
            }
        }
        return left;
    }

    private Operand parseLogicOperation(final char operation) {
        Operand left = getLogicLowerOperation(operation);

        while (!test(END)) {
            skipWhiteSpace();
            char op = ch;
            if (op != operation) {
                break;
            }
            nextChar();

            Operand right = getLogicLowerOperation(operation);
            if (op == '|') {
                left = new LogicOR(left, right);
            } else if (op == '^') {
                left = new LogicXOR(left, right);
            } else if (op == '&') {
                left = new LogicAND(left, right);
            }
        }

        return left;
    }

    private Operand getLogicLowerOperation(final char operation) {
        if (operation == '|') {
            return parseLogicOperation('^');
        } else if (operation == '^') {
            return parseLogicOperation('&');
        } else {
            return parseE();
        }
    }

    private Operand parseConst() {
        String value = readDigits();
        return new Const(Integer.parseInt(value));
    }

    private Operand parseNegativeConst() {
        String value = readDigits();
        return new Const(Integer.parseInt('-' + value));
    }


    private void skipWhiteSpace() {
        while (Character.isWhitespace(ch)) {
            nextChar();
        }
    }

    private String readVariable() {
        final StringBuilder sb = new StringBuilder();

        do {
            sb.append(ch);
            nextChar();
        } while (isLetter());

        String s = sb.toString();
        if (s.equals("x") || s.equals("y") || s.equals("z"))
            return s;

        throw error("Unexpected variable name: " + s);
    }

    private String readDigits() {
        final StringBuilder sb = new StringBuilder();

        do {
            sb.append(ch);
            nextChar();
        } while (isDigit());

        return sb.toString();
    }

    private boolean isLetter() {
        return between('a', 'z') || between('A', 'Z');
    }

    private boolean isDigit() {
        return between('0', '9');
    }
}