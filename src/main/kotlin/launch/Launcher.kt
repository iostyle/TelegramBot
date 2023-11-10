package launch

import bot.TalkBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

fun main() {
    val api = TelegramBotsApi(DefaultBotSession::class.java)
    val bot = TalkBot()
    api.registerBot(bot)
}