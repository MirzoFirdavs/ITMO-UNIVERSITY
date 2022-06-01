import java.io.*;
import java.util.StringTokenizer;
 
public class I {
    private static final int MOD = 104857601;
 
    public static long[] evenOdd(long[] a, int even) {
        long[] ret = new long[a.length / 2];
 
        for (int i = 0; i < ret.length; i++) {
            if (2 * i + even < a.length && 2 * i + even >= 0) {
                long element = a[2 * i + even];
                ret[i] = ((element % MOD) + MOD) % MOD;
            }
        }
 
        return ret;
    }
 
    public static long Mul(long a, long b) {
        long result = a * b;
        return ((result % MOD) + MOD) % MOD;
    }
 
    public static long[] prod(long[] P, long[] Q) {
        long[] product = new long[P.length + Q.length];
 
        for (int i = 0; i < P.length; i++) {
            for (int j = 0; j < Q.length; j++) {
                long element = product[i + j] + Mul(P[i], Mul(Q[j], j % 2 == 0 ? 1L : -1L));
                product[i + j] = ((element % MOD) + MOD) % MOD;
            }
        }
 
        return product;
    }
 
    public static void solve() {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);
 
        long k = inputReader.nextLong();
        long n = inputReader.nextLong();
 
        long[] a = new long[(int) k];
        long[] c = new long[(int) k];
        long[] q = new long[(int) k + 1];
        long[] p = new long[(int) k];
 
        q[0] = 1;
 
        for (int i = 0; i < k; ++i) {
            a[i] = inputReader.nextLong();
        }
 
        for (int i = 0; i < k; ++i) {
            c[i] = inputReader.nextLong();
            q[i + 1] = -c[i];
        }
 
        for (int i = 0; i < k; i++) {
            for (int j = 1; j <= k; j++) {
                long curC = 0;
                long curA = 0;
                
                if (j - 1 < c.length && j - 1 >= 0) {
                    curC = ((c[j - 1] % MOD) + MOD) % MOD;
                }
                
                if (i - j < a.length && i - j >= 0) {
                    curA = ((a[i - j] % MOD) + MOD) % MOD;
                }
                
                long curAC = ((curC * curA % MOD) + MOD) % MOD;
                long element = p[i] + curAC;
                p[i] = ((element % MOD) + MOD) % MOD;
            }
            
            long element = a[i] - p[i];
            p[i] = ((element % MOD) + MOD) % MOD;
        }
 
        n--;
 
        while (n != 0) {
            long[] temp = prod(q, q);
            p = prod(p, q);
            p = evenOdd(p, (int) (n % 2));
            q = evenOdd(temp, 0);
            n /= 2;
        }
 
        long element = 0;
        if (0 < p.length) {
            element = ((p[0] % MOD) + MOD) % MOD;
        }
 
        printWriter.println(((element % MOD) + MOD) % MOD);
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