import java.io.*;
import java.util.*;
 
public class A {
    public static final List<Integer> result = new ArrayList<>();
 
    public static void getPrimeFactorization(int n) {
        int iter = 2;
 
        while (iter * iter <= n) {
            while (n % iter == 0) {
                result.add(iter);
                n /= iter;
            }
            ++iter;
        }
 
        if (n > 1) {
            result.add(n);
        }
    }
 
    public static void generateInput() {
        InputReader inputReader = new InputReader(System.in);
 
        int n = inputReader.nextInt();
 
        getPrimeFactorization(n);
    }
 
    public static void generateOutput() {
        PrintWriter printWriter = new PrintWriter(System.out);
 
        for (int element : result) {
            printWriter.print(element + " ");
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