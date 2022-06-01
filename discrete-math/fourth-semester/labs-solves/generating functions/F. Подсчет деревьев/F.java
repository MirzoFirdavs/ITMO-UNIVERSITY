import java.io.*;
import java.util.StringTokenizer;
 
public class F {
    private static final int MOD = 1000000007;
 
    public static void solve() {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);
 
        int k = inputReader.nextInt();
        int m = inputReader.nextInt();
 
        long[] c = new long[k];
        long[] trees = new long[m + 1];
        long[] subTrees = new long[m + 1];
 
        trees[0] = 1;
        subTrees[0] = 1;
 
        for (int i = 0; i < k; ++i) {
            c[i] = inputReader.nextLong();
        }
 
        for (int i = 0; i < m; ++i) {
            for (long cI : c) {
                if (cI <= i + 1) {
                    trees[i + 1] += subTrees[i + 1 - (int) cI];
                }
            }
            trees[i + 1] %= MOD;
            for (int j = 0; j < i + 2; ++j) {
                subTrees[i + 1] += trees[i + 1 - j] * trees[j] % MOD;
            }
            subTrees[i + 1] %= MOD;
        }
 
        for (int i = 1; i < trees.length; ++i) {
            printWriter.print(trees[i] % MOD + " ");
        }
 
        printWriter.close();
    }
 
    public static void main(String[] args) {
        solve();
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