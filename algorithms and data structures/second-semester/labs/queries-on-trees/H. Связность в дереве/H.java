import java.io.*;
import java.util.StringTokenizer;

public class H {
    private static final int N = 100001;
    private static final String CUT = "cut";
    private static final String CONNECTED = "connected";
    private static final String LINK = "link";

    private static final Node[] tree = new Node[N];

    public static class Node {
        public Node leftChild;
        public Node rightChild;
        public Node parent;
        public boolean isReversed;

        public Node() {
            this.leftChild = null;
            this.rightChild = null;
            this.parent = null;
            this.isReversed = false;
        }

        public boolean isRoot() {
            return parent == null || (parent.getLeftChild() != this && parent.getRightChild() != this);
        }

        public void push() {
            if (isReversed) {
                isReversed = false;

                final Node tmp = leftChild;

                leftChild = rightChild;
                rightChild = tmp;

                if (leftChild != null) {
                    leftChild.setReversed(leftChild.getReversed());
                }

                if (rightChild != null) {
                    rightChild.setReversed(rightChild.getReversed());
                }
            }
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public Node getParent() {
            return parent;
        }

        public boolean getReversed() {
            return !isReversed;
        }

        public void setLeftChild(final Node newLeftChild) {
            this.leftChild = newLeftChild;
        }

        public void setRightChild(final Node newRightChild) {
            this.rightChild = newRightChild;
        }

        public void setParent(final Node newParent) {
            this.parent = newParent;
        }

        public void setReversed(final boolean newIsReversed) {
            this.isReversed = newIsReversed;
        }
    }

    public static void connect(final Node child, final Node parent, final Boolean isLeftChild) {
        if (child != null) {
            child.setParent(parent);
        }

        if (isLeftChild != null) {
            if (isLeftChild) {
                parent.setLeftChild(child);
            } else {
                parent.setRightChild(child);
            }
        }
    }

    public static void rotate(final Node node) {
        final Node parent = node.parent;
        final Node grandpa = parent.parent;

        final boolean isRootParent = parent.isRoot();
        final boolean isLeftChild = (node == parent.getLeftChild());

        connect((isLeftChild ? node.getRightChild() : node.getLeftChild()), parent, isLeftChild);
        connect(parent, node, !isLeftChild);
        connect(node, grandpa, !isRootParent ? parent == grandpa.getLeftChild() : null);
    }

    public static void splay(final Node node) {
        if (node == null) {
            return;
        }

        while (!node.isRoot()) {
            final Node parent = node.getParent();
            final Node grandpa = parent.getParent();

            if (!parent.isRoot()) {
                grandpa.push();
            }

            parent.push();
            node.push();

            if (!parent.isRoot()) {
                rotate((node == parent.getLeftChild()) == (parent == grandpa.getLeftChild()) ? parent : node);
            }

            rotate(node);
        }

        node.push();
    }

    public static void expose(final Node node) {
        Node last = null;
        Node current = node;

        while (current != null) {
            splay(current);
            current.setLeftChild(last);
            last = current;
            current = current.getParent();
        }

        splay(node);
    }

    public static void makeRoot(final Node node) {
        expose(node);
        node.setReversed(node.getReversed());
    }

    public static boolean connected(final Node x, final Node y) {
        if (x == y) {
            return true;
        }

        expose(x);
        expose(y);

        return x.getParent() != null;
    }

    public static void link(final Node x, final Node y) {
        if (connected(x, y)) {
            return;
        }

        makeRoot(x);
        x.setParent(y);
    }

    public static void cut(final Node x, final Node y) {
        makeRoot(x);
        expose(y);

        if (y.getRightChild() != x || x.getLeftChild() != null || x.getRightChild() != null) {
            return;
        }

        y.getRightChild().setParent(null);
        y.setRightChild(null);
    }

    public static void generate() {
        final InputReader inputReader = new InputReader(System.in);
        final PrintWriter printWriter = new PrintWriter(System.out);

        final int n = inputReader.nextInt();

        for (int i = 0; i <= n; i++) {
            tree[i] = new Node();
        }

        final int m = inputReader.nextInt();

        for (int i = 0; i < m; i++) {
            final String query = inputReader.next();

            final int u = inputReader.nextInt();
            final int v = inputReader.nextInt();

            switch (query) {
                case LINK:
                    link(tree[u], tree[v]);
                    break;
                case CUT:
                    cut(tree[u], tree[v]);
                    break;
                case CONNECTED:
                    printWriter.println((connected(tree[u], tree[v]) ? "1" : "0"));
                    break;
            }
        }

        printWriter.close();
    }

    public static void main(final String[] args) {
        generate();
    }

    public static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public final String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public final int nextInt() {
            return Integer.parseInt(next());
        }
    }
}