import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class E {
    public static boolean[] used;
    public static ArrayList<ArrayList<Integer>> edges;
    public static int[] matching;
 
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
 
    public static int m;
 
    public static int getVertex(int i, int j) {
        return ((i * m + j) / 2);
    }
 
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        int n = in.nextInt();
        m = in.nextInt();
        int a = in.nextInt();
        int b = in.nextInt();
        ArrayList<ArrayList<Boolean>> empty = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            ArrayList<Boolean> current = new ArrayList<>();
            for (int j = 0; j < m; ++j) {
                current.add(false);
            }
            empty.add(current);
        }
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            String c = in.next();
            for (int j = 0; j < m; j++) {
                if (c.charAt(j) == '*') {
                    empty.get(i).set(j, true);
                    cnt++;
                }
            }
        }
        PrintWriter out = new PrintWriter(System.out);
        if (2 * b <= a) {
            out.println(b * cnt);
        } else {
            int k = n * m;
            edges = new ArrayList<>();
            matching = new int[k];
            used = new boolean[k];
            for (int i = 0; i < k; ++i) {
                edges.add(new ArrayList<>());
                matching[i] = -1;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    if (!empty.get(i).get(j) || (i + j) % 2 == 0) {
                        continue;
                    }
                    if (j > 0 && empty.get(i).get(j - 1)) {
                        edges.get(getVertex(i, j)).add(getVertex(i, j - 1));
                    }
                    if (j < m - 1 && empty.get(i).get(j + 1)) {
                        edges.get(getVertex(i, j)).add(getVertex(i, j + 1));
                    }
                    if (i > 0 && empty.get(i - 1).get(j)) {
                        edges.get(getVertex(i, j)).add(getVertex(i - 1, j));
                    }
                    if (i < n - 1 && empty.get(i + 1).get(j)) {
                        edges.get(getVertex(i, j)).add(getVertex(i + 1, j));
                    }
                }
            }
            for (int i = 0; i < k; i++) {
                if (dfs(i)) {
                    used = new boolean[k];
                }
            }
            int ans = 0;
            for (int j : matching) {
                if (j != -1) {
                    ans++;
                }
            }
            out.println(ans * a + (cnt - 2 * ans) * b);
        }
        out.close();
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