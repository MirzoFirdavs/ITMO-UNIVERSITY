import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class C {
    public static class Node {
        int x;
        int y;
        int sze;
        int zero;
        Node left;
        Node right;

        public Node(int k) {
            this.left = null;
            this.right = null;
            this.sze = 1;
            this.y = new Random().nextInt() * new Random().nextInt() * new Random().nextInt();
            this.x = k;
            this.zero = (k == 0 ? 1 : 0);
        }
    }

    public static class Pair {
        Node first;
        Node second;

        public Pair(Node first, Node second) {
            this.first = first;
            this.second = second;
        }
    }

    Node root;
    ArrayList<Integer> result = new ArrayList<>();

    public int get_size(Node t) {
        return t != null ? t.sze : 0;
    }

    public int get_zero(Node t) {
        return t != null ? t.zero : 0;
    }

    public void update(Node t) {
        if (t != null) {
            t.sze = get_size(t.left) + get_size(t.right) + 1;
            t.zero = get_zero(t.left) + get_zero(t.right) + (t.x != 0 ? 0 : 1);
        }
    }

    public Pair split(Node t, int k) {
        if (k == get_size(t.left) + 1) {
            Node temp = t.right;
            t.right = null;
            update(t);
            return new Pair(t, temp);
        }
        if (k > get_size(t.left) + 1) {
            Pair temp = split(t.right, k - get_size(t.left) - 1);
            t.right = temp.first;
            update(t);
            return new Pair(t, temp.second);
        }
        if (k == 0) {
            return new Pair(null, t);
        }
        Pair temp = split(t.left, k);
        t.left = temp.second;
        update(t);
        return new Pair(temp.first, t);
    }

    public Node merge(Node a, Node b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        if (a.y > b.y) {
            a.right = merge(a.right, b);
            update(a);
            return a;
        }
        b.left = merge(a, b.left);
        update(b);
        return b;
    }

    public Node remove_zero(Node t) {
        if (t == null) {
            return null;
        }
        if (get_zero(t.left) != 0) {
            t.left = remove_zero(t.left);
        } else if (t.x == 0) {
            return merge(t.left, t.right);
        } else if (get_zero(t.right) != 0) {
            t.right = remove_zero(t.right);
        }
        update(t);
        return t;
    }

    public void build_result(Node v) {
        if (v == null) return;
        build_result(v.left);
        result.add(v.x);
        build_result(v.right);
    }

    public void solve() {
        Scanner in = new Scanner(System.in);
        root = null;
        int n = in.nextInt();
        int m = in.nextInt();
        for (int i = 1; i <= n + m; i++) {
            root = merge(root, new Node(0));
        }
        for (int i = 1; i <= n; i++) {
            int k = in.nextInt();
            Pair temp = split(root, k - 1);
            temp.first = merge(temp.first, new Node(i));
            temp.second = remove_zero(temp.second);
            root = merge(temp.first, temp.second);
        }
        
        build_result(root);
        int num = result.size();
        int p = num - 1;
        for (int i = p; i > -1; i--) {
            if (result.get(p) == 0) {
                num--;
                p--;
            } else {
                break;
            }
        }

        System.out.println(num);
        for (int i = 0; i < num; i++) {
            System.out.print(result.get(i) + " ");
        }
    }

    public static void main(String[] args) {
        new C().solve();
    }
}