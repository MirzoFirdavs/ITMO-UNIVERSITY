import java.io.*;
import java.util.*;
 
public class I {
    public static class Pair {
        int first;
        int second;
 
        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
 
    public static class Node {
        int[] next = new int[26];
        int[] go = new int[26];
        int link = -1;
        int parent = -1;
        int parentChar = 0;
        ArrayList<Pair> terminals;
 
        public void fill(int[] a, int[] b) {
            for (int i = 0; i < a.length; ++i) {
                a[i] = -1;
                b[i] = -1;
            }
        }
 
        public Node(int link, int parent, int parentChar, ArrayList<Pair> terminals) {
            fill(next, go);
            this.link = link;
            this.parent = parent;
            this.parentChar = parentChar;
            this.terminals = terminals;
        }
 
        public Node(ArrayList<Pair> terminals) {
            this.terminals = terminals;
            fill(next, go);
        }
    }
 
    public static ArrayList<Node> tree = new ArrayList<>(List.of(new Node(new ArrayList<>())));
    public static int cnt = 0;
    public static int tSize = 1;
 
    public static void addString(String s) {
        int v = 0;
        for (int i = 0; i < s.length(); ++i) {
            int c = (s.charAt(i) - 'a');
            if (tree.get(v).next[c] == -1) {
                Node nd = new Node(-1, v, c, new ArrayList<>());
                tree.add(nd);
                tree.get(v).next[c] = tSize++;
            }
            v = tree.get(v).next[c];
        }
        tree.get(v).terminals.add(new Pair(cnt++, s.length()));
    }
 
    public static InputReader input = new InputReader(System.in);
 
    public static int getLink(int v) {
        if (tree.get(v).link == -1) {
            if (v == 0 || tree.get(v).parent == 0) {
                tree.get(v).link = 0;
            } else {
                tree.get(v).link = go(getLink(tree.get(v).parent), tree.get(v).parentChar);
            }
        }
        return tree.get(v).link;
    }
 
    public static int go(int v, int c) {
        if (tree.get(v).go[c] == -1) {
            if (tree.get(v).next[c] != -1) {
                tree.get(v).go[c] = tree.get(v).next[c];
            } else {
                tree.get(v).go[c] = v == 0 ? 0 : go(getLink(v), c);
            }
        }
        return tree.get(v).go[c];
    }
 
    public static void fill(Pair[] a, int len) {
        for (int i = 0; i < len; ++i) {
            a[i] = new Pair(-1, -1);
        }
    }
 
    public static void solve() {
        int n = input.nextInt();
        for (int i = 0; i < n; ++i) {
            String s = input.next();
            addString(s);
        }
        String t = input.next();
        Pair[] result = new Pair[n];
        Pair[] borders = new Pair[tree.size()];
        fill(result, n);
        fill(borders, tree.size());
        ArrayList<Integer> order = new ArrayList<>(List.of(0));
        int i = 0;
        while (i != order.size()) {
            int u = order.get(i++);
            for (int v : tree.get(u).next) {
                if (v != -1) {
                    order.add(v);
                }
            }
        }
        Collections.reverse(order);
        int cur = 0;
        int index = 0;
        for (int j = 0; j < t.length(); ++j) {
            cur = go(cur, (t.charAt(j) - 'a'));
            if (borders[cur].first == -1) {
                borders[cur].first = index;
            }
            borders[cur].second = index++;
        }
        for (int v : order) {
            int link = getLink(v);
            borders[link].first = borders[link].first == -1 ? borders[v].first : borders[v].first == -1 ? borders[link].first : Math.min(borders[link].first, borders[v].first);
            borders[link].second = borders[link].second == -1 ? borders[v].second : borders[v].second == -1 ? borders[link].second : Math.max(borders[link].second, borders[v].second);
 
        }
        for (int v : order) {
            for (Pair terminal : tree.get(v).terminals) {
                result[terminal.first] = new Pair(borders[v].first - terminal.second + 1 < 0 ? -1 : borders[v].first - terminal.second + 1,
                        borders[v].second - terminal.second + 1 < 0 ? -1 : borders[v].second - terminal.second + 1);
            }
        }
        OutputStream outputStream = System.out;
        PrintWriter out = new PrintWriter(outputStream);
        for (Pair element : result) {
            out.println(element.first + " " + element.second);
        }
        out.close();
    }
 
    public static void main(String[] args) {
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