import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class K {
    public static class Pair {
        int first;
        int second;
 
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
 
    public static ArrayList<ArrayList<Pair>> graph;
    public static boolean[] used;
    public static int[] grundy;
    public static int result = -1;
 
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        int n = in.nextInt();
        int r = in.nextInt();
        graph = new ArrayList<>();
        used = new boolean[n];
        grundy = new int[n];
        for (int i = 0; i < n; ++i) {
            graph.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            int u = in.nextInt();
            int v = in.nextInt();
            graph.get(u - 1).add(new Pair(v - 1, i));
            graph.get(v - 1).add(new Pair(u - 1, i));
        }
        getGrundyResult(r - 1);
        if (grundy[r - 1] == 0) {
            System.out.println(2);
        } else {
            used = new boolean[n];
            getEdgeResult(r - 1, 0);
            System.out.println(1);
            System.out.println(result);
        }
    }
 
    private static void getEdgeResult(int u, int need) {
        used[u] = true;
        for (Pair vNum : graph.get(u)) {
            if (used[vNum.first]) {
                continue;
            }
            int new_need = need ^ grundy[u] ^ (grundy[vNum.first] + 1);
            if (new_need == 0) {
                result = vNum.second;
                return;
            }
            getEdgeResult(vNum.first, new_need - 1);
            if (result != -1) {
                return;
            }
        }
    }
 
    public static void getGrundyResult(int u) {
        used[u] = true;
        int res = 0;
        for (Pair vNum : graph.get(u)) {
            if (!used[vNum.first]) {
                getGrundyResult(vNum.first);
                res ^= (grundy[vNum.first] + 1);
            }
        }
        grundy[u] = res;
    }
 
    public static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}