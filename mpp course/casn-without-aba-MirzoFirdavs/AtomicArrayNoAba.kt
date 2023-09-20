import kotlinx.atomicfu.*

class AtomicArrayNoAba<E>(size: Int, initialValue: E) {
    private val a = Array(size) { Ref(initialValue) }
    init { for (i in 0 until size) a[i] = Ref(initialValue) }
    fun get(index: Int) = a[index].value!!
    fun set(index: Int, update: E) { a[index].value = update }
    fun cas(index: Int, expected: E, update: E): Boolean { return a[index].cas(expected, update) }
    fun cas2(index1: Int, expected1: E, update1: E, index2: Int, expected2: E, update2: E): Boolean {
        if (index1 == index2) {
            return if (expected1 == expected2) {
                @Suppress("UNCHECKED_CAST")
                return cas(index1, expected1, (expected1.toString().toInt() + 2) as E)
            } else false
        }
        if (index1 >= index2) {
            val descriptor: DescriptorCas2<E> = DescriptorCas2(a[index2], expected2, update2, a[index1], expected1, update1)
            if (a[index2].cas(expected2, descriptor)) {
                descriptor.complete()
                return descriptor.outcome.value == "SUCCESS"
            }
        } else {
            val descriptor: DescriptorCas2<E> = DescriptorCas2(a[index1], expected1, update1, a[index2], expected2, update2)
            if (a[index1].cas(expected1, descriptor)) {
                descriptor.complete()
                return descriptor.outcome.value == "SUCCESS"
            }
        }
        return false
    }
}
abstract class Descriptor { abstract fun complete() }
class DescriptorCas2<E>(
    private val a: Ref<E>, private val expectA: E, private val updateA: E,
    private val b: Ref<E>, private val expectB: E, private val updateB: E
) : Descriptor() {
    val outcome: Ref<String> = Ref("UNDECIDED")
    override fun complete() {
        if (if(b.v.value != this) b.cas(expectB, this) else true) {
            outcome.v.compareAndSet("UNDECIDED", "SUCCESS")
            a.v.compareAndSet(this, updateA)
            b.v.compareAndSet(this, updateB)
        } else {
            outcome.v.compareAndSet("UNDECIDED", "FAIL")
            a.v.compareAndSet(this, expectA)
            b.v.compareAndSet(this, expectB)
        }
    }
}
@Suppress("UNCHECKED_CAST")
class Ref<E>(initialValue: E) {
    val v: AtomicRef<Any?> = atomic(initialValue)
    var value: E
        get() = v.loop {
            if (it !is Descriptor) return it as E else it.complete()
        }
        set(update) {
            v.loop {
                if (it is Descriptor) it.complete() else if (v.compareAndSet(it, update)) return
            }
        }
    fun cas(expect: Any?, update: Any?): Boolean {
        while (true) {
            if (v.compareAndSet(expect, update)) return true
            val curValue: Any? = v.value
            if (curValue is Descriptor) curValue.complete()
            else if (curValue != expect) return false
        }
    }
}