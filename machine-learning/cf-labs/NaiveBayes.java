import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NaiveBayes {
    public static PrintWriter printWriter;
    public static InputReader inputReader;
    public static Map<Integer, Integer> classCount;
    public static Map<String, Integer> wordsCount;
    public static List<Map<String, Integer>> hasWordRepeats;
    public static Map<Integer, List<PairMode>> wordsPair;
    public static List<List<String>> fitWords;
    public static List<List<String>> testWords;
    public static Pair[] cl;
    public static int[] lambdas;
    public static int k, n, m, alpha;

    public static class Pair {
        int c;
        int l;

        public Pair(int c, int l) {
            this.c = c;
            this.l = l;
        }

        public int getC() {
            return c;
        }

        @SuppressWarnings("unused")
        public void setC(int c) {
            this.c = c;
        }

        public int getL() {
            return l;
        }

        @SuppressWarnings("unused")
        public void setL(int l) {
            this.l = l;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return l == pair.l && Objects.equals(c, pair.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(c, l);
        }

        @Override
        public String toString() {
            return "Pair{" + "c='" + c + '\'' + ", l=" + l + '}';
        }
    }

    public static class PairMode {
        String first;
        double second;

        public PairMode(String first, double second) {
            this.first = first;
            this.second = second;
        }

        public String getFirst() {
            return first;
        }

        @SuppressWarnings("unused")
        public void setFirst(String first) {
            this.first = first;
        }

        public double getSecond() {
            return second;
        }

        @SuppressWarnings("unused")
        public void setSecond(double second) {
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PairMode pair = (PairMode) o;
            return first.equals(pair.first) && Objects.equals(second, pair.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        @Override
        public String toString() {
            return "PairMode{" + "first='" + first + '\'' + ", second=" + second + '}';
        }
    }

    public static Pair castToInt(String a, String b) {
        return new Pair(Integer.parseInt(a) - 1, Integer.parseInt(b));
    }

    public static double calculation(List<PairMode> currentWordsPair, Set<String> setOfCurrentWords) {
        double result = 1;

        for (PairMode pair : currentWordsPair) {
            result *= !setOfCurrentWords.contains(pair.getFirst()) ? (1 - pair.getSecond()) : pair.getSecond();
        }

        return result;
    }

    public static void generateFitPart() {
        for (int i = 0; i < n; ++i) {
            final int index = i;
            Pair pair = cl[index];
            classCount.put(pair.getC(), classCount.getOrDefault(pair.getC(), 0) + 1);

            IntStream.range(
                    0, pair.getL()).forEach(j ->
                    wordsCount.put(fitWords.get(index).get(j), wordsCount
                            .getOrDefault(fitWords.get(index).get(j), 0) + 1)
            );

            Set<String> setOfCurrentWords = new TreeSet<>(fitWords.get(i));

            for (String key : setOfCurrentWords) {
                Map<String, Integer> map = hasWordRepeats.get(pair.getC());
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
        }

        for (int i = 0; i < k; ++i) {
            List<PairMode> pairs = new ArrayList<>();

            for (Map.Entry<String, Integer> wordCount : wordsCount.entrySet()) {
                String word = wordCount.getKey();
                double numerator = hasWordRepeats.get(i).getOrDefault(word, 0) + alpha;
                double denominator = classCount.getOrDefault(i, 0) + alpha * 2;
                PairMode pairMode = new PairMode(word, numerator / denominator);
                pairs.add(pairMode);
            }

            wordsPair.put(i, pairs);
        }
    }

    public static void generateTestPart() {
        printWriter = new PrintWriter(System.out);

        for (int i = 0; i < m; ++i) {
            double[] result = new double[k];
            Set<String> setOfCurrentWords = new TreeSet<>(testWords.get(i));

            for (int j = 0; j < k; ++j) {
                double numerator = (double) lambdas[j] * classCount.getOrDefault(j, 0) / n;
                double denominator = 0;
                numerator *= calculation(wordsPair.get(j), setOfCurrentWords);

                for (int g = 0; g < k; ++g) {
                    double expression = (double) classCount.getOrDefault(g, 0) / n;
                    expression *= calculation(wordsPair.get(g), setOfCurrentWords);
                    denominator += expression;
                }

                result[j] = numerator / denominator;
            }

            double sum = Arrays.stream(result).sum();
            Arrays.stream(result).forEach(element -> printWriter.print((element / sum) + " "));
            printWriter.println();
        }

        printWriter.close();
    }

    public static void main(String[] args) {
        inputReader = new InputReader(System.in);
        k = inputReader.nextInt();
        lambdas = new int[k];

        IntStream.range(0, k).forEach(i -> lambdas[i] = inputReader.nextInt());

        alpha = inputReader.nextInt();
        n = inputReader.nextInt();
        cl = new Pair[n];
        fitWords = IntStream.range(0, n).mapToObj(i -> new ArrayList<String>()).collect(Collectors.toList());

        for (int i = 0; i < n; ++i) {
            final int index = i;

            String[] input = inputReader.nextLine().split(" ");
            cl[index] = castToInt(input[0], input[1]);
            Arrays.stream(input, 2, input.length).forEach(word -> fitWords.get(index).add(word));
        }

        m = inputReader.nextInt();
        testWords = IntStream.range(0, m).mapToObj(i -> new ArrayList<String>()).collect(Collectors.toList());

        for (int i = 0; i < m; ++i) {
            String[] input = inputReader.nextLine().split(" ");
            testWords.get(i).addAll(Arrays.asList(input).subList(1, input.length));
        }

        classCount = new TreeMap<>();
        wordsCount = new TreeMap<>();
        hasWordRepeats = IntStream.range(0, k).mapToObj(i -> new TreeMap<String, Integer>()).collect(Collectors.toList());
        wordsPair = new TreeMap<>();

        generateFitPart();
        generateTestPart();
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

        public final String nextLine() {
            try {
                if (tokenizer != null && tokenizer.hasMoreTokens()) {
                    StringBuilder sb = new StringBuilder();
                    while (tokenizer.hasMoreTokens()) {
                        sb.append(tokenizer.nextToken());
                        sb.append(" ");
                    }
                    return sb.toString().trim();
                } else {
                    return reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
