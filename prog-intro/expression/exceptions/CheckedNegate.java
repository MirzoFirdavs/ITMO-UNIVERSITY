package expression.exceptions;

import expression.Operand;
import expression.UnaryOperations;

public class CheckedNegate extends UnaryOperations {
    public CheckedNegate(final Operand operand) {
        super(operand, "-");
    }

    public int calc(final int a) {
        if (a == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow");
        }
        return -a;
    }
}
