package kpring.auth.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import kpring.auth.dto.JwtToken
import kpring.auth.dto.TokenInfo
import kpring.auth.error.AuthErrorCode
import kpring.core.auth.dto.request.CreateTokenRequest
import kpring.core.auth.enums.TokenType
import kpring.core.global.exception.ServiceException
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*
import javax.crypto.SecretKey

fun CreateTokenRequest.toToken(
  type: TokenType,
  signingKey: SecretKey,
  duration: Int,
): TokenInfo {
  val tokenId = "${id}${UUID.randomUUID()}"
  val expiredAt = Calendar.getInstance().plus(duration)

  val token =
    Jwts.builder()
      .setSubject(id)
      .setId(tokenId)
      .setClaims(
        mutableMapOf<String, Any>(
          "userId" to id,
          "id" to tokenId,
          "type" to type,
          "nickname" to nickname,
        ),
      )
      .setIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("+09:00"))))
      .setExpiration(expiredAt.time)
      .signWith(signingKey, SignatureAlgorithm.HS256)
      .compact()

  return TokenInfo(token, expiredAt.toLocalDateTime(), tokenId)
}

/**
 * @throws ServiceException 토큰 검증 실패시 예외 발생
 */
fun String.toObject(key: SecretKey): JwtToken {
  try {
    val claims =
      Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(this.removePrefix("Bearer "))
        .body

    return JwtToken(
      id = claims.tokenId(),
      type = claims.type(),
      nickname = claims.nickname(),
      userId = claims.userId(),
      expiredAt = claims.expiredAt(),
    )
  } catch (ex: ExpiredJwtException) {
    throw ServiceException(AuthErrorCode.TOKEN_EXPIRED)
  } catch (ex: RuntimeException) {
    throw ServiceException(AuthErrorCode.TOKEN_NOT_VALID)
  }
}

private fun Claims.userId() = this.get("userId", String::class.java)

private fun Claims.tokenId() = this.get("id", String::class.java)

private fun Claims.nickname() = this.get("nickname", String::class.java)

private fun Claims.type() = TokenType.valueOf(this.get("type", String::class.java))

private fun Claims.expiredAt() = LocalDateTime.ofInstant(this.expiration.toInstant(), ZoneId.of("Asia/Seoul"))

private fun Calendar.toLocalDateTime() = LocalDateTime.ofInstant(this.time.toInstant(), ZoneId.of("Asia/Seoul"))

private fun Calendar.plus(milliseconds: Int): Calendar {
  this.add(Calendar.MILLISECOND, milliseconds)
  return this
}
