package kpring.core.auth.dto.response

import java.time.LocalDateTime

data class ReCreateAccessTokenResponse(
  val accessToken: String,
  val accessExpireAt: LocalDateTime,
)