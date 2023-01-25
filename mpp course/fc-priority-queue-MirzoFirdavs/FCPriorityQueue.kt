import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.atomicArrayOfNulls
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class FCPriorityQueue<E : Comparable<E>> {
    private val q = PriorityQueue<E>()
    private val queue = atomicArrayOfNulls<Operation<E>>(64)
    private val lock = atomic(false)

    /**
     * Retrieves the element with the highest priority
     * and returns it as the result of this function;
     * returns `null` if the queue is empty.
     */
    fun poll(): E? {
        return methodGenerator(Operation { q.poll() }).value
    }

    /**
     * Returns the element with the highest priority
     * or `null` if the queue is empty.
     */
    fun peek(): E? {
        return methodGenerator(Operation { q.peek() }).value
    }

    /**
     * Adds the specified element to the queue.
     */
    fun add(element: E) {
        methodGenerator(Operation { q.add(element); null })
    }

    private fun methodGenerator(operation: Operation<E>): Operation<E> {
        var ind = ThreadLocalRandom.current().nextInt(0, 64)
        while (true) {
            if (queue[ind].compareAndSet(null, operation)) break
            ind = (ind + 1) % 64
        }
        while (true) {
            if (lock.compareAndSet(expect = false, update = true)) {
                for (i in 0 until 64) {
                    if (queue[i].value != null) {
                        (queue[i].value)?.invoke()
                        queue[i].value = null
                    }
                }
                lock.value = false
                return operation
            }
            if (operation.status) return operation
        }
    }

    class Operation<E>(@Volatile var operation: () -> E?) {
        @Volatile
        var status = false

        @Volatile
        var value: E? = null
        fun invoke() {
            this.value = operation.invoke()
            this.status = true
        }
    }
}