import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;
 
public class D {
    public static class Ufo {
        int time;
        int x;
        int y;
 
        public Ufo(int time, int x, int y) {
            this.time = time;
            this.x = x;
            this.y = y;
        }
 
        @SuppressWarnings("unused")
        public int getTime() {
            return time;
        }
 
        @SuppressWarnings("unused")
        public int getX() {
            return x;
        }
 
        @SuppressWarnings("unused")
        public int getY() {
            return y;
        }
 
        @SuppressWarnings("unused")
        public void setTime(int time) {
            this.time = time;
        }
 
        @SuppressWarnings("unused")
        public void setX(int x) {
            this.x = x;
        }
 
        @SuppressWarnings("unused")
        public void setY(int y) {
            this.y = y;
        }
    }
 
    public static int v;
    public static int n;
    public static ArrayList<Ufo> vec;
    public static boolean[] used;
    public static ArrayList<ArrayList<Integer>> edges;
    public static int[] matching;
 
    public static double dist(Ufo a, Ufo b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)) / v * 60;
    }
 
    public static boolean dfs1(int u) {
        if (used[u]) {
            return false;
        }
        used[u] = true;
        for (int v : edges.get(u)) {
            if (matching[v] == -1 || dfs1(matching[v])) {
                matching[v] = u;
                return true;
            }
        }
        return false;
    }
 
    public static boolean dfs2(int u) {
        if (used[u]) {
            return false;
        }
        used[u] = true;
        if (matching[u] != -1) {
            dfs2(matching[u]);
        }
        return true;
    }
 
    public static void generateInput() {
        InputReader in = new InputReader(System.in);
        n = in.nextInt();
        v = in.nextInt();
        vec = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String ch = in.next();
            int x = in.nextInt();
            int y = in.nextInt();
            int hh = ch.charAt(0) * 10 + ch.charAt(1);
            int mm = ch.charAt(3) * 10 + ch.charAt(4);
            vec.add(new Ufo(60 * hh + mm, x, y));
        }
        vec.sort(Comparator.comparing(p -> (p.time)));
        edges = new ArrayList<>(n);
        matching = new int[n];
        used = new boolean[n];
        for (int i = 0; i < n; ++i) {
            edges.add(new ArrayList<>());
            matching[i] = -1;
        }
    }
 
    public static void generateOutput() {
        PrintWriter out = new PrintWriter(System.out);
        int result = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (dfs2(i)) {
                result++;
            }
        }
        out.println(result);
        out.close();
    }
 
    public static void main(String[] args) {
        generateInput();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (vec.get(i).time + dist(vec.get(i), vec.get(j)) <= vec.get(j).time) {
                    edges.get(i).add(j);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (dfs1(i)) {
                used = new boolean[n];
            }
        }
        used = new boolean[n];
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
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}