import java.util.Scanner;
 
public class H {
 
    public static int[][] g;
    public static int[][] rg;
    public static boolean[] used;
    public static int cnt = 0;
    public static int middle = 0;
    public static int n;
 
    public static void dfs(int[][] graph, int v) {
        used[v] = true;
        cnt++;
        for (int to = 0; to < n + 10; ++to) {
            if (graph[v][to] != -1 && !used[to] && graph[v][to] <= middle) {
                dfs(graph, to);
            }
        }
    }
 
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        g = new int[n + 10][n + 10];
        rg = new int[n + 10][n + 10];
 
        for (int i = 0; i < n + 10; ++i) {
            for (int j = 0; j < n + 10; ++j) {
                g[i][j] = -1;
                rg[i][j] = -1;
            }
        }
        used = new boolean[n + 10];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                int d = in.nextInt();
                g[i][j] = d;
            }
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                rg[i][j] = g[j][i];
            }
        }
        int left = -1;
        int right = (int) 2e9;
        while (right - left > 1) {
            middle = (left + right) / 2;
            cnt = 0;
            used = new boolean[n + 10];
            dfs(g, 0);
            if (cnt == n) {
                cnt = 0;
                used = new boolean[n + 10];
                dfs(rg, 0);
                if (cnt == n) {
                    right = middle;
                } else {
                    left = middle;
                }
            } else {
                left = middle;
            }
        }
        System.out.println(right);
    }
}