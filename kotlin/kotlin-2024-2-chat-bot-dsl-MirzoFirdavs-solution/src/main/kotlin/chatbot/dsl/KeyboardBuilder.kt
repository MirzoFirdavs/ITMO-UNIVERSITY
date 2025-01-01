package chatbot.dsl

import chatbot.api.Keyboard

@ChatBotDSL
class KeyboardBuilder {
    var oneTime: Boolean = false
    var keyboard = mutableListOf<MutableList<Keyboard.Button>>()

    fun row(builder: KeyboardRowDsl.() -> Unit) {
        keyboard.add(KeyboardRowDsl().apply(builder).build())
    }

    @ChatBotDSL
    inner class KeyboardRowDsl {
        private val buttons = mutableListOf<Keyboard.Button>()

        fun button(text: String) = buttons.add(Keyboard.Button(text))

        fun build(): MutableList<Keyboard.Button> = buttons

        operator fun String.unaryMinus() = buttons.add(Keyboard.Button(this))
    }
}
