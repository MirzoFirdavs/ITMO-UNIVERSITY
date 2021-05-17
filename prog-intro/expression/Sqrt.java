package expression;

public class Sqrt extends UnaryOperations {
    public Sqrt(final Operand operand) {
        super(operand, "sqrt");
    }

    public int calc(final int a) {
        if (a < 0) {
            throw new ArithmeticException("sqrt by negative integer");
        }
        int left = 0;
        int right = a;
        while (right - left > 0) {
            int mid = (left + right + 1) / 2;
            if (mid * mid <= a && Integer.MAX_VALUE / mid >= mid) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }
}
