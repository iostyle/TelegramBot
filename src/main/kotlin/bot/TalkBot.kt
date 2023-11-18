package bot

import constants.BotConstants
import extension.BotExtension.handleOrder
import extension.BotExtension.sendMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

class TalkBot : TelegramLongPollingBot() {

    override fun getBotUsername(): String {
        return BotConstants.BOT_NAME
    }

    override fun getBotToken(): String {
        return BotConstants.BOT_TOKEN
    }

    override fun onUpdateReceived(update: Update) {
        GlobalScope.launch(Dispatchers.IO) {
            if (update.hasMessage() && update.message.hasText()) {
                when (update.message.chat.type) {
                    "private" -> {
                        privateMessageReply(update)
                    }

                    "group", "supergroup" -> {
                        groupMessageReply(update)
                    }

                    "channel" -> {

                    }
                }

            }
        }
    }

    private suspend fun groupMessageReply(update: Update) {
        System.out.println("groupMessageReply: ${update.message}")
        update.message.text?.let { content ->
            if (content.contains("@$botUsername")) {
                if (handleOrder(update)) return
                val result = zzTalkReply(content.replace("@$botUsername", ""))
                sendMessage(update.message.chatId.toString(), result, update.message.messageId)
            }
        }
    }

    // TODO private order
    private suspend fun privateMessageReply(update: Update) {
        System.out.println("privateMessageReply: ${update.message}")
        val result = zzTalkReply(update.message.text)
        sendMessage(update.message.chatId.toString(), result, update.message.messageId)
    }

    private suspend fun zzTalkReply(origin: String): String {
        return withContext(Dispatchers.IO) {
            var result = origin
            remove.forEach { result = result.replace(it, "") }
            exchange.forEach { result = result.replace(it.first, it.second) }
            return@withContext result
        }
    }

    private val remove = arrayOf("@$botUsername", "吗", "吧", "啊")
    private val exchange = arrayOf("你" to "我", "？" to "!", "怎么样" to "挺好的")
}