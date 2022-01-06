import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;
 
public class F {
 
    public static boolean[] used;
    public static ArrayList<ArrayList<Integer>> graph_1;
    public static ArrayList<Integer> p;
    public static int[] comp;
    public static ArrayList<ArrayList<Integer>> graph_2;
    public static ArrayList<TreeSet<Integer>> condensation;
    public static int n;
 
    public static void topSort(int v) {
        if (used[v]) {
            return;
        }
        used[v] = true;
        for (int u : graph_1.get(v)) {
            topSort(u);
        }
        p.add(v);
    }
 
    public static void dfs(int v, int c) {
        if (comp[v] != -1) {
            return;
        }
        comp[v] = c;
        for (int u : graph_2.get(v)) {
            dfs(u, c);
        }
    }
 
    public static void generation() {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        int m = in.nextInt();
        graph_1 = new ArrayList<>();
        graph_2 = new ArrayList<>();
        used = new boolean[n];
        comp = new int[n];
        p = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            graph_1.add(new ArrayList<>());
            graph_2.add(new ArrayList<>());
            comp[i] = -1;
        }
        for (int i = 0; i < m; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph_1.get(u - 1).add(v - 1);
            graph_2.get(v - 1).add(u - 1);
        }
        for (int i = 0; i < n; i++) {
            topSort(i);
        }
    }
 
    public static void main(String[] args) throws NullPointerException {
        generation();
        int cnt = 1;
        for (int i = n - 1; i >= 0; i--) {
            int v = p.get(i);
            if (comp[v] != -1) {
                continue;
            }
            dfs(v, cnt);
            cnt++;
        }
        condensation = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            condensation.add(new TreeSet<>());
        }
        for (int u = 0; u < n; u++) {
            for (int v : graph_1.get(u)) {
                if (comp[u] != comp[v]) {
                    condensation.get(comp[u]).add(comp[v]);
                }
            }
        }
        int ans = 0;
        for (TreeSet<Integer> s :condensation){
            ans += s.size();
        }
        System.out.print(ans);
    }
}