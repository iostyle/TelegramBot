package order

/**
start - Starting bot
random - Random
joke - Have a joke
 */
enum class TalkBotOrder(val value: String) {
    START("/start"),
    RANDOM("/random"),
    JOKE("/joke")
}