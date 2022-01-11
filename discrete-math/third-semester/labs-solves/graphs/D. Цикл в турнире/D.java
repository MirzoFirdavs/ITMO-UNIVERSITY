import java.util.ArrayList;
import java.util.Scanner;
 
public class D {
 
    public static ArrayList<ArrayList<Integer>> graph;
    public static boolean[] used;
    public static ArrayList<Integer> order;
 
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        used = new boolean[n];
        graph = new ArrayList<>();
        order = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<>());
        }
        for (int i = 1; i < n; ++i) {
            String st = in.next();
            for (int j = 0; j < i; ++j) {
                if (st.charAt(j) == '1') {
                    graph.get(i).add(j);
                } else {
                    graph.get(j).add(i);
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            used = new boolean[n];
            dfs(i);
            order = r_v_rs_(order);
            if (f_nd(order.get(0), graph.get(order.get(order.size() - 1)))) {
                for (int v: order) {
                    System.out.print((v + 1) + " ");
                }
                break;
            }
            order.clear();
        }
    }
 
    public static ArrayList<Integer> r_v_rs_(ArrayList<Integer> order) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = order.size() - 1; i >= 0; --i) {
            result.add(order.get(i));
        }
        return result;
    }
 
    public static boolean f_nd(int e, ArrayList<Integer> u) {
        for (int v: u) {
            if (e == v) {
                return true;
            }
        }
        return false;
    }
 
    public static void dfs(int v) {
        used[v] = true;
        for (int to: graph.get(v)) {
            if (!used[to]) {
                dfs(to);
            }
        }
        order.add(v);
    }
}