import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

public class I {
    private static final String LINK = "link";
    private static final String CUT = "cut";
    private static final String SIZE = "size";
    private static final Random random = new Random();
    private static Map<String, Node> tree;

    public static class Node {
        public Node leftChild;
        public Node rightChild;
        public Node parent;
        public String edge;
        public int priority;
        public int size;

        public Node(final String edge) {
            this.edge = edge;
            this.priority = random.nextInt();
            this.size = 1;
            this.leftChild = null;
            this.rightChild = null;
            this.parent = null;
        }

        @SuppressWarnings("unused")
        public final String getEdge() {
            return edge;
        }

        @SuppressWarnings("unused")
        public final int getPriority() {
            return priority;
        }

        @SuppressWarnings("unused")
        public final int getSize() {
            return size;
        }

        @SuppressWarnings("unused")
        public final Node getLeftChild() {
            return leftChild;
        }

        @SuppressWarnings("unused")
        public final Node getRightChild() {
            return rightChild;
        }

        @SuppressWarnings("unused")
        public final Node getParent() {
            return parent;
        }

        @SuppressWarnings("unused")
        public final void setLeftChild(final Node newLeftChild) {
            this.leftChild = newLeftChild;
        }

        @SuppressWarnings("unused")
        public final void setRightChild(final Node newRightChild) {
            this.rightChild = newRightChild;
        }

        @SuppressWarnings("unused")
        public final void setParent(final Node newParent) {
            this.parent = newParent;
        }

        @SuppressWarnings("unused")
        public final void setEdge(final String newEdge) {
            this.edge = newEdge;
        }

        @SuppressWarnings("unused")
        public final void setPriority(final int newPriority) {
            this.priority = newPriority;
        }

        @SuppressWarnings("unused")
        public final void setSize(final int newSize) {
            this.size = newSize;
        }

        @Override
        public final boolean equals(final Object o) {
            if (this == o) {
                return true;
            }

            if (!(o instanceof Node)) {
                return false;
            }

            final Node key = (Node) o;

            return leftChild == key.leftChild && rightChild == key.rightChild
                    && parent == key.parent && priority == key.priority && size == key.size && edge.equals(key.edge);

        }

        @Override
        public final int hashCode() {
            return 31 * priority + size;
        }
    }

    public static class Pair {
        public Node first;
        public Node second;

        public Pair(final Node first, final Node second) {
            this.first = first;
            this.second = second;
        }

        @SuppressWarnings("unused")
        public final Node getFirst() {
            return first;
        }

        @SuppressWarnings("unused")
        public final Node getSecond() {
            return second;
        }

        @SuppressWarnings("unused")
        public final void setFirst(final Node newFirst) {
            this.first = newFirst;
        }

        @SuppressWarnings("unused")
        public final void setSecond(final Node newSecond) {
            this.second = newSecond;
        }
    }

    public static int getSize(final Node node) {
        return (node != null ? node.getSize() : 0);
    }

    public static void update(final Node node) {
        if (node != null) {
            node.setSize(1 + getSize(node.getLeftChild()) + getSize(node.getRightChild()));
        }
    }

    public static int getIndex(Node node) {
        int ans = getSize(node.getLeftChild());

        while (node != null) {
            if (node.getParent() != null && node.getParent().getRightChild() == node) {
                ans += getSize(node.getParent().getLeftChild()) + 1;
            }

            node = node.getParent();
        }

        return ans;
    }

    public static Node getRoot(Node node) {
        while (node.getParent() != null) {
            node = node.getParent();
        }

        return node;
    }

    public static Node merge(final Node first, final Node second) {
        if (first == null && second == null) {
            return null;
        } else if (second == null) {
            update(first);
            return first;
        } else if (first == null) {
            update(second);
            return second;
        } else {
            update(first);
            update(second);

            if (first.getPriority() < second.getPriority()) {
                second.setLeftChild(merge(first, second.getLeftChild()));

                if (second.getLeftChild() != null) {
                    second.getLeftChild().setParent(second);
                }

                update(first);
                update(second);

                second.setParent(null);

                return second;
            } else {
                first.setRightChild(merge(first.getRightChild(), second));

                if (first.getRightChild() != null) {
                    first.getRightChild().setParent(first);
                }

                update(first);
                update(second);

                first.setParent(null);

                return first;
            }
        }
    }

    public static Pair split(final Node node, final int key) {
        if (node == null) {
            return new Pair(null, null);
        }

        final int index = getSize(node.getLeftChild());

        if (index >= key) {
            final Pair tmp = split(node.getLeftChild(), key);
            node.setLeftChild(tmp.getSecond());

            if (node.getLeftChild() != null) {
                node.getLeftChild().setParent(node);
            }

            update(node);
            node.setParent(null);

            if (tmp.getFirst() != null) {
                tmp.getFirst().setParent(null);
            }

            return new Pair(tmp.getFirst(), node);
        } else {
            final Pair tmp = split(node.getRightChild(), key - index - 1);
            node.setRightChild(tmp.getFirst());

            if (node.getRightChild() != null) {
                node.getRightChild().setParent(node);
            }

            update(node);
            node.setParent(null);

            if (tmp.getSecond() != null) {
                tmp.getSecond().setParent(null);
            }

            return new Pair(node, tmp.getSecond());
        }
    }

    public static void link(final int v, final int u) {
        final String vu = v + " " + u;
        final String uv = u + " " + v;
        final String uu = u + " " + u;
        final String vv = v + " " + v;


        final Node vU = new Node(vu);
        final Node uV = new Node(uv);

        tree.put(vu, vU);
        tree.put(uv, uV);

        final Pair first = split(getRoot(tree.get(vv)), getIndex(tree.get(vv)));
        final Pair second = split(getRoot(tree.get(uu)), getIndex(tree.get(uu)));

        Node tmp = merge(first.getFirst(), vU);

        tmp = merge(tmp, second.getSecond());
        tmp = merge(tmp, second.getFirst());
        tmp = merge(tmp, uV);

        merge(tmp, first.getSecond());
    }

    public static void cut(final int v, final int u) {
        final String vu = v + " " + u;
        final String uv = u + " " + v;

        Node vU = tree.get(vu);
        Node uV = tree.get(uv);

        if (getIndex(uV) < getIndex(vU)) {
            final Node temp = uV;
            uV = vU;
            vU = temp;
        }

        final Pair tmp1 = split(getRoot(vU), getIndex(vU));

        split(tmp1.getSecond(), getIndex(vU) + 1);

        final Pair tmp2 = split(getRoot(uV), getIndex(uV));
        final Pair tmp3 = split(tmp2.getSecond(), getIndex(uV) + 1);

        merge(tmp1.getFirst(), tmp3.getSecond());
    }


    public static void solve() {
        InputReader inputReader = new InputReader(System.in);
        PrintWriter printWriter = new PrintWriter(System.out);

        final int n = inputReader.nextInt();
        final int m = inputReader.nextInt();

        tree = new HashMap<>();

        for (int i = 0; i < n; ++i) {
            final Node temp = new Node(i + " " + i);
            tree.put(i + " " + i, temp);
        }

        for (int i = 0; i < m; ++i) {
            final String query = inputReader.next();

            switch (query) {
                case LINK: {
                    final int u = inputReader.nextInt() - 1;
                    final int v = inputReader.nextInt() - 1;

                    link(Math.min(v, u), Math.max(v, u));
                    break;
                }
                case CUT: {
                    final int u = inputReader.nextInt() - 1;
                    final int v = inputReader.nextInt() - 1;

                    cut(Math.min(v, u), Math.max(v, u));
                    break;
                }
                case SIZE: {
                    final int v = inputReader.nextInt() - 1;
                    final String vv = v + " " + v;
                    printWriter.println((getRoot(tree.get(vv)).getSize() + 2) / 3);
                    break;
                }
            }
        }

        printWriter.close();
    }

    public static void main(final String[] args) {
        solve();
    }

    public static class InputReader {
        public final BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public final String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (final IOException e) {
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