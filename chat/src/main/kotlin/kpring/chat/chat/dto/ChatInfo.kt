package kpring.chat.chat.dto

data class ChatInfo(
    val chatId: String,
    val userId: String,
    val roomId: String,
    val nickname: String,
    val content: String
)
