package chatbot.dsl

import chatbot.api.ChatBot
import chatbot.api.ChatContext
import chatbot.bot.MessageHandler
import chatbot.bot.MessageProcessorContext
import java.util.*

@ChatBotDSL
class IntoBehaviourBuilder(chatBot: ChatBot) : BehaviourBuilder<ChatContext?>(chatBot) {
    inline fun <reified C : ChatContext?> into(builder: BehaviourBuilder<C>.() -> Unit) {
        val childBuilder = BehaviourBuilder<C>(chatBot).apply(builder)
        val childHandlers = childBuilder.build()

        this.handlers += childHandlers.map { handler ->
            MessageHandler(
                predicate = { message, context ->
                    context is C && handler.predicate(message, context)
                },
                processor = {
                    require(context is C)
                    val messageProcessorContext = MessageProcessorContext(
                        message = message,
                        client = client,
                        context = context,
                        setContext = setContext,
                    )
                    handler.processor(messageProcessorContext)
                },
            )
        }
    }

    inline infix fun <reified C : ChatContext?> C.into(builder: BehaviourBuilder<C>.() -> Unit) {
        val intoBehaviourBuilder = IntoBehaviourBuilder(chatBot).apply { into<C>(builder) }
        val childHandlers = intoBehaviourBuilder.build()

        handlers += childHandlers.map { handler ->
            MessageHandler(
                predicate = { message, context ->
                    val castedContext = context as? C
                    val isContextEqual = Objects.equals(this, context)
                    val isPredicateMatched = castedContext != null && handler.predicate(message, castedContext)
                    isContextEqual && isPredicateMatched
                },
                processor = handler.processor,
            )
        }
    }
}
