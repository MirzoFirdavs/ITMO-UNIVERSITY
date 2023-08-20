import java.io.*;
import java.util.*;

public class H {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);

        List<Double> y = new ArrayList<>();
        Map<Double, Double> mapX = new HashMap<>();
        Map<Double, Double> mapE = new HashMap<>();
        double cur1 = 0, cur2 = 0;

        @SuppressWarnings("unused")
        double k = inputReader.nextDouble();
        int n = inputReader.nextInt();

        for (int i = 0; i < n; ++i) {
            double x_i = inputReader.nextDouble();
            double y_i = inputReader.nextDouble();
            double px = !mapX.containsKey(x_i - 1) ? 0 : mapX.get(x_i - 1);
            double pe = !mapE.containsKey(x_i - 1) ? 0 : mapE.get(x_i - 1);
            mapX.put(x_i - 1, px + 1 / (double) n);
            mapE.put(x_i - 1, pe + y_i / (double) n);
            y.add(y_i);
        }

        for (int i = 0; i < n; ++i) cur1 += (y.get(i) * y.get(i)) / n;
        for (Double dot: mapX.keySet()) cur2 += (mapE.get(dot) * mapE.get(dot)) / mapX.get(dot);

        printWriter.println(cur1 - cur2);
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
