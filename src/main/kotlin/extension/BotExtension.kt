package extension

import order.TalkBotOrder
import org.telegram.telegrambots.meta.api.methods.send.SendDice
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

object BotExtension {

    suspend fun AbsSender.sendMessage(chatId: String, content: String, fromMessageId: Int? = null) {
        val message = SendMessage() // Create a SendMessage object with mandatory fields
        message.chatId = chatId
        message.text = content
        message.replyToMessageId = fromMessageId
        try {
            execute(message) // Call method to send the message
            System.out.println("sendMessage success $chatId $content")
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

    /**
     * 处理命令消息
     * @return 是否已处理
     */
    suspend fun AbsSender.handleOrder(update: Update): Boolean {
        update.message.text?.let { content ->
            if (content.startsWith("/")) {
                TalkBotOrder.entries.forEach {
                    if (content.startsWith(it.value)) {
                        when (it) {
                            TalkBotOrder.START -> {
                                // TODO chatID 入库
                            }

                            TalkBotOrder.RANDOM -> {
                                sendRandomEmoji(update)
                            }

                            TalkBotOrder.JOKE -> {
                                // TODO joke
                                sendMessage(update.message.chatId.toString(), "还没编好", update.message.messageId)
                            }
                        }
                        return true
                    }
                }
            }
        }

        return false
    }

    suspend fun AbsSender.sendRandomEmoji(update: Update) {
        /**
         * Emoji on which the dice throw animation is based. Currently, must be one of “🎲”, “🎯”, “🏀”, “⚽”, “🎳”, or “🎰”. Dice can have values 1-6 for “🎲”, “🎯” and “🎳”, values 1-5 for “🏀” and “⚽”, and values 1-64 for “🎰”. Defaults to “🎲”
         */

        val message = SendDice()
        message.chatId = update.message.chatId.toString()
        message.replyToMessageId = update.message.messageId
        message.emoji = "🎲"
        try {
            execute(message) // Call method to send the message
            System.out.println("send dice success $update.message.chatId.toString() $message")
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
}