import java.io.*;
import java.util.*;

public class FScore {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);

        int k = inputReader.nextInt();
        long all = 0;
        long[][] matrix = new long[k][k];

        double[] tp = new double[k];
        double[] fp = new double[k];
        double[] fn = new double[k];
        double[] precision = new double[k];
        double[] recall = new double[k];
        double[] f1 = new double[k];
        double[] row = new double[k];
        double fmeraW = 0;
        double precisionW = 0;
        double recallW = 0;
        double sumTP = 0;
        double sumFP = 0;
        double sumFN = 0;

        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < k; ++j) {
                matrix[i][j] = inputReader.nextInt();
                row[i] += matrix[i][j];
                all += matrix[i][j];
            }
        }

        for (int i = 0; i < k; i++) {
            tp[i] = matrix[i][i] * row[i];

            for (int j = 0; j < k; j++) {
                if (j == i) {
                    continue;
                }

                fp[i] += matrix[i][j] * row[i];
                fn[i] += matrix[j][i] * row[i];
            }

            sumTP += tp[i];
            sumFP += fp[i];
            sumFN += fn[i];
        }

        for (int i = 0; i < k; ++i) {
            precision[i] = tp[i] + fp[i] == 0 ? 0 : tp[i] / (tp[i] + fp[i]);
            recall[i] = tp[i] + fn[i] == 0 ? 0 : tp[i] / (tp[i] + fn[i]);
            f1[i] = precision[i] + recall[i] == 0 ? 0 : 2 * (precision[i] * recall[i]) / (precision[i] + recall[i]);
            fmeraW += row[i] * f1[i];
            precisionW += row[i] * precision[i];
            recallW += row[i] * recall[i];
        }

        precisionW /= all;
        recallW /= all;
        sumTP /= all;
        sumFP /= all;
        sumFN /= all;

        printWriter.println(sumTP + 0.5 * (sumFP + sumFN) == 0 ? 0: sumTP / (sumTP + 0.5 * (sumFP + sumFN)));
        printWriter.println(recallW + precisionW == 0 ? 0: 2 * precisionW * recallW / (precisionW + recallW));
        printWriter.println(fmeraW / all);
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