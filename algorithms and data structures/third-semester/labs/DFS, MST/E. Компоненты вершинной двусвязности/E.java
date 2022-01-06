import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;
 
public class E {
 
    public static class Pair {
        int first;
        int second;
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
 
    public static Scanner in = new Scanner(System.in);
    public static int n;
    public static int m;
    public static ArrayList<ArrayList<Pair>> graph;
    public static ArrayDeque<Integer> queue;
    public static int[] color;
    public static int[] tIn;
    public static int[] up;
    public static int[] component;
    public static boolean[] used;
    public static int t = 0;
    public static int current = 0;
 
    public static void dfs(int v, int p) {
        color[v] = 1;
        tIn[v] = t++;
        up[v] = tIn[v];
        for (int i = 0; i < graph.get(v).size(); i++) {
            if (!used[graph.get(v).get(i).second]) {
                queue.addFirst(graph.get(v).get(i).second);
                used[graph.get(v).get(i).second] = true;
            }
            if (graph.get(v).get(i).second == p)
                continue;
            if (color[graph.get(v).get(i).first] == 1) {
                up[v] = Math.min(up[v], tIn[graph.get(v).get(i).first]);
            }
            if (color[graph.get(v).get(i).first] == 0) {
                dfs(graph.get(v).get(i).first, graph.get(v).get(i).second);
                up[v] = Math.min(up[v], up[graph.get(v).get(i).first]);
                if (up[graph.get(v).get(i).first] >= tIn[v]) {
                    while (queue.getFirst() != graph.get(v).get(i).second) {
                        component[queue.getFirst()] = current;
                        queue.removeFirst();
                    }
                    component[queue.getFirst()] = current;
                    queue.removeFirst();
                    current++;
                }
            }
        }
        color[v] = 2;
    }
 
    public static void generation() {
        graph = new ArrayList<>();
        queue = new ArrayDeque<>();
        color = new int[n];
        tIn = new int[n];
        up = new int[n];
        component = new int[m];
        used = new boolean[m];
        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph.get(u - 1).add(new Pair(v - 1, i));
            graph.get(v - 1).add(new Pair(u - 1, i));
        }
        for (int i = 0; i < n; i++) {
            if (color[i] == 0) {
                dfs(i, -1);
            }
        }
    }
 
    public static void main(String[] args) {
        n = in.nextInt();
        m = in.nextInt();
        generation();
        System.out.println(current);
        for (int i: component) {
            System.out.print((i + 1) + " ");
        }
    }
}