import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
 
public class E {
 
    public static ArrayList<ArrayList<Integer>> graph;
    public static int[] parent;
 
    public static void dfs(int u) {
        for (Integer v: graph.get(u)) {
            if (v != parent[u]) {
                parent[v] = u;
                dfs(v);
            }
        }
    }
 
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        graph = new ArrayList<>();
        for (int i = 0; i < n + 1; ++i) {
            graph.add(new ArrayList<>());
        }
        int[] children = new int[n + 1];
        parent = new int[n + 1];
        for (int i = 0; i < n - 1; ++i) {
            int u = input.nextInt();
            int v = input.nextInt();
            graph.get(u).add(v);
            graph.get(v).add(u);
            children[v]++;
            children[u]++;
        }
        dfs(graph.size() - 1);
        TreeSet<Integer> leaves = new TreeSet<>();
        for (int i = 0; i < children.length; ++i) {
            if (children[i] == 1) {
                leaves.add(i);
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        int it = 1;
        while (it < graph.size() - 2) {
            it++;
            int current = leaves.first();
            leaves.remove(leaves.first());
            int next_node = parent[current];
            children[next_node]--;
            result.add(next_node);
            if (children[next_node] == 1) {
                leaves.add(next_node);
            }
        }
        for (int u: result) {
            System.out.print(u + " ");
        }
    }
}