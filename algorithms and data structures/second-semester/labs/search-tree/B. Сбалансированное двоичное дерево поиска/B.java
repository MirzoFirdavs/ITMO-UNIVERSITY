import java.util.*;
import java.lang.*;
import java.io.*;

public class B {
    private static class Node {
        private Node left;
        private Node right;
        private Node parent;
        private final int x;

        Node(int x) {
            this.x = x;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private Node root;

    public void set_parent(Node x, Node p) {
        if (x != null) {
            x.parent = p;
        }
    }

    public void update_parent(Node x) {
        set_parent(x.left, x);
        set_parent(x.right, x);
    }

    public void rotate(Node x) {
        Node p = x.parent;
        Node g = p.parent;
        if (g != null) {
            if (g.left == p) {
                g.left = x;
            } else {
                g.right = x;
            }
        } else {
            root = x;
        }
        if (p.left == x) {
            p.left = x.right;
            x.right = p;
        } else {
            p.right = x.left;
            x.left = p;
        }
        update_parent(x);
        update_parent(p);
        x.parent = g;
    }

    public Node splay(Node x) {
        if (x.parent == null) {
            return x;
        }
        Node p = x.parent;
        Node g = p.parent;
        if (g == null) {
            rotate(x);
            return x;
        }
        if ((p.left == x) == (g.left == p)) {
            rotate(p);
        } else {
            rotate(x);
        }
        rotate(x);
        return splay(x);
    }

    public Node find(Node v, int k) {
        if (v == null) {
            return v;
        }
        if (v.x == k) {
            return splay(v);
        }
        if (k > v.x && v.right != null) {
            return find(v.right, k);
        }
        if (k < v.x && v.left != null) {
            return find(v.left, k);
        }
        return splay(v);
    }

    public ArrayList<Node> split(Node t, int k) {
        if (t == null) {
            ArrayList<Node> cur = new ArrayList<>();
            cur.add(null);
            cur.add(null);
            return cur;
        }
        root = find(root, k);
        if (root.x < k) {
            set_parent(root.right, null);
            Node v = root.right;
            root.right = null;
            ArrayList<Node> cur = new ArrayList<>();
            cur.add(root);
            cur.add(v);
            return cur;
        }
        set_parent(root.left, null);
        Node u = root.left;
        root.left = null;
        ArrayList<Node> cur = new ArrayList<>();
        cur.add(u);
        cur.add(root);
        return cur;
    }

    public Node merge(Node a, Node b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        splay(a);
        while (a.right != null) {
            a = a.right;
        }
        splay(a);
        a.right = b;
        set_parent(b, a);
        return a;
    }

    public boolean exists(int x) {
        return find(root, x) != null && root.x == x;
    }

    public Node insert(Node t, Node k) {
        if (exists(k.x)) {
            return root;
        }
        ArrayList<Node> cur = split(t, k.x);
        return merge(merge(cur.get(0), k), cur.get(1));
    }

    public Node delete(int x) {
        if (!exists(x)) {
            return root;
        }
        root = find(root, x);
        Node u = root.left;
        Node v = root.right;
        set_parent(root.left, null);
        set_parent(root.right, null);
        return merge(u, v);
    }

    public Node next(Node v, int k) {
        if (v == null) {
            return v;
        }
        if (k < v.x) {
            Node u = next(v.left, k);
            return u != null ? splay(u) : splay(v);
        }
        return next(v.right, k);
    }

    public Node prev(Node v, int k) {
        if (v == null) {
            return v;
        }
        if (k > v.x) {
            Node u = prev(v.right, k);
            return u != null ? splay(u) : splay(v);
        }
        return prev(v.left, k);
    }

    public Object check_next_prev(Node x) {
        if (x != null) {
            return x.x;
        } else {
            return "none";
        }
    }

    public void solve() throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String s;
        while ((s = buf.readLine()) != null) {
            st = new StringTokenizer(s);

            String in = st.nextToken();
            int value = Integer.parseInt(st.nextToken());
            switch (in) {
                case "insert":
                    root = insert(root, new Node(value));
                    break;
                case "exists":
                    if (exists(value)) {
                        System.out.println("true");
                    } else {
                        System.out.println("false");
                    }
                    break;
                case "next":
                    Node nxt = next(root, value);
                    System.out.println(check_next_prev(nxt));
                    break;
                case "prev":
                    Node prv = prev(root, value);
                    System.out.println(check_next_prev(prv));
                    break;
                case "delete":
                    root = delete(value);
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new B().solve();
    }
}