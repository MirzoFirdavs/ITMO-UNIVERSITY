import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class D {
    public static class Edge {
        int from;
        int to;
        long flow;
        long capacity;
        long weight;
 
        int reversed = -1;
 
        public Edge(int from, int to, long flow, long capacity, long weight) {
            this.from = from;
            this.to = to;
            this.flow = flow;
            this.capacity = capacity;
            this.weight = weight;
        }
 
        @SuppressWarnings("unused")
        public int getFrom() {
            return from;
        }
 
        @SuppressWarnings("unused")
        public int getTo() {
            return to;
        }
 
        @SuppressWarnings("unused")
        public long getFlow() {
            return flow;
        }
 
        @SuppressWarnings("unused")
        public long getCapacity() {
            return capacity;
        }
 
        @SuppressWarnings("unused")
        public long getWeight() {
            return weight;
        }
 
        @SuppressWarnings("unused")
        public void setFrom(int newFrom) {
            this.from = newFrom;
        }
 
        @SuppressWarnings("unused")
        public void setTo(int newTo) {
            this.to = newTo;
        }
 
        @SuppressWarnings("unused")
        public void setFlow(long newFlow) {
            this.flow = newFlow;
        }
 
        @SuppressWarnings("unused")
        public void setCapacity(long newCapacity) {
            this.capacity = newCapacity;
        }
 
        @SuppressWarnings("unused")
        public void setWeight(long newWeight) {
            this.weight = newWeight;
        }
    }
 
    public static int n, m, nullSource, nullSink;
    private static final long INF = Long.MAX_VALUE;
    public static ArrayList<ArrayList<Edge>> edges;
    public static long[] d;
    public static int[] id;
    public static Edge[] p;
 
    public static void addEdge(int from, int to, long flow, long capacity, long weight) {
        Edge edge = new Edge(from, to, flow, capacity, weight);
        Edge rev = new Edge(to, from, flow, 0, -weight);
 
        edges.get(from).add(edge);
        edges.get(to).add(rev);
 
        edges.get(from).get(edges.get(from).size() - 1).reversed = edges.get(to).size() - 1;
        edges.get(to).get(edges.get(to).size() - 1).reversed = edges.get(from).size() - 1;
    }
 
    public static long func() {
        long minCost = 0;
 
        while (true) {
            ArrayDeque<Integer> q = new ArrayDeque<>();
            id = new int[n + 1];
            d = new long[n + 1];
            p = new Edge[n + 1];
 
            for (int i = 0; i < n + 1; ++i) {
                d[i] = INF;
            }
 
            d[0] = 0;
            q.add(0);
 
            while (!q.isEmpty()) {
                int u = q.getFirst();
                q.removeFirst();
                id[u] = 2;
 
                for (Edge edge : edges.get(u)) {
                    if (edge.getFlow() < edge.getCapacity() && d[edge.getTo()] > d[edge.getFrom()] + edge.getWeight()) {
                        d[edge.getTo()] = d[edge.getFrom()] + edge.getWeight();
 
                        if (id[edge.getTo()] == 0) {
                            q.add(edge.getTo());
                        } else if (id[edge.getTo()] == 2) {
                            q.push(edge.getTo());
                        }
 
                        id[edge.getTo()] = 1;
                        p[edge.getTo()] = edge;
                    }
                }
            }
 
            long delta = INF;
 
            if (d[nullSink] == INF) {
                break;
            } else {
                for (int u = nullSink; u != nullSource; u = p[u].getFrom()) {
                    Edge edge = p[u];
                    delta = Math.min(delta, edge.getCapacity() - edge.getFlow());
                }
 
                for (int u = nullSink; u != nullSource; u = p[u].getFrom()) {
                    Edge edge = p[u];
                    Edge reversed = edges.get(edge.getTo()).get(edge.reversed);
                    edge.setFlow(edge.getFlow() + delta);
                    reversed.setFlow(reversed.getFlow() - delta);
                    minCost += delta * edge.getWeight();
                }
            }
        }
 
        return minCost;
    }
 
 
    public static void generateInput() {
        InputReader inputReader = new InputReader(System.in);
 
        n = inputReader.nextInt();
        m = inputReader.nextInt();
        edges = new ArrayList<>();
 
        long[] a = new long[n + 1];
 
        for (int i = 0; i < 2 * n + 2; ++i) {
            edges.add(new ArrayList<>());
        }
 
        for (int i = 1; i <= n; ++i) {
            a[i] = inputReader.nextLong();
            addEdge(n + i, i, 0, INF, a[i]);
            addEdge(0, n + i, 0, 1, 0);
            addEdge(i, 2 * n + 1, 0, 1, 0);
            addEdge(i, n + i, 0, INF, 0);
        }
 
        for (int i = 0; i < m; ++i) {
            int from = inputReader.nextInt();
            int to = inputReader.nextInt();
            long weight = inputReader.nextLong();
            addEdge(n + from, to, 0, INF, weight);
        }
 
        nullSource = 0;
        nullSink = 2 * n + 1;
        n = 2 * n + 2;
    }
 
    public static void generateOutput() {
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.println(func());
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
 
        @SuppressWarnings("unused")
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
        @SuppressWarnings("unused")
        public long nextLong() {
            return Long.parseLong(next());
        }
    }
}