import java.io.*;
import java.util.*;

public class F {
    public static class Pair {
        private int first;
        private int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int getFirst() {
            return first;
        }

        @SuppressWarnings("unused")
        public void setFirst(int first) {
            this.first = first;
        }

        public int getSecond() {
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
        List<Pair> pairs = new ArrayList<>();
        Map<Integer, List<Integer>> map = new HashMap<>();

        long distance1 = 0, distance2 = 0, totalP = 0, totalS = 0;
        int cnt = 0;
        int k = inputReader.nextInt();
        int n = inputReader.nextInt();

        long[] prefSum = new long[k + 1];
        long[] prefCnt = new long[k + 1];
        long[] sufSum = new long[k + 1];
        long[] sufCnt = new long[k + 1];

        for (int i = 0; i < n; ++i) {
            int x_i = inputReader.nextInt();
            int y_i = inputReader.nextInt();

            List<Integer> xy = map.getOrDefault(y_i, new ArrayList<>());
            xy.add(x_i);
            map.put(y_i, xy);
            pairs.add(new Pair(x_i, y_i));
        }

        pairs.sort(Comparator.comparing(p -> p.first));

        for (int key : map.keySet()) {
            long p = 0, s = map.get(key).stream().reduce(0, Integer::sum);
            int len = map.get(key).size();

            List<Integer> array = map.get(key);
            Collections.sort(array);

            for (int i = 0; i < len; ++i) {
                long cur = array.get(i);
                p += cur;
                s -= cur;
                distance1 += (cur * (1 + i) - p) - (cur * (len - i - 1) - s);
            }
        }

        for (Pair pair: pairs) {
            sufSum[pair.getSecond()] += pair.getFirst();
            sufCnt[pair.getSecond()]++;
            totalS += pair.getFirst();
        }

        for (Pair pair : pairs) {
            prefSum[pair.getSecond()] += pair.getFirst();
            prefCnt[pair.getSecond()]++;
            totalP += pair.getFirst();
            sufSum[pair.getSecond()] -= pair.getFirst();
            sufCnt[pair.getSecond()]--;
            totalS -= pair.getFirst();
            distance2 +=
                    pair.getFirst() * (cnt + 1 - prefCnt[pair.getSecond()]) - totalP + prefSum[pair.getSecond()] -
                    (pair.getFirst() * (pairs.size() - cnt - 1 - sufCnt[pair.getSecond()]) - totalS + sufSum[pair.getSecond()]);
            cnt++;
        }

        printWriter.println(distance1);
        printWriter.println(distance2);
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
