package expression;

import java.util.Objects;

public abstract class BinaryExpression implements Operand  {
    protected TripleExpression first;
    protected TripleExpression second;
    protected String operation;

    public BinaryExpression(TripleExpression a, TripleExpression b, String operation) {
        this.first = a;
        this.second = b;
        this.operation = operation;
    }

    public abstract int calc(final int a, final int b);

    @Override
    public int evaluate(int x) {
        return calc(first.evaluate(x, x, x), second.evaluate(x, x, x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return calc(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    public String toString() {
        return "(" + first.toString() + " " + operation + " " + second.toString() + ")";
    }

    public boolean equals(Object obj) {
        if(obj != null && obj.getClass() == getClass()){
            BinaryExpression expression = (BinaryExpression)obj;
            return first.equals(expression.first) && second.equals(expression.second);
        }
        return false;
    }

    final public int hashCode() {
        return Objects.hash(first, second, operation);
    }
}