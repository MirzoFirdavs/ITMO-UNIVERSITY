import java.io.*;
import java.util.*;

public class E {
    public static class Edge {
        int u;
        int v;
        int flow;
        int capacity;
        int i;
        int j;

        public Edge(int u, int v, int flow, int capacity, int i, int j) {
            this.u = u;
            this.v = v;
            this.flow = flow;
            this.capacity = capacity;
            this.i = i;
            this.j = j;
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
        public int getI() {
            return i;
        }

        @SuppressWarnings("unused")
        public int getJ() {
            return j;
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

        @SuppressWarnings("unused")
        public void setI(int newI) {
            this.i = newI;
        }

        @SuppressWarnings("unused")
        public void setJ(int newJ) {
            this.j = newJ;
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
        public void setFirst(int newFirst) {
            this.first = newFirst;
        }

        @SuppressWarnings("unused")
        public void setSecond(int newSecond) {
            this.second = newSecond;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Pair)) {
                return false;
            }
            Pair pair = (Pair) o;
            return first == pair.first && second == pair.second;
        }

        @Override
        public int hashCode() {
            int result = first;
            result = 31 * result + second;
            return result;
        }
    }

    public static final int INF = 10000;
    public static ArrayList<Edge> edges;
    public static ArrayList<ArrayList<Integer>> graph;
    public static ArrayList<ArrayList<String>> table;
    public static int[] d;
    public static int[] p;
    public static boolean[] used;
    public static int s;
    public static int t;
    public static int m;
    public static int n;
    public static int count = 0;
    public static HashMap<Pair, Integer> in;
    public static HashMap<Pair, Integer> out;
    public static ArrayList<Pair> go;

    public static void bfs1() {
        used[s] = true;
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.add(s);
        while (!queue.isEmpty()) {
            int u = queue.getFirst();
            queue.pop();
            for (int e : graph.get(u)) {
                Edge edge = edges.get(e);
                if (edge.getFlow() < edge.getCapacity() && !used[edge.getV()]) {
                    used[edge.getV()] = true;
                    queue.push(edge.getV());
                }
            }
        }
    }

    public static boolean bfs2() {
        d = new int[INF];
        for (int i = 0; i < INF; ++i) {
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
        while (bfs2()) {
            p = new int[INF];
            int currentFlow = dfs(s, INF);
            while (currentFlow != 0) {
                result += currentFlow;
                currentFlow = dfs(s, INF);
            }
        }
        return result;
    }

    public static void initialization() {
        in = new HashMap<>();
        out = new HashMap<>();
        table = new ArrayList<>();
        edges = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            table.add(new ArrayList<>());
            for (int j = 0; j < m; ++j) {
                table.get(i).add("0");
            }
        }
        graph = new ArrayList<>();
        for (int i = 0; i < INF; ++i) {
            graph.add(new ArrayList<>());
        }
        p = new int[INF];
        d = new int[INF];
        used = new boolean[INF];
        go = new ArrayList<>(
                List.of(new Pair(-1, 0),
                        new Pair(0, -1),
                        new Pair(1, 0),
                        new Pair(0, 1)
                ));
    }

    public static boolean correct(int x, int y) {
        return 0 <= x && x < n && 0 <= y && y < m;
    }

    public static void generateInput() {
        InputReader inputReader = new InputReader(System.in);
        n = inputReader.nextInt();
        m = inputReader.nextInt();
        initialization();
        for (int i = 0; i < n; ++i) {
            String s = inputReader.next();
            for (int j = 0; j < m; ++j) {
                table.get(i).set(j, String.valueOf(s.charAt(j)));
                in.put(new Pair(i, j), count++);
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                out.put(new Pair(i, j), count++);
            }
        }
    }

    @SuppressWarnings("unused")
    public static void printMap(HashMap<Pair, Integer> mp) {
        for (Map.Entry<Pair, Integer> entry : mp.entrySet()) {
            System.out.println(entry.getKey().getFirst() + " " + entry.getKey().getSecond() + " val: " + entry.getValue());
        }
    }

    public static void generateOutput() {
        PrintWriter printWriter = new PrintWriter(System.out);
        int mxFlow = maxFlow();
        bfs1();
        if (mxFlow >= INF) {
            printWriter.println(-1);
            printWriter.close();
            return;
        }
        printWriter.println(mxFlow);
        for (Edge edge : edges) {
            if (edge.getCapacity() == 1 && edge.getFlow() == 1 && used[edge.getU()] && !used[edge.getV()]) {
                table.get(edge.getI()).set(edge.getJ(), "+");
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                printWriter.print(table.get(i).get(j));
            }
            printWriter.println();
        }
        printWriter.close();
    }

    public static void main(String[] args) {
        generateInput();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                int currentCapacity = INF;
                Pair pr = new Pair(i, j);
                String c = table.get(i).get(j);
                if (c.equals(".")) {
                    currentCapacity = 1;
                }
                if (c.equals("#")) {
                    currentCapacity = 0;
                }
                if (c.equals("A")) {
                    s = out.get(pr);
                }
                if (c.equals("B")) {
                    t = in.get(pr);
                }
                graph.get(in.get(pr)).add(edges.size());
                edges.add(new Edge(in.get(pr), out.get(pr), 0, currentCapacity, i, j));
                graph.get(out.get(pr)).add(edges.size());
                edges.add(new Edge(out.get(pr), in.get(pr), 0, 0, i, j));
                for (Pair pair : go) {
                    int x = i + pair.getFirst();
                    int y = j + pair.getSecond();
                    if (correct(x, y)) {
                        graph.get(out.get(pr)).add(edges.size());
                        edges.add(new Edge(out.get(pr), in.get(new Pair(x, y)), 0, INF, i, j));
                        graph.get(in.get(new Pair(x, y))).add(edges.size());
                        edges.add(new Edge(in.get(new Pair(x, y)), out.get(pr), 0, 0, i, j));
                    }
                }
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

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}