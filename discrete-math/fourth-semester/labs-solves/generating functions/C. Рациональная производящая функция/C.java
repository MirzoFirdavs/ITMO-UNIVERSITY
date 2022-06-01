import java.io.*;
import java.util.StringTokenizer;
 
public class C {
    public static int[] elements;
    public static int[] c;
 
    public static int f(int x) {
        int value = 0;
        for (int i = 0; i < x; ++i) {
            value += c[i] * elements[x - i - 1];
        }
        return elements[x] - value;
    }
 
    public static void solve() {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);
        int k = inputReader.nextInt();
 
        elements = new int[k];
        c = new int[k];
        int[] q = new int[k + 1];
        int[] p = new int[k + 1];
        q[0] = 1;
 
        for (int i = 0; i < k; ++i) {
            elements[i] = inputReader.nextInt();
        }
 
        for (int i = 0; i < k; ++i) {
            c[i] = inputReader.nextInt();
            q[i + 1] = -c[i];
        }
 
        for (int i = 0; i < k; ++i) {
            p[i] = f(i);
        }
 
        int deg = k + 1;
        while (deg >= 0 && p[deg - 1] == 0) {
            --deg;
        }
 
        printWriter.println(deg - 1);
        for (int i = 0; i < deg; ++i) {
            printWriter.print(p[i] + " ");
        }
        printWriter.println();
        printWriter.println(k);
        for (int element : q) {
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