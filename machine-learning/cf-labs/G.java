import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.StringTokenizer;

import static java.math.MathContext.DECIMAL128;

public class G {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);

        int n = inputReader.nextInt();
        int k = inputReader.nextInt();

        int[] y = new int[n];
        double[] x = new double[n];
        BigDecimal[] countK = new BigDecimal[k];
        BigDecimal[] sqY = new BigDecimal[k];
        BigDecimal[] corr = new BigDecimal[k];

        BigDecimal sumX = BigDecimal.ZERO, sumPowX = BigDecimal.ZERO, result = BigDecimal.ZERO;
        Arrays.fill(countK, BigDecimal.ZERO);

        for (int i = 0; i < n; ++i) {
            y[i] = inputReader.nextInt();
            countK[y[i] - 1] = countK[y[i] - 1].add(BigDecimal.ONE);
        }

        for (int i = 0; i < n; ++i) {
            x[i] = inputReader.nextLong();
            sumX = sumX.add(BigDecimal.valueOf(x[i]));
            sumPowX = sumPowX.add(BigDecimal.valueOf(x[i] * x[i]));
        }

        BigDecimal cur = BigDecimal.valueOf(n).multiply(sumPowX).subtract(sumX.multiply(sumX));
        BigDecimal sqrtX = cur.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : cur.sqrt(DECIMAL128);

        for (int i = 0; i < k; i++) {
            corr[i] = BigDecimal.valueOf(-1.0).multiply(sumX).multiply(countK[i]);
            BigDecimal tmp = BigDecimal.valueOf(n)
                    .multiply(countK[i])
                    .subtract(countK[i].multiply(countK[i]));
            sqY[i] = tmp.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : tmp.sqrt(DECIMAL128);
        }

        for (int i = 0; i < n; i++)
            corr[y[i] - 1] = corr[y[i] - 1].add(BigDecimal.valueOf(n * x[i]));
        for (int i = 0; i < k; i++)
            corr[i] = (sqrtX.multiply(sqY[i]))
                    .compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO
                    : corr[i].divide(sqrtX.multiply(sqY[i]), 34, RoundingMode.HALF_EVEN);
        for (int i = 0; i < k; i++)
            result = result.add(corr[i].multiply(countK[i]));

        printWriter.println(result.divide(BigDecimal.valueOf(n), 34, RoundingMode.HALF_EVEN));
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

        @SuppressWarnings("unused")
        public final BigDecimal nextBigDecimal() {
            return BigDecimal.valueOf(nextDouble());
        }
    }
}