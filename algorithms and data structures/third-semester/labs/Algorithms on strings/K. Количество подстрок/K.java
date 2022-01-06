import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import static java.lang.Math.abs;
 
public class K {
    
    public static int[][] c = new int[22][400001];
    
    public static int lcp(int i, int j) {
        int ans = 0;
        for (int k = 19; k >= 0; --k)
            if (c[k][i] == c[k][j] && c[k][j] != 0) {
                ans += (1 << k);
                i += (1 << k);
                j += (1 << k);
            }
        return ans;
    }
 
    public static void main(String[] args) {
        InputReader input = new InputReader(System.in);
        String s = input.next();
        s += '#';
        int[] cnt = new int[400001];
        int[] p = new int[400001];
        for (int i = 0; i < s.length(); ++i) {
            cnt[s.charAt(i)]++;
        }
        for (int i = 1; i < 400001; i++) {
            cnt[i] += cnt[i - 1];
        }
        for (int i = 0; i < s.length(); i++)
            p[abs(--cnt[s.charAt(i)])] = i;
        c[0][p[0]] = 1;
        int classes = 2;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(p[i]) != s.charAt(p[i - 1])) {
                classes++;
            }
            c[0][p[i]] = classes - 1;
        }
        int[] pn = new int[400001];
        int n = s.length();
        for (int h = 0; (1 << h) < n; ++h) {
            for (int i = 0; i < n; ++i) {
                pn[i] = p[i] - (1 << h);
                if (pn[i] < 0) {
                    pn[i] += 2 * n;
                    pn[i] %= n;
                }
            }
            cnt = new int[classes];
            for (int i = 0; i < n; ++i)
                ++cnt[c[h][pn[i]]];
            for (int i = 1; i < classes; ++i)
                cnt[i] += cnt[i - 1];
            for (int i = n - 1; i >= 0; --i)
                p[abs(--cnt[c[h][pn[i]]])] = pn[i];
            c[h + 1][p[0]] = 1;
            classes = 2;
            for (int i = 1; i < n; ++i) {
                int mid1 = (p[i] + (1 << h)) % n, mid2 = (p[i - 1] + (1 << h)) % n;
                if (c[h][p[i]] != c[h][p[i - 1]] || c[h][mid1] != c[h][mid2])
                    ++classes;
                c[h + 1][p[i]] = classes - 1;
            }
        }
        long ans = (long) n * (n - 1) / 2;
        for (int i = 2; i < s.length(); i++) {
            ans -= lcp(p[i - 1], p[i]);
        }
        System.out.println(ans);
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
    }
}