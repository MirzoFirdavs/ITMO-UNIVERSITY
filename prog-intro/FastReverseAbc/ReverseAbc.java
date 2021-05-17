import java.io.*;
import java.util.*;

public class ReverseAbc {
    public static void main (String[] args) {
        try {
            FastScanner in = new FastScanner(System.in);
            String[][] list = new String[1][];
            String[] list1 = new String[1];
            int[] rowsLength = new int[1];
            int cur = 0, cur1 = 0;

            while (in.hasNextLine()) {
        	    String in1 = in.nextWord();
                if (in1.equals("")) {
                    list[cur] = list1;
                    rowsLength[cur++] = cur1;
                    if (cur == list.length) {
                        list = Arrays.copyOf(list, list.length * 2);
                        rowsLength = Arrays.copyOf(rowsLength, rowsLength.length * 2);
                    }
                    list1 = new String[1];
                    cur1 = 0;
                } else {
                    list1[cur1++] = in1;
                    if (cur1 == list1.length) {
                        list1 = Arrays.copyOf(list1, list1.length * 2);
                    }
                }
            }
            for (int i = cur - 1; i > -1; --i) {
                for (int j = rowsLength[i] - 1; j > -1; --j) {
                    System.out.print(list[i][j]);
                    System.out.print(" ");
                }
                System.out.print('\n');
            }
        } catch (IOException e) {
            System.out.print("There is some problem with input or output");
        } 
    }
}