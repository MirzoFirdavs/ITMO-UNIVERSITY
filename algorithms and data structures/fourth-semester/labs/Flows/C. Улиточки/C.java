import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class C {
    public static class Edge {
        int u;
        int v;
        int flow;
        int capacity;

        public Edge(int u, int v, int f, int c) {
            this.u = u;
            this.v = v;
            this.flow = f;
            this.capacity = c;
        }

        @SuppressWarnings("unused")
        public int getU() {
            return u;
        }

        @SuppressWarnings("unused")
        public int getV() {
            return v;
        }

        @SuppressWarnings("unused")
        public int getFlow() {
            return flow;
        }

        @SuppressWarnings("unused")
        public int getCapacity() {
            return capacity;
        }

        @SuppressWarnings("unused")
        public void setU(int newU) {
            this.u = newU;
        }

        @SuppressWarnings("unused")
        public void setV(int newV) {
            this.v = newV;
        }

        @SuppressWarnings("unused")
        public void setFlow(int newF) {
            this.flow = newF;
        }

        @SuppressWarnings("unused")
        public void setCapacity(int newC) {
            this.capacity = newC;
        }
    }

    public static int n;
    public static int m;
    public static ArrayList<Edge> edges;
    public static ArrayList<ArrayList<Integer>> graph;
    public static int[] d;
    public static int[] p;
    public static boolean[] used;
    public static ArrayList<Integer> path;
    public static int s, t;
    public static final int INF = Integer.MAX_VALUE;

    public static boolean bfs() {
        d = new int[n];
        for (int i = 0; i < n; ++i) {
            d[i] = INF;
        }
        d[s] = 0;
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.push(s);
        while (!queue.isEmpty()) {
            int u = queue.getFirst();
            queue.pop();
            for (int e : graph.get(u)) {
                Edge edge = edges.get(e);
                if (edge.getFlow() < edge.getCapacity() && d[edge.getV()] == INF) {
                    d[edge.getV()] = d[u] + 1;
                    queue.push(edge.getV());
                }
            }
        }
        return d[t] != INF;
    }


    public static void dfs1(int u) {
        path.add(u);
        if (u == t) {
            return;
        }
        for (int e : graph.get(u)) {
            int v = edges.get(e).getV();
            if (!used[e] && edges.get(e).getFlow() == 1) {
                used[e] = true;
                dfs1(v);
                break;
            }
        }
    }

    public static int dfs2(int u, int minCapacity) {
        if (u == t || minCapacity == 0) {
            return minCapacity;
        }
        for (; p[u] < graph.get(u).size(); p[u]++) {
            int e = graph.get(u).get(p[u]);
            Edge edge = edges.get(e);
            if (d[edge.getV()] != d[u] + 1) {
                continue;
            }
            int pushed = dfs2(edge.getV(), Math.min(minCapacity, edge.getCapacity() - edge.getFlow()));
            if (pushed != 0) {
                edges.get(e).setFlow(edges.get(e).getFlow() + pushed);
                edges.get(e ^ 1).setFlow(edges.get(e ^ 1).getFlow() - pushed);
                return pushed;
            }
        }
        return 0;
    }

    public static int maxFlow() {
        int result = 0;
        while (bfs()) {
            p = new int[n];
            int currenFlow = dfs2(s, INF);
            while (currenFlow != 0) {
                result += currenFlow;
                currenFlow = dfs2(s, INF);
            }
        }
        return result;
    }

    public static void generateInput() {
        InputReader inputReader = new InputReader(System.in);
        int aI, bI;
        n = inputReader.nextInt();
        m = inputReader.nextInt();
        s = inputReader.nextInt();
        t = inputReader.nextInt();
        s--;
        t--;
        graph = new ArrayList<>(n);
        edges = new ArrayList<>();
        path = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<>());
        }
        d = new int[n];
        p = new int[n];
        for (int i = 0; i < m; ++i) {
            aI = inputReader.nextInt();
            bI = inputReader.nextInt();
            aI--;
            bI--;
            graph.get(aI).add(edges.size());
            edges.add(new Edge(aI, bI, 0, 1));
            graph.get(bI).add(edges.size());
            edges.add(new Edge(bI, aI, 0, 0));
        }
    }

    public static void generateOutput() {
        PrintWriter printWriter = new PrintWriter(System.out);
        int result = maxFlow();
        if (result < 2) {
            printWriter.println("NO");
            printWriter.close();
            return;
        }
        printWriter.println("YES");
        used = new boolean[2 * m];
        dfs1(s);
        for (int e : path) {
            printWriter.print((e + 1) + " ");
        }
        printWriter.println();
        path.clear();
        dfs1(s);
        for (int e : path) {
            printWriter.print((e + 1) + " ");
        }
        printWriter.println();
        path.clear();
        printWriter.close();
    }

    public static void main(String[] args) {
        generateInput();
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