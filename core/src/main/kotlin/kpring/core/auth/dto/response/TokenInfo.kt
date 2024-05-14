package kpring.core.auth.dto.response

import kpring.core.auth.enums.TokenType

data class TokenInfo(
  val type: TokenType,
  val userId: String,
)
