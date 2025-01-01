fun greet(name: String): String {
    return "Hello, $name!"
}

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        for (arg in args) {
            println(greet(arg))
        }
        return
    }

    val input = readlnOrNull()
    if (input.isNullOrBlank()) {
        println(greet("Anonymous"))
    } else {
        println(greet(input))
    }
}
