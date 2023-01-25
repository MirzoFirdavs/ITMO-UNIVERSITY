package mpp.faaqueue

import kotlinx.atomicfu.*

class FAAQueue<E> {
    private val head: AtomicRef<Segment> // Head pointer, similarly to the Michael-Scott queue (but the first node is _not_ sentinel)
    private val tail: AtomicRef<Segment> // Tail pointer, similarly to the Michael-Scott queue
    private val enqIdx = atomic(0L)
    private val deqIdx = atomic(0L)

    init {
        val firstNode = Segment(0)
        head = atomic(firstNode)
        tail = atomic(firstNode)
    }

    /**
     * Adds the specified element [x] to the queue.
     */
    fun enqueue(element: E) {
        while (true) {
            if (get(tail.value, enqIdx.getAndAdd(1) / 2).cas(
                    (enqIdx.getAndAdd(1) % 2).toInt(),
                    null,
                    element
                )
            ) return
            while (true) {
                if (tail.value.index < get(
                        tail.value,
                        enqIdx.getAndAdd(1) / 2
                    ).index && tail.compareAndSet(tail.value, get(tail.value, enqIdx.getAndAdd(1) / 2))
                ) break
                else break
            }
        }
    }

    /**
     * Retrieves the first element from the queue and returns it;
     * returns `null` if the queue is empty.
     */
    fun dequeue(): E? {
        while (true) {
            if (deqIdx.value >= enqIdx.value) return null
            val curHead: Segment = head.value
            val index: Long = deqIdx.getAndAdd(1)
            while (true) {
                if (head.value.index < get(curHead, index / 2).index && head.compareAndSet(
                        head.value,
                        get(curHead, index / 2)
                    )
                ) break
                else break
            }
            if (get(curHead, index / 2).cas((index % 2).toInt(), null, false)) continue
            return get(curHead, index / 2).get((index % 2).toInt()) as E?
        }
    }

    /**
     * Returns `true` if this queue is empty, or `false` otherwise.
     */
    val isEmpty: Boolean
        get() {
            return deqIdx.value == enqIdx.value
        }

    private fun get(start: Segment, index: Long): Segment {
        var cur: Segment = start
        while (true) {
            if (cur.index > index) break
            cur.next.compareAndSet(null, Segment(cur.index + 1))
            cur = cur.next.value!!
        }
        return cur
    }
}

private class Segment(val index: Long) {
    val next: AtomicRef<Segment?> = atomic(null)
    val elements = atomicArrayOfNulls<Any>(2)
    fun get(i: Int) = elements[i].value
    fun cas(i: Int, expect: Any?, update: Any?) = elements[i].compareAndSet(expect, update)
}