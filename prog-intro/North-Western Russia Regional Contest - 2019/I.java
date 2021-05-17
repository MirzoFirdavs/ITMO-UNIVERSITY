import java.util.*;

public class I {
    public static void main(String[] args) {
        int n;
        int x, y, h;

        Scanner in = new Scanner(System.in);

        n = in.nextInt();

        int xl = Integer.MAX_VALUE;
        int xr = Integer.MIN_VALUE;
        int yl = Integer.MAX_VALUE;
        int yr = Integer.MIN_VALUE;

        for (int i = 0; i < n; ++i) {
            x = in.nextInt();
            y = in.nextInt();
            h = in.nextInt();
            xl = Math.min(xl, x - h);
            xr = Math.max(xr, x + h);
            yl = Math.min(yl, y - h);
            yr = Math.max(yr, y + h);
        }

        System.out.print((xl + xr) / 2 + " " + (yl + yr) / 2 + " " + (Math.max(xr - xl, yr - yl) + 1) / 2);
    }
}