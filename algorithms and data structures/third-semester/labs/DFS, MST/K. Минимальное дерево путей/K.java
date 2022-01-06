import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
 
public class K {
 
    public static class Edge {
        int first;
        int second;
 
        public Edge(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
 
    public static class Pair {
        int[] first;
        int second;
 
        public Pair(int[] first, int second) {
            this.first = first;
            this.second = second;
        }
    }
 
    public static Scanner in = new Scanner(System.in);
    public static ArrayList<ArrayList<Edge>> mainGraph;
    public static ArrayList<Integer> p;
    public static int visited = 0;
    public static int n;
    public static int m;
 
    public static void dfs1(int u, boolean[] used, ArrayList<ArrayList<Edge>> graph) {
        if (used[u]) {
            return;
        }
        used[u] = true;
        visited++;
        for (Edge pair1 : graph.get(u)) {
            dfs1(pair1.first, used, graph);
        }
    }
 
    public static void dfs2(int u, boolean[] used, ArrayList<ArrayList<Integer>> graph) {
        if (used[u]) {
            return;
        }
        used[u] = true;
        visited++;
        for (int v : graph.get(u)) {
            dfs2(v, used, graph);
        }
    }
 
    public static void topSort(int u, boolean[] used, ArrayList<ArrayList<Integer>> graph) {
        if (used[u]) {
            return;
        }
        used[u] = true;
        for (int v : graph.get(u)) {
            topSort(v, used, graph);
        }
        p.add(u);
    }
 
    public static void dfs3(int u, int c, int[] component, ArrayList<ArrayList<Integer>> graph) {
        if (component[u] != -1) {
            return;
        }
        component[u] = c;
        for (int v : graph.get(u)) {
            dfs3(v, c, component, graph);
        }
    }
 
    public static Pair condense(ArrayList<ArrayList<Integer>> graph) {
        int len = graph.size();
        ArrayList<ArrayList<Integer>> tIn = new ArrayList<>();
        int[] component = new int[len];
        boolean[] used = new boolean[len];
        for (int i = 0; i < len; ++i) {
            tIn.add(new ArrayList<>());
            component[i] = -1;
        }
        for (int u = 0; u < len; u++) {
            for (int v : graph.get(u)) {
                tIn.get(v).add(u);
            }
        }
        p = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            topSort(i, used, graph);
        }
        int cnt = 0;
        for (int i = len - 1; i >= 0; i--) {
            int v = p.get(i);
            if (component[v] != -1) {
                continue;
            }
            dfs3(v, cnt, component, tIn);
            cnt++;
        }
        return new Pair(component, cnt);
    }
 
    public static long mst(ArrayList<ArrayList<Edge>> graph, int root) {
        long result = 0;
        int len = graph.size();
        int[] minEdge = new int[len];
        for (int i = 0; i < len; ++i) {
            minEdge[i] = Integer.MAX_VALUE;
        }
        for (ArrayList<Edge> vec : graph) {
            for (Edge pair : vec) {
                minEdge[pair.first] = Math.min(pair.second, minEdge[pair.first]);
            }
        }
        for (int v = 0; v < len; v++) {
            if (v == root) {
                continue;
            }
            result += minEdge[v];
        }
        ArrayList<ArrayList<Integer>> zeroGraph = new ArrayList<>();
        for (int i = 0; i < len; ++i) {
            zeroGraph.add(new ArrayList<>());
        }
        for (int u = 0; u < len; u++) {
            for (Edge pair : graph.get(u)) {
                if (pair.second == minEdge[pair.first]) {
                    zeroGraph.get(u).add(pair.first);
                }
            }
        }
        boolean[] used = new boolean[len];
        visited = 0;
        dfs2(root, used, zeroGraph);
        if (visited == len) {
            return result;
        }
        Pair pair = condense(zeroGraph);
        ArrayList<ArrayList<Edge>> newGraph = new ArrayList<>();
        for (int i = 0; i < pair.second; ++i) {
            newGraph.add(new ArrayList<>());
        }
        for (int u = 0; u < len; u++) {
            for (Edge vw : graph.get(u)) {
                if (pair.first[u] == pair.first[vw.first]) {
                    continue;
                }
                newGraph.get(pair.first[u]).add(new Edge(pair.first[vw.first], vw.second - minEdge[vw.first]));
            }
        }
        result += mst(newGraph, pair.first[root]);
        return result;
    }
 
    public static void generate() {
        mainGraph = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            mainGraph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            mainGraph.get(u - 1).add(new Edge(v - 1, w));
        }
        boolean[] used = new boolean[n];
        visited = 0;
        dfs1(0, used, mainGraph);
    }
 
    public static void main(String[] args) {
        n = in.nextInt();
        m = in.nextInt();
        generate();
        if (visited != n) {
            System.out.println("NO");
        } else {
            System.out.println("YES");
            System.out.print(mst(mainGraph, 0));
        }
    }
}