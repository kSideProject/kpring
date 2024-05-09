package kpring.auth.service

import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import kpring.auth.exception.TokenExpiredException
import kpring.auth.repository.ExpireTokenRepository
import kpring.auth.util.toObject
import kpring.auth.util.toToken
import kpring.core.auth.dto.request.CreateTokenRequest
import kpring.core.auth.dto.response.CreateTokenResponse
import kpring.core.auth.dto.response.ReCreateAccessTokenResponse
import kpring.core.auth.dto.response.TokenValidationResponse
import kpring.core.auth.enums.TokenType
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
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
    val accessResult = request.toToken(TokenType.ACCESS, signingKey, accessDuration)
    val refreshResult = request.toToken(TokenType.REFRESH, signingKey, refreshDuration)

    return CreateTokenResponse(
      accessToken = accessResult.token,
      accessExpireAt = accessResult.expireAt,
      refreshToken = refreshResult.token,
      refreshExpireAt = refreshResult.expireAt,
    )
  }

  suspend fun expireToken(token: String) {
    val jwt = token.toObject(signingKey)
    tokenRepository.expireToken(jwt.id, jwt.expiredAt)
  }

  suspend fun reCreateAccessToken(refreshToken: String): ReCreateAccessTokenResponse {
    val jwt = refreshToken.toObject(signingKey)
    if (jwt.type != TokenType.REFRESH) throw IllegalArgumentException("잘못된 토큰의 타입입니다.")
    if (tokenRepository.isExpired(refreshToken)) throw TokenExpiredException("토큰이 만료되었습니다.")

    return jwt.run {
      val accessTokenInfo =
        CreateTokenRequest(userId, nickname)
          .toToken(TokenType.ACCESS, signingKey, accessDuration)

      ReCreateAccessTokenResponse(
        accessToken = accessTokenInfo.token,
        accessExpireAt = accessTokenInfo.expireAt,
      )
    }
  }

  suspend fun checkToken(token: String): TokenValidationResponse {
    return try {
      val jwt = token.toObject(signingKey)

      val isValid = !tokenRepository.isExpired(token)

      TokenValidationResponse(isValid, jwt.type, jwt.userId)
    } catch (ex: TokenExpiredException) {
      TokenValidationResponse(false, null, null)
    }
  }
}
