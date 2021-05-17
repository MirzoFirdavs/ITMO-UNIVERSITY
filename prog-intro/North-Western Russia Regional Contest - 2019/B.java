import java.util.*;

public class B {
    public static void main(String[] args) {
        int x;
        Scanner in = new Scanner(System.in);
        x = in.nextInt();
        int m = -710 * 250000;
        for (int i = 0; i < x; ++i) {
            System.out.println(m);
            m += 710;
        }
    }
}