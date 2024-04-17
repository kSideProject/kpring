package kpring.user.controller

import kpring.user.dto.request.LoginRequest
import kpring.user.service.LoginService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class LoginController(val loginService: LoginService) {
    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest,
    ): ResponseEntity<*> {
        val response = loginService.login(request)
        return ResponseEntity
            .ok()
            .header("Authorization", response.token)
            .body(response)
    }

    @DeleteMapping("/logout")
    fun logout(
        @RequestHeader("Authorization") token: String,
    ): ResponseEntity<Any> {
        loginService.logout(token)
        return ResponseEntity.ok().build()
    }
}