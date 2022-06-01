import java.io.*;
import java.util.*;
 
public class C {
    public static long a;
    public static long b;
    public static long n;
    public static long m;
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
 
    public static void generateInput() {
        InputReader inputReader = new InputReader(System.in);
 
        a = inputReader.nextLong();
        b = inputReader.nextLong();
        n = inputReader.nextLong();
        m = inputReader.nextLong();
    }
 
    public static void generateSolution() {
        long k = myGcd(n, m).getX() * (b - a);
 
        result = a + k * n;
 
        while (result < 0) {
            result += m * n;
        }
 
        while (result > m * n) {
            result -= m * n;
        }
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