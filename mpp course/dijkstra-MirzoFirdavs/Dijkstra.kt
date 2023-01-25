package dijkstra

import kotlinx.atomicfu.AtomicInt
import kotlinx.atomicfu.atomic
import java.util.*
import java.util.concurrent.Phaser
import kotlin.Comparator
import kotlin.concurrent.thread

private val NODE_DISTANCE_COMPARATOR = Comparator<Node> { o1, o2 -> o1!!.distance.compareTo(o2!!.distance) }

// Returns `Integer.MAX_VALUE` if a path has not been found.
fun shortestPathParallel(start: Node) {
    val workers = Runtime.getRuntime().availableProcessors()
    start.distance = 0
    val queue = MultiQueue(workers, NODE_DISTANCE_COMPARATOR)
    queue.push(start)
    val onFinish = Phaser(workers + 1)
    repeat(workers) {
        thread {
            while (true) {
                val cur: Node = synchronized(queue) {
                    queue.poll()
                } ?: if (queue.isEmpty()) break else continue
                for (e in cur.outgoingEdges) {
                    while (true) {
                        val old: Int = e.to.distance
                        val new: Int = cur.distance + e.weight

                        if (old > new) {
                            if (e.to.casDistance(old, new)) {
                                queue.push(e.to)
                            } else {
                                continue
                            }
                        }
                        break
                    }
                }
                queue.decrement()
            }
            onFinish.arrive()
        }
    }
    onFinish.arriveAndAwaitAdvance()
}
private class MultiQueue(val workers: Int, comparator: Comparator<Node>) {
    private val size: AtomicInt = atomic(0)
    private val random: Random = Random(0)
    private val multiQueue: MutableList<PriorityQueue<Node>> = Collections.nCopies(workers, PriorityQueue(comparator))

    fun push(node: Node) {
        val index = random.nextInt(workers)

        size.incrementAndGet()

        synchronized(multiQueue[index]) {
            multiQueue[index].add(node)
        }
    }

    fun getUniqueRandomTuple(bound: Int): Pair<Int, Int> {
        var p = Pair(0, 0)

        while (p.first == p.second) {
            p = Pair(random.nextInt(bound), random.nextInt(bound))
        }

        return p
    }

    fun poll(): Node? {
        val (first, last) = getUniqueRandomTuple(workers)

        synchronized(multiQueue[first]) {
            synchronized(multiQueue[last]) {
                if (multiQueue[first].peek() != null) {
                    if (multiQueue[last].peek() != null) {
                        return if (multiQueue[first].peek().distance < multiQueue[last].peek().distance) {
                            multiQueue[first].poll()
                        } else {
                            multiQueue[last].poll()
                        }
                    }

                    return multiQueue[first].peek()
                }

                return multiQueue[last].peek()
            }
        }
    }

    fun isEmpty() = size.compareAndSet(0, 0)
    fun decrement() = size.decrementAndGet()
}