import java.io.*;
import java.util.*;
 
public class D {
    public static long n;
    public static long e;
    public static long c;
    public static long p;
    public static long q;
    public static long result;
 
    public static class Triple {
        long d;
        long x;
        long y;
 
        public Triple(long d, long x, long y) {
            this.d = d;
            this.x = x;
            this.y = y;
        }
 
        public long getD() {
            return d;
        }
 
        @SuppressWarnings("unused")
        public void setD(long d) {
            this.d = d;
        }
 
        public long getX() {
            return x;
        }
 
        @SuppressWarnings("unused")
        public void setX(long x) {
            this.x = x;
        }
 
        public long getY() {
            return y;
        }
 
        @SuppressWarnings("unused")
        public void setY(long y) {
            this.y = y;
        }
    }
 
    public static Triple myGcd(long a, long b) {
        if (b == 0) {
            return new Triple(a, 1, 0);
        }
 
        Triple triple = myGcd(b, a % b);
 
        return new Triple(triple.getD(), triple.getY(), triple.getX() - triple.getY() * (a / b));
    }
 
    public static long multiply(long a, long b, long mod) {
        if (b == 1) {
            return a;
        }
 
        if (b % 2 == 1) {
            return (multiply(a, b - 1, mod) + a) % mod;
        }
 
        long x = multiply(a, b / 2, mod);
 
        return (x << 1) % mod;
    }
 
    public static long bin_pow(long a, long p, long mod) {
        if (p == 1) {
            return a;
        }
 
        if (p % 2 == 1) {
            long x = bin_pow(a, p - 1, mod);
 
            return multiply(x, a, mod);
        }
 
        long x = bin_pow(a, p / 2, mod);
 
        return multiply(x, x, mod);
    }
 
    public static void generateInput() {
        InputReader inputReader = new InputReader(System.in);
 
        n = inputReader.nextLong();
        e = inputReader.nextLong();
        c = inputReader.nextLong();
    }
 
    public static void generateSolution() {
        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                p = i;
                q = n / i;
                break;
            }
        }
 
        Triple triple = myGcd(e, (p - 1) * (q - 1));
 
        triple.setX(triple.getX() / triple.getD());
 
        if (triple.getX() < 0) {
            triple.setX(triple.getX() + (p - 1) * (q - 1));
        }
 
        result = bin_pow(c, triple.getX(), n);
    }
 
    public static void generateOutput() {
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.println(result);
        printWriter.close();
    }
 
    public static void main(String[] args) {
        generateInput();
        generateSolution();
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