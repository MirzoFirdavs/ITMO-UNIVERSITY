package game;

public class Table {

    private int b;
    private int[] table;

    public Table(int b) {
        this.b = b;
        table = new int[b + 10];
        for (int i = 0; i <= b + 9; ++i) {
            table[i] = 0;
        }
    }


    public void add(int pos, int cur) {
        table[pos] += cur;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int r = 1; r <= b; ++r) {
            sb.append("Player " + r + '\n');
            sb.append(table[r - 1]);
            sb.append('\n');
        }
        return sb.toString();
    }
}