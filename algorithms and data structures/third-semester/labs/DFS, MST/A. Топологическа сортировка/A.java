import java.util.ArrayList;
import java.util.Scanner;
 
import static java.util.Collections.reverse;
 
public class A {
 
    public static ArrayList<ArrayList<Integer>> graph;
    public static int[] used;
    public static ArrayList<Integer> ans;
    public static boolean cycle;
 
    public static void dfs(int u) {
        if (used[u] == 2) {
            return;
        }
        used[u] = 1;
        for (int v : graph.get(u)) {
            if (used[v] == 1) {
                cycle = true;
                return;
            }
            dfs(v);
        }
        used[u] = 2;
        ans.add(u);
    }
 
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        graph = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<>());
        }
        used = new int[n];
        ans = new ArrayList<>();
        for (int i = 0; i < m; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph.get(u - 1).add(v - 1);
        }
        for (int i = 0; i < n; ++i) {
            dfs(i);
        }
        if (cycle) {
            System.out.print(-1);
        } else {
            reverse(ans);
            for (int u: ans) {
                System.out.print((u + 1) + " ");
            }
        }
    }
}