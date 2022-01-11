import java.util.Scanner;
import java.util.TreeSet;
 
public class F {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
 
        int[] lst = new int[n - 2];
        int[] t = new int[n + 1];
        TreeSet<Integer> q = new TreeSet();
        boolean[] used = new boolean[n + 1];
 
        for (int i = 0; i < n - 2; ++i) {
            int k = in.nextInt();
            lst[i] = k;
        }
        int k = 0;
        for (int i = 1; i < n - 1; ++i) {
            t[lst[k]] = i;
            k += 1;
        }
        for (int i = 1; i < n + 1; ++i) {
            if (t[i] == 0) {
                q.add(i);
                used[i] = true;
            }
        }
        for (int i = 0; i < n - 2; ++i) {
            System.out.println(q.first() + " " + lst[i]);
            q.remove(q.first());
            if (t[lst[i]] <= i + 1 && !used[lst[i]]) {
                q.add(lst[i]);
                used[lst[i]] = true;
            }
        }
        int x = q.first();
        q.remove(q.first());
        int y = q.first();
        System.out.println(x + " " + y);
    }
}