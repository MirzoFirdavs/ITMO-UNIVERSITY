package chatbot.dsl

import chatbot.api.ChatBot
import chatbot.api.ChatContext
import chatbot.api.Message
import chatbot.bot.MessageHandler
import chatbot.bot.MessageProcessor

@ChatBotDSL
open class BehaviourBuilder<T : ChatContext?>(val chatBot: ChatBot) {
    val handlers = mutableListOf<MessageHandler<T>>()

    fun build(): List<MessageHandler<T>> = handlers

    private fun addHandler(predicate: (message: Message, context: T) -> Boolean, processor: MessageProcessor<T>) {
        handlers.add(MessageHandler(predicate, processor))
    }

    fun onCommand(command: String, processor: MessageProcessor<T>) {
        onMessagePrefix("/$command", processor)
    }

    fun onMessage(predicate: ChatBot.(Message) -> Boolean, processor: MessageProcessor<T>) {
        addHandler({ message, _ -> chatBot.run { predicate(message) } }, processor)
    }

    fun onMessage(processor: MessageProcessor<T>) {
        addHandler({ _, _ -> true }, processor)
    }

    fun onMessage(messageTextExactly: String, processor: MessageProcessor<T>) {
        addHandler({ message, _ -> message.text == messageTextExactly }, processor)
    }

    fun onMessagePrefix(prefix: String, processor: MessageProcessor<T>) {
        onMessage({ it.text.startsWith(prefix) }, processor)
    }

    fun onMessageContains(text: String, processor: MessageProcessor<T>) {
        onMessage({ it.text.contains(text) }, processor)
    }
}
