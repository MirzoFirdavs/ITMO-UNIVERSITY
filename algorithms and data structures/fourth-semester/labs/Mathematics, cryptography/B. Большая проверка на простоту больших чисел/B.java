import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.IntStream;
 
public class B {
    public static List<BigInteger> nums = new ArrayList<>();
    public static List<String> result = new ArrayList<>();
 
    public static void generateInput() {
        InputReader inputReader = new InputReader(System.in);
 
        IntStream.range(0, inputReader.nextInt()).forEach(i -> nums.add(new BigInteger(inputReader.next())));
    }
 
    public static void generateSolution() {
        nums.forEach(i -> {
            if (i.isProbablePrime(6)) {
                result.add("YES");
            } else {
                result.add("NO");
            }
        });
    }
 
    public static void generateOutput() {
        PrintWriter printWriter = new PrintWriter(System.out);
 
        result.forEach(printWriter::println);
 
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