package expression;

public abstract class UnaryOperations implements Operand{
    private final Operand operand;
    private final String form;

    public UnaryOperations(final Operand operand, String s) {
        this.operand = operand;
        form = s;
    }

    public abstract int calc(final int a);

    @Override
    public int evaluate(final int num) {
        return calc(operand.evaluate(num));
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return calc(operand.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return form + "(" + operand.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj.getClass() == UnaryOperations.class) {
            UnaryOperations that = (UnaryOperations) obj;
            return that.operand.equals(this.operand) && that.form.equals(this.form);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return operand.hashCode() * 31;
    }
}
