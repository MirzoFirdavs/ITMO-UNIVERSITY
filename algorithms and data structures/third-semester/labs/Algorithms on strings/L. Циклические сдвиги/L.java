import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
 
public class L {
    public static void main(String[] args) {
        InputReader input = new InputReader(System.in);
        String s = input.next();
        int k = input.nextInt();
        int[] cnt = new int[400001];
        int[] p = new int[400001];
        int[] c = new int[400001];
        int[] pn = new int[400001];
        int[] cn = new int[400001];
        int n = s.length();
        for (int i = 0; i < n; ++i) {
            cnt[s.charAt(i)]++;
        }
        for (int i = 1; i < 400001; ++i) {
            cnt[i] += cnt[i - 1];
        }
        for (int i = 0; i < n; ++i) {
            p[--cnt[s.charAt(i)]] = i;
        }
        c[p[0]] = 0;
        int classes = 1;
        for (int i = 1; i < n; i++) {
            if (s.charAt(p[i]) != s.charAt(p[i - 1])) {
                classes++;
            }
            c[p[i]] = classes - 1;
        }
        for (int h = 0; (1 << h) <= 2 * n; ++h) {
            for (int i = 0; i < n; ++i) {
                pn[i] = p[i] - (1 << h);
                if (pn[i] < 0) {
                    pn[i] += 2 * n;
                    pn[i] %= n;
                }
            }
            cnt = new int[400001];
            for (int i = 0; i < n; ++i) {
                ++cnt[c[pn[i]]];
            }
            for (int i = 1; i < classes; ++i) {
                cnt[i] += cnt[i - 1];
            }
            for (int i = n - 1; i >= 0; --i) {
                p[--cnt[c[pn[i]]]] = pn[i];
            }
            cn[p[0]] = 0;
            classes = 1;
            for (int i = 1; i < n; ++i) {
                int mid1 = (p[i] + (1 << h)) % n, mid2 = (p[i - 1] + (1 << h)) % n;
                if (c[p[i]] != c[p[i - 1]] || c[mid1] != c[mid2])
                    ++classes;
                cn[p[i]] = classes - 1;
            }
            System.arraycopy(cn, 0, c, 0, n);
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 1; i < n; i++)
            if (c[p[i - 1]] != c[p[i]])
                ans.add(p[i - 1]);
        ans.add(p[n - 1]);
        if (k > ans.size()) {
            System.out.println("IMPOSSIBLE");
        } else {
            for (int i = ans.get(k - 1); i < ans.get(k - 1) + n; i++) {
                System.out.print(s.charAt(i % n));
            }
        }
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