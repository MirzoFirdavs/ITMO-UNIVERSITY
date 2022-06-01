import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class F {
    public static class Vertical {
        int x;
        int y1;
        int y2;
 
        public Vertical(int x, int y1, int y2) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
        }
 
        @SuppressWarnings("unused")
        public int getX() {
            return x;
        }
 
        @SuppressWarnings("unused")
        public int getY1() {
            return y1;
        }
 
        @SuppressWarnings("unused")
        public int getY2() {
            return y2;
        }
 
        @SuppressWarnings("unused")
        public void setX(int x) {
            this.x = x;
        }
 
        @SuppressWarnings("unused")
        public void setY1(int y1) {
            this.y1 = y1;
        }
 
        @SuppressWarnings("unused")
        public void setY2(int y2) {
            this.y2 = y2;
        }
    }
 
    public static class Horizontal {
        int x1;
        int x2;
        int y;
 
        public Horizontal(int x1, int x2, int y) {
            this.x1 = x1;
            this.x2 = x2;
            this.y = y;
        }
 
        @SuppressWarnings("unused")
        public int getX1() {
            return x1;
        }
 
        @SuppressWarnings("unused")
        public int getX2() {
            return x2;
        }
 
        @SuppressWarnings("unused")
        public int getY() {
            return y;
        }
 
        @SuppressWarnings("unused")
        public void setX1(int x1) {
            this.x1 = x1;
        }
 
        @SuppressWarnings("unused")
        public void setX2(int x2) {
            this.x2 = x2;
        }
 
        @SuppressWarnings("unused")
        public void setY(int y) {
            this.y = y;
        }
    }
 
    public static boolean dfs1(int u, ArrayList<ArrayList<Integer>> edge, boolean[] used, int[] matching) {
        if (used[u]) {
            return false;
        }
        used[u] = true;
        for (int v : edge.get(u)) {
            if (matching[v] == -1 || dfs1(matching[v], edge, used, matching)) {
                matching[v] = u;
                return true;
            }
        }
        return false;
    }
 
    public static void dfs2(int u, ArrayList<ArrayList<Integer>> edge, boolean[] usedHorizontal, boolean[] usedVertical, int[] matching) {
        if (usedHorizontal[u]) {
            return;
        }
        usedHorizontal[u] = true;
        for (int v : edge.get(u)) {
            if (!usedVertical[v]) {
                usedVertical[v] = true;
                dfs2(matching[v], edge, usedHorizontal, usedVertical, matching);
            }
        }
    }
 
    public static boolean check(Horizontal a, Vertical b) {
        if (b.getY1() <= a.getY() && a.getY() <= b.getY2()) {
            return a.getX1() <= b.getX() && b.getX() <= a.getX2();
        }
        return false;
    }
 
    public static ArrayList<Horizontal> horizontals;
    public static ArrayList<Vertical> verticals;
    public static int[] matching;
    public static boolean[] usedHorizontal;
    public static ArrayList<ArrayList<Integer>> edge;
    public static boolean[] horizontalMask;
    public static boolean[] usedVertical;
 
    public static void generateInput() {
        InputReader in = new InputReader(System.in);
        int n = in.nextInt();
        horizontals = new ArrayList<>();
        verticals = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            int x1 = in.nextInt();
            int y1 = in.nextInt();
            int x2 = in.nextInt();
            int y2 = in.nextInt();
            if (x1 > x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
            }
            if (y1 > y2) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }
            if (x1 == x2) {
                verticals.add(new Vertical(x1, y1, y2));
            } else {
                horizontals.add(new Horizontal(x1, x2, y2));
            }
        }
        edge = new ArrayList<>(horizontals.size());
        for (int i = 0; i < horizontals.size(); ++i) {
            edge.add(new ArrayList<>());
        }
        for (int i = 0; i < horizontals.size(); i++) {
            for (int j = 0; j < verticals.size(); j++) {
                if (check(horizontals.get(i), verticals.get(j))) {
                    edge.get(i).add(j);
                }
            }
        }
        usedHorizontal = new boolean[horizontals.size()];
        usedVertical = new boolean[verticals.size()];
        matching = new int[verticals.size()];
        for (int i = 0; i < verticals.size(); ++i) {
            matching[i] = -1;
        }
        horizontalMask = new boolean[horizontals.size()];
    }
 
    public static void generateOutput() {
        PrintWriter out = new PrintWriter(System.out);
        int horiz_count = 0;
        int vert_count = 0;
        for (int i = 0; i < Math.max(horizontals.size(), verticals.size()); ++i) {
            if (i < horizontals.size() && usedHorizontal[i]) {
                horiz_count++;
            }
            if (i < verticals.size() && !usedVertical[i]) {
                vert_count++;
            }
        }
        out.println(horiz_count + vert_count);
        out.close();
    }
 
    public static void main(String[] args) {
        generateInput();
        for (int i = 0; i < horizontals.size(); i++) {
            usedHorizontal = new boolean[horizontals.size()];
            dfs1(i, edge, usedHorizontal, matching);
        }
        for (int a : matching) {
            if (a != -1) {
                horizontalMask[a] = true;
            }
        }
        usedHorizontal = new boolean[horizontals.size()];
        for (int i = 0; i < horizontals.size(); i++) {
            if (!horizontalMask[i]) {
                dfs2(i, edge, usedHorizontal, usedVertical, matching);
            }
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