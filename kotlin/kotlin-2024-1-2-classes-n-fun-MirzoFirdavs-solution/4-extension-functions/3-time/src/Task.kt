val Int.milliseconds: Time
    get() = Time(this.toLong() / 1000, this % 1000)

val Int.seconds: Time
    get() = Time(this.toLong(), 0)

val Int.minutes: Time
    get() = Time(this.toLong() * 60, 0)

val Int.hours: Time
    get() = Time(this.toLong() * 3600, 0)

operator fun Time.plus(other: Time): Time {
    val totalSeconds = seconds + other.seconds
    val totalMilliseconds = milliseconds + other.milliseconds

    return Time(totalSeconds + (totalMilliseconds / 1000), totalMilliseconds % 1000)
}

operator fun Time.minus(other: Time): Time {
    val totalSeconds = seconds - other.seconds
    val totalMilliseconds = milliseconds - other.milliseconds

    return Time(totalSeconds + (totalMilliseconds / 1000), totalMilliseconds % 1000)
}

operator fun Time.times(times: Int): Time {
    val totalSeconds = seconds * times
    val totalMilliseconds = milliseconds * times

    return Time(totalSeconds + (totalMilliseconds / 1000), totalMilliseconds % 1000)
}
