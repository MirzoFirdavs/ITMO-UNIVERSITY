import java.util.concurrent.atomic.AtomicReference;

public class Solution implements Lock<Solution.Node> {
    private final Environment environment;
    private final AtomicReference<Node> t = new AtomicReference<>();

    public Solution(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Node lock() {
        Node cur = new Node();
        cur.locked.set(true);
        Node prev = t.getAndSet(cur);

        if (prev != null) {
            prev.next.set(cur);
            while (cur.locked.get()) environment.park();
        }

        return cur;
    }

    @Override
    public void unlock(Node node) {
        if (node.next.get() == null && t.compareAndSet(node, null)) return;
        else while (node.next.get() == null) continue;

        node.next.get().locked.set(false);
        environment.unpark(node.next.get().thread);
    }

    static class Node {
        final Thread thread = Thread.currentThread();
        final AtomicReference<Node> next = new AtomicReference<>();
        final AtomicReference<Boolean> locked = new AtomicReference<>(false);
    }
}