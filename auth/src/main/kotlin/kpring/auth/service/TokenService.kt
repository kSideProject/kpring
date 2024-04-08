package kpring.auth.service

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import kpring.core.auth.dto.request.CreateTokenRequest
import kpring.core.auth.dto.response.CreateTokenResponse
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
            accessToken = accessResult.first,
            accessExpireAt = accessResult.second,
            refreshToken = refreshResult.first,
            refreshExpireAt = refreshResult.second
        )
    }

    /*
     util method
     */
    private fun createJwtToken(info: CreateTokenRequest, type: TokenType): Pair<String, LocalDateTime> {
        val duration = when (type) {
            TokenType.REFRESH -> refreshDuration
            TokenType.ACCESS -> accessDuration
        }

        val expiredAt = Calendar.getInstance().plus(duration)

        val token = Jwts.builder()
            .setSubject(info.id)
            .setClaims(
                mutableMapOf<String, Any>(
                    "type" to type,
                    "nickname" to info.nickname
                )
            )
            .setIssuedAt(Date())
            .setExpiration(expiredAt.time)
            .signWith(signingKey, SignatureAlgorithm.HS256)
            .compact()

        return "Bearer $token" to expiredAt.toLocalDateTime()
    }

    private fun Calendar.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.ofInstant(this.time.toInstant(), ZoneId.of("Asia/Seoul"))
    }

    private fun Calendar.plus(duration: Int): Calendar {
        this.add(Calendar.MILLISECOND, duration)
        return this
    }
}
