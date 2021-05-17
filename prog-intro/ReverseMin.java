import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class ReverseMin{
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int[] list = new int[1000010];
        int[] column = new int[1000010];
        int[] row = new int[1000010];
        int n = 0;
        for(int i = 0; i < 1000010; i++) {
            column[i] = Integer.MAX_VALUE;
            row[i] = Integer.MAX_VALUE;
        }
        while(in.hasNextLine()) {
            Scanner in1 = new Scanner(in.nextLine());
            int cur = 0;    
            while(in1.hasNext()) {
                int j = in1.nextInt();
                column[cur] = Math.min(column[cur], j);
                row[n] = Math.min(row[n], j);
                cur++;
            } 
            list[n] = cur;
            n++;
            in1.close(); 
        }
        in.close();
        for(int i = 0 ; i < n ; i++) {
            int m = list[i];
            for (int j = 0 ; j < m; j++) {
                System.out.print(Math.min(column[j], row[i]));
                System.out.print(" ");
            }
            System.out.println();
        }       
    }
}