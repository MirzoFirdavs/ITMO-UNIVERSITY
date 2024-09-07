import java.io.*;
import java.util.*;

public class CrossChecking {
    public static class Pair implements Comparable<Pair> {
        int value;
        int index;

        public Pair(int value, int index) {
            this.value = value;
            this.index = index;
        }

        @SuppressWarnings("unused")
        public int getValue() {
            return value;
        }

        @SuppressWarnings("unused")
        public void setValue(int value) {
            this.value = value;
        }

        public int getIndex() {
            return index;
        }

        @SuppressWarnings("unused")
        public void setIndex(int index) {
            this.index = index;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return value == pair.value && index == pair.index;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, index);
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "value=" + value +
                    ", index=" + index +
                    '}';
        }

        @Override
        public int compareTo(Pair o) {
            return 0;
        }
    }

    public static String getResult(List<Integer> lst) {
        StringBuilder result = new StringBuilder().append(lst.size());

        for (Integer value: lst) {
            result.append(" ").append(value);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);

        int j = 0;

        int n = inputReader.nextInt();
        @SuppressWarnings("unused")
        int m = inputReader.nextInt();
        int k = inputReader.nextInt();

        List<Pair> pairs = new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < k; ++i) {
            result.add(new ArrayList<>());
        }

        for (int i = 0; i < n; ++i) {
            int x = inputReader.nextInt();
            pairs.add(new Pair(x, i + 1));
        }

        pairs.sort(Comparator.comparing(Pair::getValue));

        for (int i = 0; i < n; ++i) {
            result.get(j).add(pairs.get(i).getIndex());
            j++;
            if (j == k) {
                j = 0;
            }
        }

        for (int i = 0; i < k; ++i) {
            printWriter.println(getResult(result.get(i)));
        }

        printWriter.close();
    }

    public static class InputReader {
        public final BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public final String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            return tokenizer.nextToken();
        }

        @SuppressWarnings("unused")
        public final int nextInt() {
            return Integer.parseInt(next());
        }

        @SuppressWarnings("unused")
        public final long nextLong() {
            return Long.parseLong(next());
        }

        @SuppressWarnings("unused")
        public final Double nextDouble() {return Double.parseDouble(next());}
    }
}