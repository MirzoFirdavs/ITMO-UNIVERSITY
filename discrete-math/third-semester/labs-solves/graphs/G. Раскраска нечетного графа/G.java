import java.util.*;

public class G {

    public static ArrayList<ArrayList<Integer>> graph;
    public static int[] was;
    public static boolean[] used;
    public static int[] colors;

    public static void dfs(int v, int mx) {
        used[v] = true;
        for (int i : graph.get(v)) {
            if (!used[i]) {
                for (int j = 0; j < mx; j++) {
                    was[j] = -1;
                }
                for (int j : graph.get(i)) {
                    if (colors[j] != -1) {
                        was[colors[j]] = 1;
                    }
                }
                int cur = -1;
                for (int j = 0; j < mx; j++) {
                    if (was[j] == -1) {
                        cur = j;
                        break;
                    }
                }
                colors[i] = cur;
                dfs(i, mx);
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        graph = new ArrayList<>();
        colors = new int[n];
        was = new int[n];
        used = new boolean[n];
        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            u--; v--;
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        int mx = 0;
        for (int i = 0; i < n; i++) {
            mx = Math.max(mx, graph.get(i).size());
            colors[i] = -1;
            was[i] = -1;
        }
        if (mx % 2 == 0) {
            mx++;
        }
        colors[0] = 0;
        dfs(0, mx);
        System.out.println(mx);
        for (int element: colors) {
            System.out.println(element + 1);
        }
    }
}