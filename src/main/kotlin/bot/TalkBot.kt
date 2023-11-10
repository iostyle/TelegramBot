package bot

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException


class TalkBot : TelegramLongPollingBot() {

    override fun getBotUsername(): String {
        return ""
    }

    override fun getBotToken(): String {
        return ""
    }

    override fun onUpdateReceived(update: Update) {
        System.out.println("${update.message}")
        if (update.hasMessage() && update.message.hasText()) {
            if (update.message.isSuperGroupMessage || update.message.isGroupMessage
                || update.message.isChannelMessage
            ) {
                System.out.println("${update.message.chat.type}")
                return
            }
            val message = SendMessage() // Create a SendMessage object with mandatory fields
            message.chatId = update.message.chatId.toString()

            val result = update.message.text
                .replace("@functionzzbot", "")
                .replace("你", "_Wo_")
                .replace("我", "你")
                .replace("_Wo_", "我")
                .replace("吗", "")
                .replace("吧", "")
                .replace("？", "")

            message.text = result
            try {
                execute(message) // Call method to send the message
            } catch (e: TelegramApiException) {
                e.printStackTrace()
            }
        }
    }
}