package kpring.auth.api.v1

import kpring.auth.service.TokenService
import kpring.core.auth.dto.request.CreateTokenRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class AuthController(
    private val tokenService: TokenService,
) {

    @PostMapping("/token")
    suspend fun createToken(@Validated @RequestBody request: CreateTokenRequest): ResponseEntity<*> {
        val tokenInfo = tokenService.createToken(request)
        return ResponseEntity.ok()
            .headers { it["Authorization"] = tokenInfo.accessToken }
            .body(tokenInfo)
    }

    @DeleteMapping("/token/{tokenData}")
    suspend fun expireToken(@PathVariable("tokenData") token: String): ResponseEntity<Any> {
        tokenService.expireToken(token)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/token")
    suspend fun recreateAccessToken(@RequestHeader("Authorization") refreshToken: String): ResponseEntity<*> {
        val response = tokenService.createAccessToken(refreshToken)
        return ResponseEntity.ok()
            .header("Authorization", response.accessToken)
            .body(response)
    }

    @GetMapping("/validation")
    suspend fun validateToken(): ResponseEntity<*> {
        TODO()
    }
}