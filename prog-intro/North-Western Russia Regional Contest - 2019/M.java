import java.util.*;

public class M {
    public static void main(String[] args) {
        int n, x;
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        for (int k = 0; k < n; ++k) {
            int ans = 0;
            Map<Integer, Integer> mapp = new HashMap<>();
            x = in.nextInt();
            int[] list = new int[x];
            for (int j = 0; j < x; ++j) {
                list[j] = in.nextInt();
            }
            for (int j = x; j > 1; --j) {
                for (int i = 0; i < j - 1; ++i) {
                    ans += mapp.getOrDefault(2 * list[j - 1] - list[i], 0);
                }
                if (mapp.containsKey(list[j - 1])) {
                    mapp.replace(list[j - 1], mapp.get(list[j - 1]) + 1);
                } else {
                    mapp.put(list[j - 1], 1);
                }
            }
            System.out.println(ans);
        }
    }
}