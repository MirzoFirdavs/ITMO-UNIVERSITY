import java.io.*;
import java.util.*;

public class E {
    public static Map<Integer, Integer> getRank(List<Integer> mas) {
        Map<Integer, Integer> result = new TreeMap<>();
        int cnt = 1;

        for (int element : mas) {
            if (!result.containsKey(element)) {
                result.put(element, cnt);
                cnt++;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);

        int n = inputReader.nextInt();
        double sum = 0;

        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        List<Integer> sortedX = new ArrayList<>();
        List<Integer> sortedY = new ArrayList<>();

        for (int i = 0; i < n; ++i) {
            int xI = inputReader.nextInt();
            int yI = inputReader.nextInt();

            x.add(xI);
            y.add(yI);
            sortedX.add(xI);
            sortedY.add(yI);
        }

        Collections.sort(sortedX);
        Collections.sort(sortedY);
        Map<Integer, Integer> dX = getRank(sortedX);
        Map<Integer, Integer> dY = getRank(sortedY);

        for (int i = 0; i < n; ++i) sum += Math.pow(dX.get(x.get(i)) - dY.get(y.get(i)), 2);

        printWriter.println(1 - 6 * sum / (Math.pow(n, 3) - n));
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
