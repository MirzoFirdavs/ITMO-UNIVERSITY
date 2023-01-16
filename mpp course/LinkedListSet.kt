package mpp.linkedlistset

import kotlinx.atomicfu.*

class LinkedListSet<E : Comparable<E>> {
    private val first = Node<E>(prev = null, element = null, next = null)
    private val last = Node(prev = first, element = null, next = null)
    init { first.setNext(last) }
    private val head = atomic(first)
    /**
     * Adds the specified element to this set
     * if it is not already present.
     *
     * Returns `true` if this set did not
     * already contain the specified element.
     */
    fun add(element: E): Boolean { while (true) { val (cur, next) = findWindow(element); if (!(next != last && next.element == element)) { if (cur.casNext(next, Node(cur, element, next))) return true } else return false } }
    /**
     * Removes the specified element from this set
     * if it is present.
     *
     * Returns `true` if this set contained
     * the specified element.
     */
    fun remove(element: E): Boolean { while (true) { val curNext: List<Node<E>> = findWindow(element); if (curNext[1] != last && curNext[1].element == element) { if (if (curNext[1].casFlag(expected = false, update = true)) { curNext[0].casNext(curNext[1], curNext[1].next!!); true } else false) return true } else return false } }
    /**
     * Returns `true` if this set contains
     * the specified element.
     */
    fun contains(element: E): Boolean { return findWindow(element)[1] != last && findWindow(element)[1].element == element }
    private fun findWindow(element: E): List<Node<E>> { var cur = head.value; var next = cur.next!!; while (next != last && next.element < element) { when {next.flag -> { cur.casNext(next, next.next!!); next = cur.next!! }; else -> { cur = next; next = next.next!! } } }; return ArrayList<Node<E>>(listOf(cur, next)) }
}

private class Node<E : Comparable<E>>(prev: Node<E>?, element: E?, next: Node<E>?) {
    private val _element = element // `null` for the first and the last nodes
    val element get() = _element!!
    private val _flag = atomic(false)
    val flag get() = _flag.value
    fun casFlag(expected: Boolean, update: Boolean) =
        _flag.compareAndSet(expected, update)
    @Suppress("UNUSED")
    private val _prev = atomic(prev)
    private val _next = atomic(next)
    val next get() = _next.value
    fun setNext(value: Node<E>?) {
        _next.value = value
    }
    fun casNext(expected: Node<E>?, update: Node<E>?) = _next.compareAndSet(expected, update)
}