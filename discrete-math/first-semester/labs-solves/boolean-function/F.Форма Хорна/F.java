import java.util.*;

public class F {
    public static void main(String[] args) {
        int n, k;

        Scanner in = new Scanner(System.in);

        n = in.nextInt();
        k = in.nextInt();

        Map<Integer, Long> zna4 = new HashMap<>();
        Map<Integer, Long> don = new HashMap<>();

        long[][] a = new long[k][n];
        long[][] b = new long[k][n];

        for (int i = 0; i < n; ++i) {
            zna4.put(i, 1L);
        }

        for (int i = 0; i < k; ++i) {
            don.put(i, 0L);
        }

        for (int i = 0; i < k; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = in.nextLong();
                b[i][j] = a[i][j];
                zna4.put(j, -1L);
            }
        }

        while (true) {
            int c = 0;

            for (int i = 0; i < k; ++i) {
                int cur = 0;
                if (don.get(i) == 1) {
                    continue;
                }
                for (int j = 0; j < n; ++j) {
                    if (b[i][j] != -1) {
                        cur++;
                    }
                }
                if (cur == 1) {
                    c = 1;
                    int m = 0;
                    for (int j = 0; j < n; ++j) {
                        if (b[i][j] != -1) {
                            m = j;
                            zna4.put(j, b[i][j]);
                        }
                    }
                    for (int j = 0; j < k; ++j) {
                        if (a[i][m] == b[j][m]) {
                            don.put(j, 1L);
                        }
                        b[j][m] = -1;
                    }
                }
            }
            if (c == 0) {
                break;
            }
        }

        int ans = 1;

        for (int i = 0; i < k; ++i) {
            int cur = 0;
            for (int j = 0; j < n; ++j) {
                if (zna4.get(j) != -1 && a[i][j] != -1) {
                    if (zna4.get(j) == a[i][j]) {
                        cur |= 1;
                    } else {
                        cur |= 0;
                    }
                } else if (zna4.get(j) == -1 && a[i][j] != -1) {
                    cur = 1;
                }
            }
            ans &= cur;
        }

        if (ans == 0) {
            System.out.print("YES");
        } else {
            System.out.print("NO");
        }
    }
}