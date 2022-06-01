import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.StringTokenizer;
 
public class C {
    public static class Edge {
        int from;
        int to;
        long flow;
        int capacity;
        int weight;
 
        public Edge(int from, int to, long flow, int capacity, int weight) {
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
        public int getCapacity() {
            return capacity;
        }
 
        @SuppressWarnings("unused")
        public int getWeight() {
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
        public void setCapacity(int newCapacity) {
            this.capacity = newCapacity;
        }
 
        @SuppressWarnings("unused")
        public void setWeight(int newWeight) {
            this.weight = newWeight;
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
 
    private static final int INF = Integer.MAX_VALUE;
    public static ArrayList<ArrayList<Integer>> graph;
    public static ArrayList<Edge> edges;
 
    public static Pair getFirst(HashSet<Pair> set) {
        Pair result = null;
 
        for (Pair pair : set) {
            result = pair;
            break;
        }
 
        return result;
    }
 
    public static boolean bfs(int s, int t, int[] p) {
        int n = graph.size();
        int[] d = new int[n];
 
        for (int i = 0; i < n; ++i) {
            d[i] = INF;
        }
 
        HashSet<Pair> relax = new HashSet<>();
        relax.add(new Pair(s, 0));
        d[s] = 0;
 
        while (!relax.isEmpty()) {
            Pair distU = getFirst(relax);
            relax.remove(distU);
            for (int element : graph.get(distU.getFirst())) {
                Edge edge = edges.get(element);
                if (d[edge.getTo()] <= d[edge.getFrom()] + edge.getWeight() || edge.getCapacity() - edge.getFlow() == 0) {
                    continue;
                }
                if (d[edge.getTo()] != INF) {
                    relax.remove(new Pair(edge.getTo(), d[edge.getTo()]));
                }
                d[edge.getTo()] = d[edge.getFrom()] + edge.getWeight();
                p[edge.getTo()] = element;
                relax.add(new Pair(edge.getTo(), d[edge.getTo()]));
            }
        }
 
        return d[t] != INF;
    }
 
    public static long getMinPath(int s, int v, int[] p) {
        if (v == s) {
            return INF;
        }
 
        Edge edge = edges.get(p[v]);
 
        return Math.min(edge.getCapacity() - edge.getFlow(), getMinPath(s, edge.getFrom(), p));
    }
 
    public static long getMinCost(int s, int t) {
        int[] p = new int[graph.size()];
        long result = 0;
 
        while (bfs(s, t, p)) {
            long curFlow = getMinPath(s, t, p);
            for (int cur = t; cur != s; cur = edges.get(p[cur]).getFrom()) {
                int e = p[cur];
                edges.get(e).setFlow(edges.get(e).getFlow() + curFlow);
                if (e % 2 == 0) {
                    edges.get(e + 1).setFlow(edges.get(e + 1).getFlow() - curFlow);
                } else {
                    edges.get(e - 1).setFlow(edges.get(e - 1).getFlow() - curFlow);
                }
                result += curFlow * edges.get(e).getWeight();
            }
        }
 
        return result;
    }
 
    public static void addEdges(int from, int to, int capacity, int weight) {
        graph.get(from).add(edges.size());
        edges.add(new Edge(from, to, 0, capacity, weight));
        graph.get(to).add(edges.size());
        edges.add(new Edge(to, from, 0, 0, -weight));
    }
 
    public static void generateInput() {
        InputReader inputReader = new InputReader(System.in);
 
        graph = new ArrayList<>();
        edges = new ArrayList<>();
 
        for (int i = 0; i < 8; ++i) {
            graph.add(new ArrayList<>());
        }
 
        int r1 = inputReader.nextInt();
        int s1 = inputReader.nextInt();
        int t1 = inputReader.nextInt();
        int r2 = inputReader.nextInt();
        int s2 = inputReader.nextInt();
        int t2 = inputReader.nextInt();
 
        addEdges(1, 4, INF, 0);
        addEdges(1, 5, INF, 1);
        addEdges(1, 6, INF, 0);
        addEdges(2, 4, INF, 0);
        addEdges(2, 5, INF, 0);
        addEdges(2, 6, INF, 1);
        addEdges(3, 4, INF, 1);
        addEdges(3, 5, INF, 0);
        addEdges(3, 6, INF, 0);
        addEdges(0, 1, r1, 0);
        addEdges(0, 2, s1, 0);
        addEdges(0, 3, t1, 0);
        addEdges(4, 7, r2, 0);
        addEdges(5, 7, s2, 0);
        addEdges(6, 7, t2, 0);
 
    }
 
    public static void generateOutput() {
        PrintWriter printWriter = new PrintWriter(System.out);
        printWriter.println(getMinCost(0, 7));
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