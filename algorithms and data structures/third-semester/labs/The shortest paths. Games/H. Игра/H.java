import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class H {
    public static ArrayList<ArrayList<Integer>> graph;
    public static boolean[] used;
    public static ArrayList<Integer> vv = new ArrayList<>();
 
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int s = in.nextInt();
        graph = new ArrayList<>();
        used = new boolean[n];
        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph.get(u - 1).add(v - 1);
        }
        dfs(s - 1);
        boolean[] result = new boolean[n];
        for (int u : vv) {
            for (int v : graph.get(u)) {
                result[u] |= !result[v];
            }
        }
        System.out.println((result[s - 1] ? "First" : "Second") + " player wins");
    }
 
    private static void dfs(int vertex) {
        if (used[vertex]) {
            return;
        }
        used[vertex] = true;
        for (int j: graph.get(vertex)) {
            dfs(j);
        }
        vv.add(vertex);
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