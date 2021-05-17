package expression;

public class UnaryMinus extends UnaryOperations {
    public UnaryMinus(final Operand operand) {
        super(operand, "-");
    }

    public int calc(final int a) {
        return -a;
    }
}