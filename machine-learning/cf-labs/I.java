import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.StringTokenizer;

public class I {
    public static class Pair {
        private double first;
        private double second;

        public Pair(double first, double second) {
            this.first = first;
            this.second = second;
        }

        public double getFirst() {
            return first;
        }

        @SuppressWarnings("unused")
        public void setFirst(int first) {
            this.first = first;
        }

        public double getSecond() {
            return second;
        }

        @SuppressWarnings("unused")
        public void setSecond(int second) {
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return first == pair.first && second == pair.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        @Override
        public String toString() {
            return "Pair{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }
    }

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);

        Map<Pair, Integer> map = new HashMap<>();
        Map<Double, Integer> mapX = new HashMap<>();
        Map<Double, Integer> mapY = new HashMap<>();

        @SuppressWarnings("unused")
        int k1 = inputReader.nextInt();
        @SuppressWarnings("unused")
        int k2 = inputReader.nextInt();
        int n = inputReader.nextInt();
        double result = n;

        for (int i = 0; i < n; ++i) {
            double x = inputReader.nextDouble();
            double y = inputReader.nextDouble();

            Pair pair = new Pair(x, y);

            if (!mapX.containsKey(x)) mapX.put(x, 1);
            else mapX.put(x, mapX.get(x) + 1);

            if (!mapY.containsKey(y)) mapY.put(y, 1);
            else mapY.put(y, mapY.get(y) + 1);

            if (!map.containsKey(pair)) map.put(pair, 1);
            else map.put(pair, map.get(pair) + 1);
        }

        for (Pair pair : map.keySet()) {
            double cur = (mapX.get(pair.getFirst()) * mapY.get(pair.getSecond())) / (double) n;
            double dot = map.get(pair) - cur;

            result += ((dot - cur) * (dot + cur)) / cur;
        }

        printWriter.println(result);
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
        public final Double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}
