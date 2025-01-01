package turingmachine

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.clikt.parameters.types.float
import java.io.File

class TuringMachineApp : CliktCommand() {
    private val descriptionFile: String by option(help = "Path to the Turing machine description file").required()
    private val inputFile: File? by argument(help = "Path to the input word file").file(mustExist = true).optional()
    private val auto: Boolean by option("--auto", help = "Run in automatic mode").flag()
    private val delay: Float? by option("--delay", help = "Delay between steps in seconds").float().default(0.5f)

    override fun run() {
        // Load the machine description directly into the TuringMachine
        val machine = loadMachineDescription(descriptionFile)

        // Reading input from the file or prompting for user input
        val input = inputFile?.readText()?.trim() ?: run {
            println("Введите входное слово:")
            readlnOrNull() ?: ""
        }

        simulateMachine(machine, input)
    }

    private fun loadMachineDescription(filePath: String): TuringMachine {
        val lines = File(filePath).readLines()
        val startingState = lines[0].substringAfter(":").trim()
        val acceptedState = lines[1].substringAfter(":").trim()
        val rejectedState = lines[2].substringAfter(":").trim()
        val transitions = lines.subList(4, lines.size)
            .map { parseTransition(it) }

        return TuringMachine(startingState, acceptedState, rejectedState, transitions)
    }

    private fun parseTransition(rule: String): TransitionFunction {
        val splitRule = rule.split("->", " ").filter { it.isNotBlank() }

        return TransitionFunction(
            state = splitRule[0],
            symbol = splitRule[1].first(), // Предполагаем, что символ один
            transition = Transition(
                newState = splitRule[2],
                newSymbol = splitRule[3].first(), // Предполагаем, что символ один
                move = when (splitRule[4]) {
                    "<" -> TapeTransition.Left
                    ">" -> TapeTransition.Right
                    "^" -> TapeTransition.Stay
                    else -> throw IllegalArgumentException("transition has illegal format: ${splitRule[4]}")
                },
            ),
        )
    }

    private fun simulateMachine(machine: TuringMachine, input: String) {
        val sequence: Sequence<TuringMachine.Snapshot> = machine.simulate(input)

        for (snapshot in sequence) {
            println(snapshot)
            if (auto) {
                Thread.sleep((delay!! * 1000).toLong())
            } else {
                println("Press Enter to continue...")
                readln()
            }
        }

        println("Final state of tape: ${sequence.last().tape}")
        println(if (sequence.last().state == machine.acceptedState) "Accepted" else "Rejected")
    }
}

fun main(args: Array<String>) {
    TuringMachineApp().main(args)
}
