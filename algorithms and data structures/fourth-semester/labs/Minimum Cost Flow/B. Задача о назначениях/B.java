import java.io.*;
import java.util.StringTokenizer;
 
public class B {
    private static final int INF = 1000000001;
    public static int n;
    public static int[][] c;
    public static int[] result;
    public static int[] v;
 
    public static void hungarian() {
        int[] u = new int[n + 1];
        int[] match = new int[n + 1];
        int[] way = new int[n + 1];
 
        for (int i = 1; i <= n; ++i) {
            match[0] = i;
 
            int currentColumn = 0;
            int[] minV = new int[n + 1];
 
            boolean[] used = new boolean[n + 1];
 
            for (int j = 0; j < n + 1; ++j) {
                minV[j] = INF;
            }
 
            while (match[currentColumn] != 0) {
                used[currentColumn] = true;
                int currentRow = match[currentColumn];
                int del = INF;
                int minColumn = 0;
 
                for (int j = 1; j <= n; ++j) {
                    if (!used[j]) {
                        int current = c[currentRow][j] - u[currentRow] - v[j];
 
                        if (current < minV[j]) {
                            minV[j] = current;
                            way[j] = currentColumn;
                        }
 
                        if (minV[j] < del) {
                            del = minV[j];
                            minColumn = j;
                        }
                    }
                }
 
                for (int j = 0; j <= n; ++j) {
                    if (used[j]) {
                        u[match[j]] += del;
                        v[j] -= del;
                    } else {
                        minV[j] -= del;
                    }
                }
 
                currentColumn = minColumn;
            }
 
            while (currentColumn != 0) {
                int minColumn = way[currentColumn];
                match[currentColumn] = match[minColumn];
                currentColumn = minColumn;
            }
        }
 
        for (int j = 1; j <= n; ++j) {
            result[match[j]] = j;
        }
    }
 
    public static void generateInput() {
        InputReader inputReader = new InputReader(System.in);
        n = inputReader.nextInt();
 
        c = new int[n + 1][n + 1];
 
        for (int i = 1; i < n + 1; ++i) {
            for (int j = 1; j < n + 1; ++j) {
                c[i][j] = inputReader.nextInt();
            }
        }
    }
 
    public static void generateOutput() {
        PrintWriter printWriter = new PrintWriter(System.out);
 
        result = new int[n + 1];
        v = new int[n + 1];
 
        hungarian();
 
        printWriter.println(-v[0]);
 
        for (int i = 1; i <= n; ++i) {
            printWriter.println(i + " " + result[i]);
        }
 
        printWriter.close();
    }
 
    public static void main(String[] args) {
        generateInput();
        generateOutput();
    }
 
    public static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
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
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
        @SuppressWarnings("unused")
        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}