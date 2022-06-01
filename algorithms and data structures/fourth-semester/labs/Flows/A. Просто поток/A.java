import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class A {
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
    public static int[] used;
    public static int s, t;
    public static final int INF = Integer.MAX_VALUE;

    public static boolean bfs() {
        d = new int[n];
        for (int i = 0; i < n; ++i) {
            d[i] = -1;
        }
        d[s] = 0;
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.push(s);
        while (!queue.isEmpty()) {
            int u = queue.getFirst();
            queue.pop();
            for (int e : graph.get(u)) {
                Edge edge = edges.get(e);
                if (edge.getFlow() < edge.getCapacity() && d[edge.getV()] == -1) {
                    d[edge.getV()] = d[u] + 1;
                    queue.push(edge.getV());
                }
            }
        }
        return d[t] != -1;
    }

    public static int dfs(int u, int minCapacity) {
        if (u == t || minCapacity == 0) {
            return minCapacity;
        }
        for (; used[u] < graph.get(u).size(); used[u]++) {
            int e = graph.get(u).get(used[u]);
            Edge edge = edges.get(e);
            if (d[edge.getV()] != d[u] + 1) {
                continue;
            }
            int pushed = dfs(edge.getV(), Math.min(minCapacity, edge.getCapacity() - edge.getFlow()));
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
            used = new int[n];
            int currenFlow = dfs(s, INF);
            while (currenFlow != 0) {
                result += currenFlow;
                currenFlow = dfs(s, INF);
            }
        }
        return result;
    }

    public static void generateInput() {
        InputReader inputReader = new InputReader(System.in);
        int aI, bI, cI;
        n = inputReader.nextInt();
        m = inputReader.nextInt();
        s = 0;
        t = n - 1;
        graph = new ArrayList<>(n);
        edges = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<>());
        }
        d = new int[n];
        used = new int[n];
        for (int i = 0; i < m; ++i) {
            aI = inputReader.nextInt();
            bI = inputReader.nextInt();
            cI = inputReader.nextInt();
            aI--;
            bI--;
            graph.get(aI).add(edges.size());
            edges.add(new Edge(aI, bI, 0, cI));
            graph.get(bI).add(edges.size());
            edges.add(new Edge(bI, aI, 0, cI));
        }
    }

    public static void generateOutput() {
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.println(maxFlow());
        for (int i = 0; i < 2 * m; i += 2) {
            printWriter.println(edges.get(i).getFlow());
        }
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