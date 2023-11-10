package launch

import bot.TalkBot
import constants.SessionConstants
import extension.BotExtension.sendMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

fun main() {
    val api = TelegramBotsApi(DefaultBotSession::class.java)
    val bot = TalkBot()
    api.registerBot(bot)

//    looper(bot)
}

fun looper(bot: TalkBot) {
    GlobalScope.launch {
        while (isActive){
            delay(1000)
            bot.sendMessage(SessionConstants.CHAT_ID_1,"Timer: ${System.currentTimeMillis()}")
        }
    }
}
