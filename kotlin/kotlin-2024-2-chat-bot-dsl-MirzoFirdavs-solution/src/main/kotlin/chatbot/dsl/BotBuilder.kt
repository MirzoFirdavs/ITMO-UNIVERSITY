package chatbot.dsl

import chatbot.api.*
import chatbot.bot.Bot
import chatbot.bot.MessageHandler

@ChatBotDSL
class BotBuilder(private val client: Client) {
    private var logLevel: LogLevel = LogLevel.ERROR
    private var contextManager: ChatContextsManager? = null
    private var messageHandlers: MutableList<MessageHandler<ChatContext?>> = mutableListOf()

    fun use(logLevel: LogLevel) {
        this.logLevel = logLevel
    }

    fun use(chatContextsManager: ChatContextsManager) {
        this.contextManager = chatContextsManager
    }

    operator fun LogLevel.unaryPlus() {
        logLevel = this
    }

    fun behaviour(builder: IntoBehaviourBuilder.() -> Unit) {
        val behaviourBuilder = IntoBehaviourBuilder(object : ChatBot {
            override val logLevel: LogLevel
                get() = this@BotBuilder.logLevel

            override fun processMessages(message: Message) {}
        })
        builder(behaviourBuilder)
        messageHandlers.addAll(behaviourBuilder.build())
    }

    fun build(): ChatBot {
        return Bot(logLevel, messageHandlers, contextManager, client)
    }
}

fun chatBot(client: Client, chatBotBuilder: BotBuilder.() -> Unit): ChatBot {
    return BotBuilder(client).apply(chatBotBuilder).build()
}
