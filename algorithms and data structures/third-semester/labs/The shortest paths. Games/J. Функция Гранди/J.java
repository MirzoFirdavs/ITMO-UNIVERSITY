import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
 
public class J {
    public static ArrayList<ArrayList<Integer>> graph;
    public static boolean[] used;
    public static ArrayList<Integer> v = new ArrayList<>();
 
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
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
        for (int i = 0; i < n; ++i) {
            if (!used[i])
                dfs(i);
        }
        int ans[] = new int[n];
        for (int curVertex : v) {
            ArrayList<Integer> children = new ArrayList<>();
            for (int to : graph.get(curVertex)) {
                children.add(ans[to]);
            }
            Collections.sort(children);
            for (int child : children) {
                if (ans[curVertex] == child)
                    ++ans[curVertex];
            }
        }
 
        for (int i = 0; i < n; ++i)
            System.out.println(ans[i]);
    }
 
    private static void dfs(int vertex) {
        used[vertex] = true;
 
        for (int to : graph.get(vertex)) {
            if (!used[to])
                dfs(to);
        }
        v.add(vertex);
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