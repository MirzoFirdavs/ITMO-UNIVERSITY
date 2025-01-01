sealed interface IntResult {
    data class Ok(val value: Int) : IntResult
    data class Error(val reason: String) : IntResult
}

class NoResultProvided(reason: String) : NoSuchElementException(reason)

fun safeRun(unsafe: () -> Int): IntResult {
    return try {
        IntResult.Ok(unsafe())
    } catch (e: Exception) {
        IntResult.Error(e.message ?: "Unknown error occurred")
    }
}

fun IntResult.getOrDefault(default: Int): Any {
    return when (this) {
        is IntResult.Ok -> this.value
        is IntResult.Error -> default
    }
}

fun IntResult.getOrNull(): Any? {
    return when (this) {
        is IntResult.Ok -> this.value
        is IntResult.Error -> null
    }
}

fun IntResult.getStrict(): Any {
    return when (this) {
        is IntResult.Ok -> this.value
        is IntResult.Error -> throw NoResultProvided(this.reason)
    }
}
