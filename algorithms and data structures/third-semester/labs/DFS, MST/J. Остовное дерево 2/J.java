import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
 
public class J {
 
    public static class Edge {
        int u;
        int v;
 
        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }
 
    public static class Pair {
        Edge first;
        int second;
 
        public Pair(Edge first, int second) {
            this.first = first;
            this.second = second;
        }
    }
 
    public static ArrayList<Pair> edges;
    public static int[] p;
 
    public static int f_nd(int a) {
        if (p[a] != a) {
            return p[a] = f_nd(p[a]);
        } else {
            return a;
        }
    }
 
    public static void j_in(int a, int b) {
        a = f_nd(a);
        b = f_nd(b);
        p[b] = a;
    }
 
    public static void main(String[] args) throws NullPointerException {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        edges = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            int w = in.nextInt();
            edges.add(new Pair(new Edge(u - 1, v - 1), w));
        }
 
        edges.sort(Comparator.comparingInt(o -> o.second));
 
        p = new int[n];
        for (int i = 0; i < n; ++i) {
            p[i] = i;
        }
        long ans = 0;
        for (Pair v : edges) {
            Edge cur_1 = v.first;
            if (f_nd(cur_1.u) == f_nd(cur_1.v)) {
                continue;
            }
            ans += v.second;
            j_in(cur_1.u, cur_1.v);
        }
        System.out.print(ans);
    }
}