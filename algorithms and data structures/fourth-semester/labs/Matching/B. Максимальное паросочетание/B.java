import java.io.*;
import java.util.*;
 
public class B {
    public static class Vertex {
        int index;
        int weight;
 
        public Vertex(int index, int weight) {
            this.weight = weight;
            this.index = index;
        }
 
        @SuppressWarnings("unused")
        public int getIndex() {
            return index;
        }
 
        @SuppressWarnings("unused")
        public int getWeight() {
            return weight;
        }
 
        @SuppressWarnings("unused")
        public void setIndex(int index) {
            this.index = index;
        }
 
        @SuppressWarnings("unused")
        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
 
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
 
    public static boolean dfs1(int u, ArrayList<ArrayList<Integer>> edges, int[] matching, boolean[] used) {
        if (used[u]) {
            return false;
        }
        used[u] = true;
        for (int v : edges.get(u)) {
            if (matching[v] == -1 || dfs1(matching[v], edges, matching, used)) {
                matching[v] = u;
                return true;
            }
        }
        return false;
    }
 
    public static void dfs2(int u, boolean direction, ArrayList<Pair> edges, TreeSet<Integer> match,
                            int[] fromTrans, int[] toTrans,
                            boolean[] fromUsed, boolean[] toUsed) {
        if (fromUsed[u]) {
            return;
        }
        fromUsed[u] = true;
        int to = fromTrans[u];
        if (direction) {
            edges.add(new Pair(u, to));
        } else
            edges.add(new Pair(to, u));
        toUsed[to] = true;
        if (toTrans[to] != -1 && match.contains(toTrans[to])) {
            dfs2(toTrans[to], direction, edges, match, fromTrans, toTrans, fromUsed, toUsed);
        }
    }
 
    public static void maxMatching(int[] matching, ArrayList<ArrayList<Integer>> edges, ArrayList<Vertex> vert, boolean[] used) {
        vert.sort(Comparator.comparing(p -> (p.weight)));
        Collections.reverse(vert);
        for (Vertex vertex : vert) {
            if (dfs1(vertex.getIndex(), edges, matching, used)) {
                used = new boolean[used.length];
            }
        }
    }
 
    public static void fillMatch(TreeSet<Integer> leftMatch, int[] matching, int[] fromDeg, int[] toDeg) {
        for (int j = 0; j < matching.length; j++) {
            if (matching[j] == -1) {
                continue;
            }
            toDeg[j]++;
            fromDeg[matching[j]]++;
            leftMatch.add(matching[j]);
        }
    }
 
    public static void fillTrans(int[] trans, int[] matching) {
        for (int i = 0; i < matching.length; i++) {
            if (matching[i] == -1) {
                continue;
            }
            trans[matching[i]] = i;
        }
    }
 
    public static InputReader in = new InputReader(System.in);
    public static ArrayList<Pair> edges;
    public static HashMap<Pair, Integer> num;
    public static int n;
    public static int m;
    public static boolean[] leftUsed;
    public static boolean[] rightUsed;
    public static int[] leftDeg;
    public static int[] rightDeg;
    public static TreeSet<Integer> leftMatch;
    public static TreeSet<Integer> rightMatch;
    public static int[] leftTrans;
    public static int[] rightTrans;
    public static ArrayList<Vertex> leftVertex;
    public static ArrayList<Vertex> rightVertex;
    public static int[] rightWeight;
    public static int[] leftWeight;
    public static ArrayList<ArrayList<Integer>> leftEdge;
    public static ArrayList<ArrayList<Integer>> rightEdge;
    public static boolean[] used;
    public static int[] leftMatching;
    public static int[] rightMatching;
 
    public static void prepare() {
        edges = new ArrayList<>();
        leftVertex = new ArrayList<>(n);
        rightVertex = new ArrayList<>(m);
        rightWeight = new int[m];
        leftWeight = new int[n];
        num = new HashMap<>();
        leftEdge = new ArrayList<>(n);
        rightEdge = new ArrayList<>(m);
        used = new boolean[n];
        leftMatching = new int[m];
        rightMatching = new int[n];
        leftDeg = new int[n];
        rightDeg = new int[m];
        leftMatch = new TreeSet<>();
        rightMatch = new TreeSet<>();
        leftUsed = new boolean[n];
        rightUsed = new boolean[m];
        leftTrans = new int[n];
        rightTrans = new int[m];
    }
 
    public static void generateInput() {
        n = in.nextInt();
        m = in.nextInt();
        int e = in.nextInt();
        prepare();
        for (int i = 0; i < n; i++) {
            leftWeight[i] = in.nextInt();
            leftVertex.add(new Vertex(i, leftWeight[i]));
            rightMatching[i] = -1;
            leftTrans[i] = -1;
            leftEdge.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            rightEdge.add(new ArrayList<>());
            rightWeight[i] = in.nextInt();
            rightVertex.add(new Vertex(i, rightWeight[i]));
            leftMatching[i] = -1;
            rightTrans[i] = -1;
        }
        for (int i = 0; i < e; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            num.put(new Pair(u - 1, v - 1), i + 1);
            leftEdge.get(u - 1).add(v - 1);
            rightEdge.get(v - 1).add(u - 1);
        }
        maxMatching(leftMatching, leftEdge, leftVertex, used);
        used = new boolean[m];
        maxMatching(rightMatching, rightEdge, rightVertex, used);
        fillMatch(leftMatch, leftMatching, leftDeg, rightDeg);
        fillMatch(rightMatch, rightMatching, rightDeg, leftDeg);
        fillTrans(leftTrans, leftMatching);
        fillTrans(rightTrans, rightMatching);
    }
 
    public static void generateOutput() {
        PrintWriter out = new PrintWriter(System.out);
        int result = 0;
        for (Pair pair : edges) {
            result += leftWeight[pair.getFirst()] + rightWeight[pair.getSecond()];
        }
        out.println(result);
        out.println(edges.size());
        for (Pair pair : edges) {
            out.print(num.get(pair) + " ");
        }
        out.close();
    }
 
    public static void main(String[] args) {
        generateInput();
        // paths from L
        for (int i = 0; i < n; i++) {
            if (leftDeg[i] == 1 && leftMatch.contains(i)) {
                dfs2(i, true, edges, leftMatch, leftTrans, rightTrans, leftUsed, rightUsed);
            }
        }
        // paths from R
        for (int i = 0; i < m; i++) {
            if (rightDeg[i] == 1 && rightMatch.contains(i)) {
                dfs2(i, false, edges, rightMatch, rightTrans, leftTrans, rightUsed, leftUsed);
            }
        }
        // cycles
        for (int i = 0; i < n; i++) {
            if (!leftUsed[i] && leftDeg[i] == 2) {
                dfs2(i, true, edges, leftMatch, leftTrans, rightTrans, leftUsed, rightUsed);
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