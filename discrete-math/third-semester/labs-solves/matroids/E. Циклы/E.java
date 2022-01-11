import java.io.*;
import java.util.*;
 
public class E {
    public static class Pair {
        int first;
        int second;
 
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
 
    public static boolean[] used;
    public static ArrayList<Pair> edges = new ArrayList<>();
    public static ArrayDeque<Integer> queue = new ArrayDeque<>();
 
    public static void solve() throws FileNotFoundException {
        InputReader input = new InputReader(new FileInputStream("cycles.in"));
        //InputReader input = new InputReader(System.in);
        int n = input.nextInt();
        int m = input.nextInt();
        used = new boolean[1 << n];
        for (int i = 0; i < n; ++i) {
            edges.add(new Pair(-1, -1));
        }
        for (int i = 0; i < n; ++i) {
            int w = input.nextInt();
            edges.set(i, new Pair(w, i));
        }
        for (int i = 0; i < m; ++i) {
            int cur = 0;
            int s = input.nextInt();
            for (int j = 0; j < s; ++j) {
                int k = input.nextInt();
                k--;
                cur = cur | (1 << k);
            }
            used[cur] = true;
            queue.add(cur);
        }
        while (!queue.isEmpty()) {
            int current = queue.getFirst();
            queue.removeFirst();
            for (int i = 0; i < n; ++i) {
                if ((current & (1 << i)) == 0) {
                    current = current | (1 << i);
                    if (!used[current]) {
                        used[current] = !used[current];
                        queue.add(current);
                    }
                    current &= ~(1 << i);
                }
            }
        }
        edges.sort(Comparator.comparingInt(o -> o.first));
        Collections.reverse(edges);
        int b = 0;
        long result = 0;
        for (int i = 0; i < n; ++i) {
            if (!used[b | (1 << edges.get(i).second)]) {
                b = b | (1 << edges.get(i).second);
                result += edges.get(i).first;
            }
        }
        PrintWriter out = new PrintWriter("cycles.out");
        out.println(result);
        out.close();
    }
 
    public static void main(String[] args) throws FileNotFoundException {
        solve();
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