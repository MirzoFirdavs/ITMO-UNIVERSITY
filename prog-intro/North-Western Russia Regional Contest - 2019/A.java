import java.util.*;

public class A {
    public static void main(String[] args) {
        int[] x = new int[3];
        Scanner in = new Scanner(System.in);
        x[0] = in.nextInt();
        x[1] = in.nextInt();
        x[2] = in.nextInt();
        double k1 = (x[2] - x[1]);
        double k2 = (x[1] - x[0]);
        double k3 = (k1 / k2);
        double k4 = Math.ceil(k3);
        int ans = (int) (k4);
        System.out.print(2 * ans + 1);
    }
}