import java.util.*;
import java.lang.*;
import java.util.Random;

public class G {
    public static class Node {
        int ky, priority, sze;
        Node left;
        Node right;

        public Node(int ky, int priority) {
            this.ky = ky;
            this.priority = priority;
            this.sze = 1;
        }
    }

    static public class Pair {
        Node first;
        Node second;

        public Pair(Node first, Node second) {
            this.first = first;
            this.second = second;
        }
    }

    public int get_size(Node n) {
        if (n != null) {
            return n.sze;
        }
        return 0;
    }

    public void update(Node n) {
        if (n != null) {
            n.sze = get_size(n.left) + get_size(n.right) + 1;
        }
    }

    public Node merge(Node l, Node r) {
        if (l == null) {
            return r;
        }
        if (r == null) {
            return l;
        }
        Node res = new Node(1, 1);
        if (l.priority < r.priority) {
            r.left = merge(l, r.left);
            res = r;
        } else if (l.priority > r.priority) {
            l.right = merge(l.right, r);
            res = l;
        }
        update(res);
        return res;
    }

    public Pair split(Node n, int cnt) {
        if (n == null) {
            return new Pair(null, null);
        }
        Pair res = new Pair(null, null);
        if (get_size(n.left) < cnt) {
            res = split(n.right, cnt - get_size(n.left) - 1);
            n.right = res.first;
            res.first = n;
        } else if (get_size(n.left) == cnt) {
            res = new Pair(n.left, null);
            n.left = res.second;
            res.second = n;
        } else if (get_size(n.left) > cnt) {
            res = split(n.left, cnt);
            n.left = res.second;
            res.second = n;
        }
        update(n);
        return res;
    }

    public Node insert(Node n, int x, int y) {
        if (n == null) {
            return new Node(x, y);
        }
        if (n.priority < y) {
            Node m = new Node(x, y);
            m.left = n;
            update(m);
            return m;
        } else if (n.priority > y) {
            n.right = insert(n.right, x, y);
            update(n);
            return n;
        }
        return n;
    }

    public void print_ans(Node n) {
        if (n == null) {
            return;
        }
        print_ans(n.left);
        System.out.print(n.priority + " ");
        print_ans(n.right);
    }

    public void solve() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        Node root = null;
        for (int i = 0; i < n; ++i) {
            root = insert(root, new Random().nextInt(), i + 1);
        }
        for (int i = 0; i < m; ++i) {
            int l = in.nextInt();
            int r = in.nextInt();
            Pair s1 = split(root, l - 1);
            Pair s2 = split(s1.second, r - l + 1);
            Node s = merge(s1.first, s2.second);
            root = merge(s2.first, s);
        }
        print_ans(root);
    }

    public static void main(String[] args) {
        new G().solve();
    }
}