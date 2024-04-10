package kpring.auth.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import kpring.auth.dto.TokenInfo
import kpring.auth.repository.ExpireTokenRepository
import kpring.core.auth.dto.request.CreateTokenRequest
import kpring.core.auth.dto.response.CreateTokenResponse
import kpring.core.auth.dto.response.ReCreateAccessTokenResponse
import kpring.core.auth.enums.TokenType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.crypto.SecretKey

@Service
class TokenService(
    @Value("\${jwt.access.duration}") private val accessDuration: Int,
    @Value("\${jwt.refresh.duration}") private val refreshDuration: Int,
    @Value("\${jwt.secret}") private val secretKey: String,
    private val tokenRepository: ExpireTokenRepository,
) {
    private lateinit var signingKey: SecretKey

    @PostConstruct
    fun init() {
        signingKey = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
            ?: throw IllegalStateException("토큰을 발급하기 위한 key가 적절하지 않습니다.")
    }

    /*
     business logic
     */
    fun createToken(request: CreateTokenRequest): CreateTokenResponse {
        val accessResult = createJwtToken(request, TokenType.ACCESS)
        val refreshResult = createJwtToken(request, TokenType.REFRESH)

        return CreateTokenResponse(
            accessToken = accessResult.token,
            accessExpireAt = accessResult.expireAt,
            refreshToken = refreshResult.token,
            refreshExpireAt = refreshResult.expireAt
        )
    }

    suspend fun expireToken(token: String) {
        val claim = token.claim()
        val tokenId = claim.tokenId()
        val expiredAt = claim.expiredAt()
        tokenRepository.expireToken(tokenId, expiredAt)
    }

    suspend fun reCreateAccessToken(refreshToken: String): ReCreateAccessTokenResponse {
        val claims = refreshToken.claim()
        if (claims.type() != TokenType.REFRESH ||
            tokenRepository.isExpired(refreshToken)
        ) {
            throw IllegalArgumentException("잘못된 토큰의 타입입니다.")
        }
        val userId = claims.userId()
        val nickname = claims.nickname()
        val accessTokenInfo = createJwtToken(CreateTokenRequest(userId, nickname), TokenType.ACCESS)
        return ReCreateAccessTokenResponse(
            accessToken = accessTokenInfo.token,
            accessExpireAt = accessTokenInfo.expireAt,
        )
    }

    /*
     util method
     */
    private fun createJwtToken(info: CreateTokenRequest, type: TokenType): TokenInfo {
        val duration = when (type) {
            TokenType.REFRESH -> refreshDuration
            TokenType.ACCESS -> accessDuration
        }

        val tokenId = "${info.id}${UUID.randomUUID()}"
        val expiredAt = Calendar.getInstance().plus(duration)

        val token = Jwts.builder()
            .setSubject(info.id)
            .setId(tokenId)
            .setClaims(
                mutableMapOf<String, Any>(
                    "userId" to info.id,
                    "id" to tokenId,
                    "type" to type,
                    "nickname" to info.nickname
                )
            )
            .setIssuedAt(Date())
            .setExpiration(expiredAt.time)
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()

        return TokenInfo(token, expiredAt.toLocalDateTime(), tokenId)
    }

    private fun String.claim(): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(this)
            .body
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

}
