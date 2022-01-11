import java.io.*;
import java.util.*;
 
public class C {
    public static class Pair {
        int first;
        int second;
 
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
 
    public static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    public static boolean[] used = new boolean[2002];
    public static int[] mt = new int[2002];
 
    public static boolean findKuhn(int v) {
        if (used[v]) {
            return false;
        }
        used[v] = true;
        for (int to : graph.get(v)) {
            if (mt[to] == 0 || findKuhn(mt[to])) {
                mt[to] = v;
                return true;
            }
        }
        return false;
    }
 
    public static void solve() throws FileNotFoundException {
        InputReader input = new InputReader(new FileInputStream("matching.in"));
        int n = input.nextInt();
        ArrayList<Pair> vertex = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            int w = input.nextInt();
            vertex.add(new Pair(w, i + 1));
        }
        for (int i = 0; i < 2002; ++i) {
            graph.add(new ArrayList<>());
        }
        vertex.sort(Comparator.comparingInt(o -> o.first));
        Collections.reverse(vertex);
        for (int i = 1; i <= n; i++) {
            int s = input.nextInt();
            for (int j = 0; j < s; j++) {
                int v = input.nextInt();
                graph.get(i).add(v + n);
                graph.get(v + n).add(i);
            }
        }
        for (Pair now : vertex) {
            used = new boolean[2002];
            findKuhn(now.second);
        }
        int[] result = new int[1001];
        for (int i = 1; i <= n; i++) {
            result[mt[i + n]] = i;
        }
        PrintWriter out = new PrintWriter("matching.out");
        for (int i = 1; i <= n; i++) {
            out.print(result[i] + " ");
        }
        out.close();
    }
 
    public static void main(String[] args) throws FileNotFoundException {
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
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}