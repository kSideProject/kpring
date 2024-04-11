package kpring.auth.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import kpring.auth.dto.TokenInfo
import kpring.core.auth.dto.request.CreateTokenRequest
import kpring.core.auth.enums.TokenType
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey

fun CreateTokenRequest.toToken(type: TokenType, signingKey: SecretKey, duration: Int): TokenInfo {
    val tokenId = "${id}${UUID.randomUUID()}"
    val expiredAt = Calendar.getInstance().plus(duration)

    val token = Jwts.builder()
        .setSubject(id)
        .setId(tokenId)
        .setClaims(
            mutableMapOf<String, Any>(
                "userId" to id,
                "id" to tokenId,
                "type" to type,
                "nickname" to nickname
            )
        )
        .setIssuedAt(Date())
        .setExpiration(expiredAt.time)
        .signWith(signingKey, SignatureAlgorithm.HS256)
        .compact()

    return TokenInfo(token, expiredAt.toLocalDateTime(), tokenId)
}

fun String.claim(signingKey: SecretKey): Claims {
    return Jwts.parserBuilder()
        .setSigningKey(signingKey)
        .build()
        .parseClaimsJws(this)
        .body
}

fun Claims.userId() = this.get("userId", String::class.java)
fun Claims.tokenId() = this.get("id", String::class.java)
fun Claims.nickname() = this.get("nickname", String::class.java)
fun Claims.type() = TokenType.valueOf(this.get("type", String::class.java))

fun Claims.expiredAt() = LocalDateTime.ofInstant(this.expiration.toInstant(), ZoneId.of("Asia/Seoul"))
fun Calendar.toLocalDateTime() = LocalDateTime.ofInstant(this.time.toInstant(), ZoneId.of("Asia/Seoul"))
fun Calendar.plus(milliseconds: Int): Calendar {
    this.add(Calendar.MILLISECOND, milliseconds)
    return this
}

