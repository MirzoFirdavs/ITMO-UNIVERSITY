package search;

public class BinarySearchSpan {

    //Pre: for i in range (0, args.size() - 1): args[i] != null &&
    // for i in range(1, args.size() - 2): args[i] >= args[i + 1] && args.size() > 0;
    //Post: print(k: 0 <= k < args.size() : args[k] <= args[0] && for i in range(k, 1, -1) args[i] > args[0]) &&
    // print(x: for i in range(k, k + x): args[i] == args[0])

    public static void main(String[] args) {
        //Pre: args.size() > 1 && for i in range(args.size()): args[i] != null;
        int key;
        key = Integer.parseInt(args[0]);
        int[] lst = new int[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            lst[i - 1] = Integer.parseInt(args[i]);
        }
        // for i in range(1, lst.size() - 2): args[i] >= args[i + 1] && lst.size() >= 0;
        int left = -1;
        int right = lst.length;

        int x = iterativeBinarySearch(key, lst, left, right);
        int y = recursiveBinarySearch(key, lst, left, right);
        if (x == y) {
            // x == y;
            // lst.count(key) == 0;
            System.out.println(x + " " + 0);
            //return
        } else if (y - x == 1) {
            // y - x == 1;
            //Pre: lst.count(key) == 1;
            System.out.println(x + " " + 1);
            //Post: print((x: lst[x] == key: 0 <= x < lst.size() && for i in range(0, x, 1): lst[i] > key) && lst.count(key));
        } else {
            // y - x > 1;
            //Pre: lst.count(key) > 1;
            System.out.println(x + " " + (y - x));
            //Post: print((x: lst[x] == key: 0 <= x < lst.size() && for i in range(0, x, 1): lst[i] > key) && lst.count(key));
        }
    }

    //Pred: for i in range(0, lst.size() - 2): lst[i] >= lst[i + 1];
    // left == -1; right = lst.size();
    //Post: return(right: 0 <= right < lst.size() : lst[right] <= key && for i in range(right, 0, -1) lst[i] > key);

    public static int iterativeBinarySearch(int key, int[] lst, int left, int right) {
        //for i in range(0, lst.size() - 2): lst[i] >= lst[i + 1];
        //lst.size() >= 0;
        //left == -1; right = lst.size();
        //Inv: (lst[left] > key || left = -1) && (lst[right] <= key || right == lst.size()) && right > left;
        while (right - left > 1) {
            // right - left > 1
            int middle = (left + right) / 2;
            // middle = (left + right) / 2;
            if (lst[middle] <= key) {
                //lst[middle] <= key;
                right = middle;
                // right = middle;
                // lst[right] <= key && right - left = (right' - left') / 2;
            } else {
                left = middle;
                // left = middle;
                //lst[middle] > key && right - left = (right' - left') / 2;
            }
        }
        return right;
    }

    // Pred: for i in range(0, lst.size() - 2): lst[i] >= lst[i + 1];
    // left == -1; right = lst.size();
    // Post: return(right: 0 <= right < lst.size() : lst[right] < key && for i in range(right, 0, -1) lst[i] > key);

    public static int recursiveBinarySearch(int key, int[] lst, int left, int right) {
        // for i in range(0, lst.size() - 2): lst[i] >= lst[i + 1];
        // left == -1; right = lst.size() && lst.size() >= 0;
        if (right - left == 1) {
            // right - left = 1 && lst[right] < key && for i in range(right, 0, -1) lst[i] >= key
            return right;
        }
        int middle = (left + right) / 2;
        // middle = (left + right) / 2 && right - left > 1
        if (lst[middle] < key) {
            //Pre: lst[middle] < key && key <= lst[left] && right - left = middle - left >= 1;
            //Post: lst[middle] < key && key <= lst[left] && right - left = middle - left >= 1;
            return recursiveBinarySearch(key, lst, left, middle);
        } else {
            //Pre: lst[middle] >= key && key > lst[right] && right - left = right - middle >= 1;
            //Post: lst[middle] >= key && key > lst[right] && right - left = right - middle >= 1;
            return recursiveBinarySearch(key, lst, middle, right);
        }
    }
}