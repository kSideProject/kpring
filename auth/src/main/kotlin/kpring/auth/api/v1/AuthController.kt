package kpring.auth.api.v1

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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
            .header("Authorization", "Bearer ${tokenInfo.accessToken}")
            .body(tokenInfo)
    }

    @DeleteMapping("/token/{tokenData}")
    suspend fun expireToken(@PathVariable("tokenData") token: String): ResponseEntity<Any> {
        coroutineScope { async{tokenService.expireToken(token) } }.await()
        return ResponseEntity.ok().build()
    }

    @GetMapping("/token")
    suspend fun recreateAccessToken(@RequestHeader("Authorization") refreshToken: String): ResponseEntity<*> {
        val response = tokenService.reCreateAccessToken(refreshToken.removePrefix("Bearer "))
        return ResponseEntity.ok()
            .header("Authorization", "Bearer ${response.accessToken}")
            .body(response)
    }

    @GetMapping("/validation")
    suspend fun validateToken(@RequestHeader("Authorization") token: String): ResponseEntity<*> {
        val response = tokenService.checkToken(token.removePrefix("Bearer "))
        return ResponseEntity.ok()
            .body(response)
    }
}