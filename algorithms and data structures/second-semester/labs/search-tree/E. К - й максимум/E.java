import java.util.Random;
import java.util.Scanner;

public class E {
    public static class Node {
        Node left;
        Node right;

        long x;
        long y;
        long sze;

        public Node(long x) {
            this.x = x;
            this.y = new Random().nextLong() * new Random().nextLong() + new Random().nextLong();
            this.sze = 1;
            this.left = null;
            this.right = null;
        }
    }

    public static class Pair {
        Node first;
        Node second;

        private Pair(Node first, Node second) {
            this.first = first;
            this.second = second;
        }
    }

    public Node root;

    public long get_size(Node t) {
        return t != null ? t.sze : 0;
    }

    public Pair split(Node t, long k) {
        if (t == null) {
            return new Pair(null, null);
        }
        if (k > t.x) {
            Pair temp = split(t.right, k);
            t.right = temp.first;
            t.sze = get_size(t.left) + get_size(t.right) + 1;
            return new Pair(t, temp.second);
        }
        Pair temp = split(t.left, k);
        t.left = temp.second;
        t.sze = get_size(t.left) + get_size(t.right) + 1;
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
            a.sze = get_size(a.left) + get_size(a.right) + 1;
            return a;
        }
        b.left = merge(a, b.left);
        b.sze = get_size(b.left) + get_size(b.right) + 1;
        return b;
    }

    public Node insert(Node t, Node k) {
        Pair temp = split(t, k.x);
        temp.first = merge(temp.first, k);
        return merge(temp.first, temp.second);
    }

    public Node find(Node v, long k) {
        if (v == null)
            return null;
        if (v.x == k)
            return v;
        return (k > v.x) ? find(v.right, k) : find(v.left, k);
    }

    public boolean exists(long x) {
        return find(root, x) != null;
    }

    public Node del(long x) {
        Pair temp1 = split(root, x);
        Pair temp2 = split(temp1.second, x + 1);
        return merge(temp1.first, temp2.second);
    }

    public long findKthMax(Node t, long key) {
        if (get_size(t.right) == key - 1) {
            return t.x;
        }
        if (t.right == null) {
            return findKthMax(t.left, key - 1);
        }
        if (t.right.sze < key) {
            return findKthMax(t.left, key - (t.right.sze + 1));
        }
        return findKthMax(t.right, key);
    }

    public void solve() {
        Scanner in = new Scanner(System.in);
        root = null;
        long n = in.nextLong();
        for (int i = 0; i < n; i++) {
            long x = in.nextLong();
            long k = in.nextLong();
            if (x == 1) {
                if (!exists(k)) {
                    root = insert(root, new Node(k));
                }
            }
            if (x == -1) {
                root = del(k);
            }
            if (x == 0) {
                System.out.println(findKthMax(root, k));
            }
        }
    }

    public static void main(String[] args) {
        new E().solve();
    }
}