package bot

import constants.BotConstants
import extension.BotExtension.sendMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import order.TalkBotOrder
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update
import kotlin.random.Random

class TalkBot : TelegramLongPollingBot() {

    private val orderSet = TalkBotOrder.entries

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
                // /random@name
                if (content.startsWith("/")) {
                    orderSet.forEach {
                        if (content.startsWith(it.value)) {
                            when (it) {
                                TalkBotOrder.RANDOM -> {
                                    sendMessage(update.message.chatId.toString(),"random ${Random.nextInt(100)}")
                                }

                                TalkBotOrder.JOKE -> {
                                    sendMessage(update.message.chatId.toString(),"还没编好")
                                }
                            }

                            return@let
                        }
                    }
                }

                val result = zzTalkReply(content.replace("@$botUsername", ""))
                sendMessage(update.message.chatId.toString(), result)
            }
        }
    }

    private suspend fun privateMessageReply(update: Update) {
        System.out.println("privateMessageReply: ${update.message}")
        val result = zzTalkReply(update.message.text)
        sendMessage(update.message.chatId.toString(), result)
    }

    private suspend fun zzTalkReply(origin: String): String {
        return origin.replace("@functionzzbot", "")
                .replace("你", "_Wo_")
                .replace("我", "你")
                .replace("_Wo_", "我")
                .replace("吗", "")
                .replace("吧", "")
                .replace("？", "")
                .replace("怎么样", "挺好的")
    }
}