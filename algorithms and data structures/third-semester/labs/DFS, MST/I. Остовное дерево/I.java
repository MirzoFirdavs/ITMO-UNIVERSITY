import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
public class I {
 
    public static class Pair {
        int first;
        int second;
 
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
 
    public static int dist(Pair v1, Pair v2) {
        return (v1.first - v2.first) * (v1.first - v2.first) + (v1.second - v2.second) * (v1.second - v2.second);
    }
 
    public static void main(String[] args) throws NullPointerException {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<Pair> vert = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            int u = in.nextInt();
            int v = in.nextInt();
            vert.add(new Pair(u, v));
        }
        boolean[] used = new boolean[n];
        int[] m_n = new int[n];
        int[] end = new int[n];
        for (int i = 0; i < n; ++i) {
            m_n[i] = Integer.MAX_VALUE;
            end[i] = -1;
        }
        m_n[0] = 0;
 
        double ans = 0;
        for (int i = 0; i < n; i++) {
            int u = -1;
            for (int j = 0; j < n; j++) {
                if (!used[j] && (u == -1 || m_n[j] < m_n[u])) {
                    u = j;
                }
            }
            used[u] = true;
            ans += u == 0 ? 0 : Math.sqrt(dist(vert.get(u), vert.get(end[u])));
            for (int v = 0; v < n; v++) {
                if (dist(vert.get(u), vert.get(v)) < m_n[v]) {
                    m_n[v] = dist(vert.get(u), vert.get(v));
                    end[v] = u;
                }
            }
        }
        System.out.print(ans);
    }
}