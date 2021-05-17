package expression;

public class Abs extends UnaryOperations {
    public Abs(final Operand operand) {
        super(operand, "abs");
    }

    public int calc(final int a) {
        if (a == Integer.MIN_VALUE) {
            throw new ArithmeticException("integer overflow");
        }
        if (a < 0) return -a;
        return a;
    }
}
