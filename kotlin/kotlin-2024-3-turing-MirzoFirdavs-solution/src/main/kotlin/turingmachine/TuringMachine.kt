package turingmachine

import kotlin.collections.ArrayDeque

class TuringMachine(
    private val startingState: String,
    val acceptedState: String,
    private val rejectedState: String,
    transitions: Collection<TransitionFunction>,
) {

    private val transitionMap: Map<Pair<String, Char>, Transition> = transitions.associate {
        Pair(it.state, it.symbol) to it.transition
    }

    fun initialSnapshot(input: String): Snapshot {
        return Snapshot(startingState, Tape(input))
    }

    fun simulateStep(snapshot: Snapshot): Snapshot {
        val currentSymbol = snapshot.tape.content.getOrNull(snapshot.tape.position) ?: BLANK
        val transition = transitionMap[Pair(snapshot.state, currentSymbol)]

        return if (transition != null) {
            snapshot.applyTransition(transition)
        } else {
            snapshot.copy(state = rejectedState)
        }
    }

    fun simulate(input: String): Sequence<Snapshot> {
        return sequence {
            var snapshot = initialSnapshot(input)
            yield(snapshot)
            do {
                snapshot = simulateStep(snapshot)
                yield(snapshot)
            } while (snapshot.state != acceptedState && snapshot.state != rejectedState)
        }
    }

    data class Snapshot(val state: String, val tape: Tape) {
        fun applyTransition(transition: Transition): Snapshot {
            tape.applyTransition(transition.newSymbol, transition.move)
            return copy(state = transition.newState)
        }

        fun copy(): Snapshot {
            return Snapshot(state, tape.copy())
        }

        override fun toString(): String {
            return "Snapshot(state='$state', tape=$tape)"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Snapshot

            if (state != other.state) return false
            if (tape != other.tape) return false

            return true
        }

        override fun hashCode(): Int {
            var result = state.hashCode()
            result = 31 * result + tape.hashCode()
            return result
        }
    }

    class Tape(initialContent: String, var position: Int = 0) {
        val content: ArrayDeque<Char> =
            ArrayDeque(if (initialContent.isNotEmpty()) initialContent.toList() else listOf(BLANK))

        fun applyTransition(char: Char, move: TapeTransition): Tape {
            content[position] = char

            when (move) {
                TapeTransition.Left -> position--
                TapeTransition.Right -> position++
                TapeTransition.Stay -> {}
            }

            if (position < 0) {
                content.addFirst(BLANK)
                position = 0
            }

            if (position >= content.size) {
                content.addLast(BLANK)
            }

            while (content.last() == BLANK && position < content.size - 1) {
                content.removeLast()
            }

            while (content.first() == BLANK && position > 0) {
                content.removeFirst()
                position--
            }

            return this
        }

        override fun toString(): String {
            return content.joinToString("") + " (head at $position)"
        }

        fun copy(): Tape {
            return Tape(content.joinToString("", "", ""), position)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Tape) return false
            return content == other.content && position == other.position
        }

        override fun hashCode(): Int {
            var result = content.hashCode()
            result = 31 * result + position
            return result
        }
    }
}
