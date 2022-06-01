import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class D {
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
        public void setFlow(int newFlow) {
            this.flow = newFlow;
        }

        @SuppressWarnings("unused")
        public void setCapacity(int newCapacity) {
            this.capacity = newCapacity;
        }
    }

    public static int n, s, t;
    public static ArrayList<ArrayList<Integer>> graph;
    public static ArrayList<ArrayList<String>> table;
    public static int[] d, p;
    public static ArrayList<Edge> edges;
    public static final int INF = Integer.MAX_VALUE;

    public static void add_edges(int u, int v, int c) {
        graph.get(u).add(edges.size());
        edges.add(new Edge(u, v, 0, c));
        graph.get(v).add(edges.size());
        edges.add(new Edge(v, u, 0, 0));
    }

    public static boolean bfs() {
        d = new int[n + 2];
        for (int i = 0; i < n + 2; ++i) {
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
        for (; p[u] < graph.get(u).size(); p[u]++) {
            int e = graph.get(u).get(p[u]);
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
            p = new int[n + 2];
            int currentFlow = dfs(s, INF);
            while (currentFlow != 0) {
                result += currentFlow;
                currentFlow = dfs(s, INF);
            }
        }
        return result;
    }

    public static void generateInput() {
        InputReader inputReader = new InputReader(System.in);
        n = inputReader.nextInt();
        graph = new ArrayList<>(n + 2);
        table = new ArrayList<>(n + 2);
        edges = new ArrayList<>();
        for (int i = 0; i < n + 2; ++i) {
            graph.add(new ArrayList<>());
            table.add(new ArrayList<>());
            for (int j = 0; j < n + 2; ++j) {
                table.get(i).add("0");
            }
        }
        d = new int[n + 2];
        p = new int[n + 2];
        s = n;
        t = n + 1;
        int[] score = new int[n];
        int[] maxScore = new int[n];
        for (int i = 0; i < n; ++i) {
            String s = inputReader.next();
            for (int j = 0; j < n; ++j) {
                char c = s.charAt(j);
                table.get(i).set(j, String.valueOf(c));
                if (c == 'W') {
                    score[i] += 3;
                }
                if (c == 'l') {
                    score[i] += 1;
                }
                if (c == 'w') {
                    score[i] += 2;
                }
                if (c == '.') {
                    if (i < j) {
                        add_edges(i, j, 3);
                        maxScore[i] += 3;
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            int k = inputReader.nextInt();
            add_edges(s, i, maxScore[i]);
            add_edges(i, t, k - score[i]);
        }
        @SuppressWarnings("unused")
        int x = maxFlow();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < graph.get(i).size(); ++j) {
                Edge edge = edges.get(graph.get(i).get(j));
                if (table.get(i).get(edge.getV()).equals(".") && i < edge.getV() && edge.getV() != s && edge.getV() != t) {
                    if (edge.getFlow() == 0) {
                        table.get(i).set(edge.getV(), "W");
                        table.get(edge.getV()).set(i, "L");
                    }
                    if (edge.getFlow() == 1) {
                        table.get(i).set(edge.getV(), "w");
                        table.get(edge.getV()).set(i, "l");
                    }
                    if (edge.getFlow() == 2) {
                        table.get(i).set(edge.getV(), "l");
                        table.get(edge.getV()).set(i, "w");
                    }
                    if (edge.getFlow() == 3) {
                        table.get(i).set(edge.getV(), "L");
                        table.get(edge.getV()).set(i, "W");
                    }
                }
            }
        }
    }

    public static void generateOutput() {
        PrintWriter printWriter = new PrintWriter(System.out);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                printWriter.print(table.get(i).get(j));
            }
            printWriter.println();
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