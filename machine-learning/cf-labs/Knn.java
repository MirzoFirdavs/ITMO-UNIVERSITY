import java.io.*;
import java.util.*;

public class Knn {
    public static class Pair implements Comparable<Pair> {
        long index;
        long value;

        public Pair(long index, long value) {
            this.index = index;
            this.value = value;
        }

        @SuppressWarnings("unused")
        public long getValue() {
            return value;
        }

        @SuppressWarnings("unused")
        public void setValue(long value) {
            this.value = value;
        }

        public long getIndex() {
            return index;
        }

        @SuppressWarnings("unused")
        public void setIndex(long index) {
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
                    "index=" + index +
                    ", value=" + value +
                    '}';
        }

        @Override
        public int compareTo(Pair o) {
            return 0;
        }
    }

    public static class ModePair {
        long first;
        long second;

        public ModePair(long first, long second) {
            this.first = first;
            this.second = second;
        }

        @SuppressWarnings("unused")
        public long getFirst() {
            return first;
        }

        @SuppressWarnings("unused")
        public void setFirst(long first) {
            this.first = first;
        }

        public long getSecond() {
            return second;
        }

        @SuppressWarnings("unused")
        public void setSecond(long second) {
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ModePair pair = (ModePair) o;
            return first == pair.first && second == pair.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        @Override
        public String toString() {
            return "ModePair{" +
                    "first=" + first +
                    ", second=" + second +
                    '}';
        }
    }

    private static List<Pair> line;
    private static List<Long> prefixSumArray;

    public static ModePair search(long pointLeft, long pointRight) {
        long left = 0;
        long right = line.size() - 1;

        while (left <= right) {
            long middle = (left + right) / 2;

            if (line.get((int) middle).getIndex() < pointLeft) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        long leftResult = left;

        left = 0;
        right = line.size() - 1;

        while (left <= right) {
            long middle = (left + right) / 2;

            if (line.get((int) middle).getIndex() <= pointRight) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }

        long rightResult = right;

        return new ModePair(leftResult, rightResult);
    }

    public static double calculate(long point, long k) {
        long left = 0;
        long right = 2000000000L;

        while (left + 1 < right) {
            long middle = (left + right) / 2;
            long pointLeft = point - middle;
            long pointRight = point + middle;

            ModePair pair = search(pointLeft, pointRight);

            if (pair.getSecond() - pair.getFirst() + 1 < k) {
                left = middle;
            } else if (pair.getSecond() - pair.getFirst() + 1 > k) {
                right = middle;
            } else {
                return (double) (prefixSumArray.get((int) pair.getSecond() + 1) - prefixSumArray.get((int) pair.getFirst()))
                        / (pair.getSecond() - pair.getFirst() + 1);
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter prlongWriter = new PrintWriter(System.out);

        long currentSum = 0;
        long n = inputReader.nextLong();

        line = new ArrayList<>();
        prefixSumArray = new ArrayList<>();

        for (long i = 0; i < n; ++i) {
            long index = inputReader.nextLong();
            long value = inputReader.nextLong();
            line.add(new Pair(index, value));
        }

        line.sort(Comparator.comparing(Pair::getIndex));
        prefixSumArray.add(0L);

        for (int i = 0; i < n; ++i) {
            currentSum += line.get(i).getValue();
            prefixSumArray.add(currentSum);
        }

        long query = inputReader.nextLong();

        for (int i = 0; i < query; ++i) {
            long point = inputReader.nextLong();
            long k = inputReader.nextLong();
            prlongWriter.println(calculate(point, k));
        }

        prlongWriter.close();
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
        public final long nextInt() {
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