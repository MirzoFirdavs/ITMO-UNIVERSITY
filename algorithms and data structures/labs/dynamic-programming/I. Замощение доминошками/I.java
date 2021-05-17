import java.util.Scanner;

public class I {
    public static void main(String[] args) {
        char[][] a = new char[20][20];
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        for (int i = 0; i <= n; i++) {
            String s = "";
            if (i != n) s = in.next();
            for (int j = 0; j <= m; j++) {
                if (i < n && j < m)
                    a[i][j] = s.charAt(j);
                else a[i][j] = '.';
            }
        }
        long[][] d = new long[15][1 << 13];
        int p = 0;
        for (int i = 0; i < n; i++)
            if (a[i][0] == 'X') p |= (1 << i);
        d[0][p] = 1;
        for (int i = 1; i <= m; i++) {
            int o = p;
            p = 0;
            for (int j = 0; j < n; j++)
                if (a[j][i] == 'X') p |= (1 << j);
            for (int x = 0; x <= (1 << n) - 1; x++) {
                for (int y = 0; y <= (1 << n) - 1; y++) {
                    if ((y & p) == p && (x & o) == o && ((x & y) | p) == p)
                        if (good(x, y, i, n, a))
                            d[i][y] += d[i - 1][x];
                }
            }
        }
        System.out.println(d[m][0]);
    }

    private static boolean good(int x, int y, int u, int n, char[][] a) {
        x |= (1 << n);
        y |= (1 << n);
        int i = 0;
        while (i < n) {
            if ((x & (1 << i)) != 0 && (y & (1 << i)) != 0 && a[i][u] != 'X') return false;
            if ((x & (1 << i)) == 0 && ((y & (1 << i)) == 0 || a[i][u] == 'X')) {
                if ((x & (1 << (i + 1))) != 0 || ((y & (1 << (i + 1))) != 0 && a[i + 1][u] != 'X'))
                    return false;
                i++;
            }
            i++;
        }
        return true;
    }
}