package expression.exceptions;

import expression.*;
import expression.parser.BaseParser;
import expression.parser.StringSource;

public class ExpressionParser extends BaseParser implements Parser {
    public Operand parse(String expression) {
        this.source = new StringSource(expression);
        nextChar();
        Operand result = parseE();
        if (eof()) {
            return result;
        }
        throw error("Expected end of input, actual: '" + ch + '\'');
    }

    private Operand parseE() {
        return parseBinaryOperation('+', '-');
    }

    private Operand parseT() {
        return parseBinaryOperation('*', '/');
    }

    private Operand parseF() {
        skipWhiteSpace();

        if (Character.isDigit(ch)) {
            return parseConst();
        } else if (test('(')) {
            Operand lowestLevel = parseE();
            expect(')');
            return lowestLevel;
        } else if (ch == '-') {
            return parseU();
        } else if (Character.isLetter(ch)) {
            String name = readName();
            if (name.equals("sqrt")) {
                return new Sqrt(parseF());
            } else if (name.equals("abs")) {
                return new Abs(parseF());
            }
            return new Variable(name);
        }
        throw error("Expected constant, variable or parentheses, actual: '" + ch + '\'');
    }

    private Operand parseU() {

        expect('-');

        skipWhiteSpace();
        if (Character.isDigit(ch)) {
            return parseNegativeConst();
        } else if (test('(')) {
            Operand nestedOperand = parseE();
            expect(')');
            return new CheckedNegate(nestedOperand);
        } else if (Character.isLetter(ch)) {
            String name = readName();
            if (name.equals("sqrt")) {
                return new CheckedNegate(new Sqrt(parseF()));
            } else if (name.equals("abs")) {
                return new CheckedNegate(new Abs(parseF()));
            }
            return new CheckedNegate(new Variable(name));
        }
        return new CheckedNegate(parseU());
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
                left = new CheckedAdd(left, right);
            } else if (op == '-') {
                left = new CheckedSubtract(left, right);
            } else if (op == '*') {
                left = new CheckedMultiply(left, right);
            } else if (op == '/') {
                left = new CheckedDivide(left, right);
            }
        }

        return left;
    }

    private int GetNumber(String s) {
        int a;
        try{
            a = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw error("Fail format of number " + s);
        }
        return a;
    }

    private Operand parseConst() {
        String value = nextNumber();
        return new Const(GetNumber(value));
    }

    private Operand parseNegativeConst() {
        String value = nextNumber();
        return new Const(GetNumber("-" + value));
    }


    private void skipWhiteSpace() {
        while (Character.isWhitespace(ch)) {
            nextChar();
        }
    }

    private String readName() {
        final StringBuilder sb = new StringBuilder();
        do {
            sb.append(ch);
            nextChar();
        } while (Character.isLetter(ch));
        String s = sb.toString();
        if (s.equals("x") || s.equals("y") || s.equals("z") || s.equals("abs") || s.equals("sqrt"))
            return s;
        throw error("Unexpected variable or function name: " + s);
    }

    private String nextNumber() {
        final StringBuilder sb = new StringBuilder();
        do {
            sb.append(ch);
            nextChar();
        } while (Character.isDigit(ch));
        return sb.toString();
    }
}