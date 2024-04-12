package kpring.auth.dto

import kpring.core.auth.enums.TokenType
import java.time.LocalDateTime

data class JwtToken(
    val id: String,
    val userId: String,
    val type: TokenType,
    val nickname: String,
    val expiredAt: LocalDateTime
)