import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
 
public class G {
    public static class Pair {
        int first;
        int second;
 
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
 
        @SuppressWarnings("unused")
        public int getFirst() {
            return first;
        }
 
        @SuppressWarnings("unused")
        public int getSecond() {
            return second;
        }
 
        @SuppressWarnings("unused")
        public void setIndex(int first) {
            this.first = first;
        }
 
        @SuppressWarnings("unused")
        public void setSecond(int second) {
            this.second = second;
        }
 
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Pair)) {
                return false;
            }
            Pair key = (Pair) o;
            return first == key.first && second == key.second;
        }
 
        @Override
        public int hashCode() {
            int result = first;
            result = 31 * result + second;
            return result;
        }
    }
 
    public static boolean[] used;
    public static ArrayList<ArrayList<Integer>> edges;
    public static int[] matching;
    public static int n;
    public static int m;
    public static int k;
    public static HashSet<Pair> pairs;
    public static TreeSet<Integer> set;
 
    public static boolean dfs(int u) {
        if (used[u]) {
            return false;
        }
        used[u] = true;
        for (int v : edges.get(u)) {
            if (matching[v] == -1 || dfs(matching[v])) {
                matching[v] = u;
                return true;
            }
        }
        return false;
    }
 
    public static void prepare() {
        pairs = new HashSet<>();
        set = new TreeSet<>();
        edges = new ArrayList<>();
        matching = new int[k + 1 + m - n];
        used = new boolean[m + k - n + 1];
    }
 
    public static void generateInput() {
        InputReader in = new InputReader(System.in);
        m = in.nextInt();
        k = in.nextInt();
        n = in.nextInt();
        int t = in.nextInt();
        prepare();
        for (int i = 0; i < k + 1 + m - n; ++i) {
            matching[i] = -1;
        }
        for (int i = 0; i < t; ++i) {
            int a = in.nextInt();
            int b = in.nextInt();
            pairs.add(new Pair(a, b));
        }
        int q = in.nextInt();
        for (int i = 0; i < q; i++) {
            int no = in.nextInt();
            set.add(no);
        }
        for (int i = 0; i < m + 1 + k - n; ++i) {
            edges.add(new ArrayList<>());
        }
    }
 
    public static void generateOutput() {
        PrintWriter out = new PrintWriter(System.out);
        ArrayList<Pair> result = new ArrayList<>();
        for (int i = 1; i <= k + m - n; ++i) {
            if (matching[i] == -1) {
                out.println("NO");
                out.close();
                return;
            } else {
                if (matching[i] <= m && i <= k) {
                    result.add(new Pair(matching[i], i + m));
                }
            }
        }
        out.println("YES");
        for (Pair pair: result) {
            out.println(pair.getFirst() + " " + pair.getSecond());
        }
        out.close();
    }
 
    public static void main(String[] args) {
        generateInput();
        for (int i = 1; i <= m; i++) {
            for (int j = m + 1; j <= m + k; j++) {
                if (!pairs.contains(new Pair(i, j))) {
                    edges.get(i).add(j - m);
                }
            }
            if (!set.contains(i)) {
                for (int j = m + k + 1; j <= 2 * m + k - n; j++) {
                    edges.get(i).add(j - m);
                }
            }
        }
        for (int i = m + 1; i <= m + k; i++) {
            if (!set.contains(i)) {
                for (int j = m + 1; j <= m + k - n; j++) {
                    edges.get(j).add(i - m);
                }
            }
        }
        for (int i = 1; i <= m + k - n; i++) {
            used = new boolean[m + k - n + 1];
            dfs(i);
        }
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
        public long nexLong() {
            return Long.parseLong(next());
        }
    }
}