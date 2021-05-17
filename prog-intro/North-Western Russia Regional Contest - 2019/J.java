import java.util.*;

public class J {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n;
        String st;
        st = in.nextLine();
        n = Integer.parseInt(st);

        int[][] graph = new int[n][n];

        for (int i = 0; i < n; ++i) {
            st = in.nextLine();
            for (int j = 0; j < n; ++j) {
                char x = st.charAt(j);
                int b = x - '0';
                graph[i][j] = b;
            }
        }

        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (graph[i][j] == 1) {
                    for (int k = j + 1; k < n; ++k) {
                        graph[i][k] -= graph[j][k];
                        graph[i][k] += 10;
                        graph[i][k] %= 10;
                    }
                }
            }
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(graph[i][j]);
            }
            System.out.print('\n');
        }

    }
}