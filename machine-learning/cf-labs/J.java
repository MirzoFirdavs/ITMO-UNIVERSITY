import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class J {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);
        Map<Double, Double> map = new HashMap<>();
        Map<Double, Map<Double, Double>> mapP = new HashMap<>();

        double result = 0;
        @SuppressWarnings("unused")
        double kX = inputReader.nextDouble();
        @SuppressWarnings("unused")
        double kY = inputReader.nextDouble();
        int n = inputReader.nextInt();

        for (int i = 0; i < n; ++i) {
            double x = inputReader.nextDouble();
            double y = inputReader.nextDouble();

            Map<Double, Double> pp = mapP.getOrDefault(x, new HashMap<>());
            Double px = map.getOrDefault(x, 0D);
            Double py = pp.getOrDefault(y, 0D);
            pp.put(y, py + 1 / (double) n);
            mapP.put(x, pp);
            map.put(x, px + 1 / (double) n);
        }

        for (Double curX : map.keySet())
            for (Double curY : mapP.get(curX).keySet())
                result += mapP.get(curX).get(curY) * (Math.log(mapP.get(curX).get(curY)) - Math.log(map.get(curX)));

        printWriter.println(0D - result);
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

        public final Double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}
