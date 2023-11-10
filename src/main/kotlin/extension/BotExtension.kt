package extension

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.bots.AbsSender
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

object BotExtension {
    suspend fun AbsSender.sendMessage(chatId: String, content: String) {
        val message = SendMessage() // Create a SendMessage object with mandatory fields
        message.chatId = chatId
        message.text = content
        try {
            execute(message) // Call method to send the message
            System.out.println("sendMessage success $chatId $content")
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
}