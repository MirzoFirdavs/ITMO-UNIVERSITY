import java.io.*;
import java.util.*;
 
public class A {
    public static class Pair {
        int first;
        int second;
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
 
    public static int[] p = new int[100000];
 
    public static int get(int v) {
        if (p[v] != v) {
            p[v] = get(p[v]);
        }
        return p[v];
    }
 
    public static void solve() throws FileNotFoundException {
        InputReader input = new InputReader(new FileInputStream("schedule.in"));
        for (int i = 0; i < 100000; ++i) {
            p[i] = i;
        }
        int n = input.nextInt();
        ArrayList<Pair> tasks = new ArrayList<>();
        long result = 0;
        for (int i = 0; i < n; ++i) {
            int a = input.nextInt();
            int b = input.nextInt();
            if (a < n) {
                tasks.add(new Pair(b, a));
            }
        }
        tasks.sort(Comparator.comparingInt(o -> o.first));
        Collections.reverse(tasks);
        while (!tasks.isEmpty()) {
            Pair cur = tasks.get(0);
            tasks.remove(0);
            if (get(cur.second) != 0) {
                int t = get(cur.second);
                p[t] = t - 1;
            } else {
                result += cur.first;
            }
        }
        PrintWriter out = new PrintWriter("schedule.out");
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