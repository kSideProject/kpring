package kpring.core.auth.dto.response

import java.time.LocalDateTime

data class CreateTokenResponse(
    val accessToken : String,
    val accessExpireAt: LocalDateTime,
    val refreshToken : String,
    val refreshExpireAt: LocalDateTime,
)
