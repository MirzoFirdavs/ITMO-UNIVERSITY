import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class A {
    private static final long MOD = 998244353;
    // (((element % MOD) + MOD) % MOD)
 
    public static void cutFromTheEnd(ArrayList<Long> a) {
        while (a.get(a.size() - 1) % MOD == 0 && a.size() > 0) {
            a.remove(a.size() - 1);
        }
    }
 
    public static void solve() {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);
 
        int n = inputReader.nextInt();
        int m = inputReader.nextInt();
        long maxMN = Math.max(m, n);
 
        long[] p = new long[n + 1];
        long[] q = new long[m + 1];
        long[] value = new long[m + n + 1];
        long[] ratio = new long[1000];
 
        for (int i = 0; i < n + 1; ++i) {
            p[i] = inputReader.nextLong();
        }
 
        for (int i = 0; i < m + 1; ++i) {
            q[i] = inputReader.nextLong();
        }
 
        ArrayList<Long> a = new ArrayList<>();
 
        for (int i = 0; i < maxMN + 1; ++i) {
            long curP = i < p.length ? p[i] : 0;
            long curQ = i < q.length ? q[i] : 0;
            long element = curP + curQ;
            a.add(element);
        }
 
        cutFromTheEnd(a);
        printWriter.println(a.size() - 1);
        for (Long element : a) {
            printWriter.print(element % MOD + " ");
        }
        printWriter.println();
 
        for (int i = 0; i < n + m + 1; ++i) {
            int current = Math.min(i, m) + 1;
            for (int j = 0; j < current; ++j) {
                long curP = i - j < p.length ? p[i - j] : 0;
                long curQ = j < q.length ? q[j] : 0;
                value[i] += curP * curQ;
                value[i] %= MOD;
            }
        }
 
        ArrayList<Long> b = new ArrayList<>();
        for (long element : value) {
            b.add(element);
        }
        cutFromTheEnd(b);
 
        printWriter.println(b.size() - 1);
        for (Long element : b) {
            printWriter.print(element % MOD + " ");
        }
 
        printWriter.println();
 
        for (int i = 0; i < 1000; ++i) {
            ratio[i] = i < p.length ? p[i] : 0;
        }
 
        for (int i = 0; i < 1000; ++i) {
            int current = Math.min(i, m) + 1;
            for (int j = 1; j < current; ++j) {
                if (j < q.length) {
                    long element = ratio[i] - (ratio[i - j] * q[j]);
                    ratio[i] = (((element % MOD) + MOD) % MOD);
 
                }
            }
        }
 
        for (long element : ratio) {
            printWriter.print(element + " ");
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