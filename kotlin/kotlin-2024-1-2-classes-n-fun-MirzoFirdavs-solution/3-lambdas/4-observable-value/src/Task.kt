interface Value<T> {
    var value: T
    fun observe(onChange: (T) -> Unit): Cancellation
}

class MutableValue<T>(initial: T) : Value<T> {
    private val observers = mutableListOf<(T) -> Unit>()

    override var value: T = initial
        set(newValue) {
            field = newValue
            notifyObservers(newValue)
        }

    override fun observe(onChange: (T) -> Unit): Cancellation {
        observers.add(onChange)
        try {
            onChange(value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Cancellation { observers.remove(onChange) }
    }

    private fun notifyObservers(newValue: T) {
        for (observer in observers) {
            observer(newValue)
        }
    }
}

fun interface Cancellation {
    fun cancel()
}
