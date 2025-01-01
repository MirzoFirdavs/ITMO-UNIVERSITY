package chatbot.bot

import chatbot.api.ChatContext
import chatbot.api.ChatId
import chatbot.api.Client
import chatbot.api.Message
import chatbot.dsl.ChatBotDSL
import chatbot.dsl.SendMessageBuilder

@ChatBotDSL
class MessageProcessorContext<C : ChatContext?>(
    val message: Message,
    val client: Client,
    val context: C,
    val setContext: (c: ChatContext?) -> Unit,
) {
    fun sendMessage(chatId: ChatId, sender: SendMessageBuilder.() -> Unit) {
        SendMessageBuilder(client = client, chatId = chatId, message = message).apply(sender).send()
    }

    fun sendMessage(chatId: ChatId, textMessage: String) {
        client.sendMessage(chatId, textMessage)
    }
}

typealias MessageProcessor<C> = MessageProcessorContext<C>.() -> Unit
