import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
 
public class G {
 
    public static Scanner in;
    public static int n;
    public static int m;
    public static HashMap<String, Integer> num;
    public static HashMap<Integer, String> r_num;
    public static int[] component;
    public static ArrayList<Integer> p;
    public static boolean[] used;
    public static ArrayList<ArrayList<Integer>> g1;
    public static ArrayList<ArrayList<Integer>> g2;
 
    public static int geT(String currentName) {
        char path = currentName.charAt(0);
        StringBuilder name = new StringBuilder();
        for (int i = 1; i < currentName.length(); ++i) {
            name.append(currentName.charAt(i));
        }
        if (path == '+') {
            return num.get(name.toString());
        } else {
            return num.get(name.toString()) + n;
        }
    }
 
    public static void topSort(int u) {
        if (used[u]) {
            return;
        }
        used[u] = true;
        for (int v : g2.get(u)) {
            topSort(v);
        }
        p.add(u);
    }
 
    public static void dfs(int u, int c) {
        if (component[u] != -1) {
            return;
        }
        component[u] = c;
        for (int v : g1.get(u)) {
            dfs(v, c);
        }
    }
 
    public static void generation() {
        num = new HashMap<>();
        r_num = new HashMap<>();
        for (int i = 0; i < n; ++i) {
            String name = in.next();
            num.put(name, i);
            r_num.put(i, name);
        }
        g1 = new ArrayList<>();
        g2 = new ArrayList<>();
        p = new ArrayList<>();
        component = new int[2 * n];
        for (int i = 0; i < 2 * n; ++i) {
            component[i] = -1;
        }
        used = new boolean[2 * n];
        for (int i = 0; i < 2 * n; ++i) {
            g1.add(new ArrayList<>());
            g2.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            String u = in.next();
            String arrow = in.next();
            String v = in.next();
            int x = geT(u);
            int y = geT(v);
            int nX = (x + n) % (2 * n);
            int nY = (y + n) % (2 * n);
            g1.get(y).add(x);
            g1.get(nX).add(nY);
            g2.get(x).add(y);
            g2.get(nY).add(nX);
        }
        for (int i = 0; i < 2 * n; i++) {
            topSort(i);
        }
        int cnt = 1;
        for (int i = 2 * n - 1; i >= 0; i--) {
            int v = p.get(i);
            if (component[v] != -1) {
                continue;
            }
            dfs(v, cnt++);
        }
    }
 
    public static void main(String[] args) {
        in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        generation();
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            if (component[i] == component[i + n]) {
                flag = true;
                break;
            }
        }
        if (flag) {
            System.out.println(-1);
        } else {
            ArrayList<Integer> result = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (component[i] > component[i + n]) {
                    result.add(i);
                }
            }
            System.out.println(result.size());
            for (int element: result) {
                System.out.println(r_num.get(element));
            }
        }
    }
}