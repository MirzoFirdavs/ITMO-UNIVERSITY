package chatbot.dsl

import chatbot.api.*

@ChatBotDSL
class SendMessageBuilder(private val client: Client, private val chatId: ChatId, val message: Message) {
    var text: String = ""
    var replyTo: MessageId? = null
    private var keyboard: Keyboard? = null

    fun removeKeyboard() {
        keyboard = Keyboard.Remove
    }

    fun withKeyboard(init: KeyboardBuilder.() -> Unit) {
        val builder = KeyboardBuilder()
        builder.init()
        keyboard = Keyboard.Markup(builder.oneTime, builder.keyboard)
    }

    internal fun send() {
        if (text.isBlank() && keyboard !is Keyboard.Remove) {
            if (keyboard == null || (keyboard as? Keyboard.Markup)?.keyboard?.all { it.isEmpty() } == true) {
                return
            }
        }

        client.sendMessage(chatId, text, keyboard, replyTo)
    }
}
