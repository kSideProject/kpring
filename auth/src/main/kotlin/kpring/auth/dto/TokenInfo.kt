package kpring.auth.dto

import java.time.LocalDateTime

data class TokenInfo(
    val token: String,
    val expireAt: LocalDateTime,
    val tokenId: String
)
